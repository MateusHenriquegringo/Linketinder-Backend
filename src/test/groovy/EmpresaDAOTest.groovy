import repository.EmpresaDAO
import DB.H2DatabaseConnection
import model.Empresa
import spock.lang.Shared
import spock.lang.Specification

import java.sql.Connection

class EmpresaDAOTest extends Specification {

    Connection connection = H2DatabaseConnection.getConnection()

    @Shared
    Empresa empresa

    @Shared
    EmpresaDAO dao

    def setup() {
        def statement = connection.createStatement()
        statement.execute("""
        CREATE TABLE \"Empresa\" (
            id SERIAL PRIMARY KEY,
            name VARCHAR(100) NOT NULL,
            description TEXT,
            email VARCHAR(100) NOT NULL,
            cnpj VARCHAR(18) NOT NULL,
            cep VARCHAR(10) NOT NULL,
            country VARCHAR(50) NOT NULL,
            password VARCHAR(100) NOT NULL
        )
    """)

        empresa = new Empresa(
                "Nome da Empresa",
                "Descrição da Empresa",
                "email@empresa.com",
                "12.345.678/0001-90",
                "12345-678",
                "Brasil",
                "senhaSegura"
        )
        dao = new EmpresaDAO(connection)
    }

    def cleanup() {
        def statement = connection.createStatement()
        statement.execute("DROP TABLE \"Empresa\"")
        statement.close()
        connection.close()
    }

    def "verify list all method"() {
        given:
        Empresa empresa1 = new Empresa(
                "Nome da Empresa",
                "Descrição da Empresa",
                "email@empresa.com",
                "12.345.678/0001-90",
                "12345-678",
                "Brasil",
                "senhaSegura"
        )
        dao.create(empresa1)

        Empresa empresa2 = new Empresa(
                "Nome da Empresa 2",
                "Descrição da Empresa 2",
                "email2@empresa.com",
                "12.345.678/0001-90",
                "12345-678",
                "Brasil",
                "senhaSegura"
        )
        dao.create(empresa2)

        when:
        List<Empresa> list = dao.listAll()

        then:
        assert list.size() == 2
        list[0].name == empresa1.getName()
        list[1].name == empresa2.getName()
        list[0].email == empresa1.getEmail()
        list[1].email == empresa2.getEmail()
    }

    def "verify create method"() {
        when:
        dao.create(empresa)

        then:
        def resultSet = connection.createStatement().executeQuery("SELECT * FROM \"Empresa\"")
        resultSet.next()
        assert resultSet.getString("name") == empresa.getName()
        assert resultSet.getString("description") == empresa.getDescription()
        assert resultSet.getString("email") == empresa.getEmail()
        assert resultSet.getString("cnpj") == empresa.getCNPJ()
        assert resultSet.getString("cep") == empresa.getCEP()
    }

    def "verify update method"() {
        given:
        Empresa ogEmpresa = empresa
        dao.create(ogEmpresa)
        Long idToUpdate = 1L

        when:
        ogEmpresa.setName("update")
        ogEmpresa.setDescription("update")

        dao.update(ogEmpresa, idToUpdate)

        then:
        Empresa updated = dao.findById(idToUpdate)
        updated.name == "update"
        updated.description == "update"

    }

    def "verify findById method"() {

        when:
        dao.create(empresa)

        def resultSet = connection.createStatement().executeQuery("SELECT * FROM \"Empresa\"")
        resultSet.next()
        Long id = resultSet.getLong("id")

        then:
        Empresa foundEmpresa = dao.findById(id)
        foundEmpresa.name == empresa.getName()
        foundEmpresa.description == empresa.getDescription()
        foundEmpresa.email == empresa.getEmail()
        foundEmpresa.CNPJ == empresa.getCNPJ()
        foundEmpresa.CEP == empresa.getCEP()
    }

    def "verify delete method"() {
        when:
        dao.create(empresa)

        def resultSet = connection.createStatement().executeQuery("SELECT * FROM \"Empresa\"")
        resultSet.next()
        Long id = resultSet.getLong("id")

        and:
        dao.delete(id)

        then:
        def deletedResultSet = connection.createStatement().executeQuery("SELECT * FROM \"Empresa\" WHERE id = ${id}")
        assert !deletedResultSet.next()
    }

    def "verify findById throws exception"() {
        when:
        dao.findById(-1)

        then:
        def exception = thrown(NoSuchElementException)
        exception.message == "Empresa nao encontrada"
    }

}
