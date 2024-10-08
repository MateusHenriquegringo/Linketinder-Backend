package dao

import DB.ConnectionFactory
import DB.DBTypes
import model.Candidato
import model.Empresa
import spock.lang.Shared
import spock.lang.Specification

import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class EmpresaDAOTest extends Specification {

    @Shared
    private Connection connection = ConnectionFactory.getConnection(DBTypes.H2DATABASE)

    @Shared
    Empresa empresa

    @Shared
    EmpresaDAO dao

    def setupSpec() {
        def statement = connection.createStatement()

        statement.execute(
                """
        CREATE TABLE  empresa (
              id SERIAL PRIMARY KEY,
              empresa_name VARCHAR(100) NOT NULL,
              description TEXT,
              email VARCHAR(100) NOT NULL UNIQUE,
              cnpj VARCHAR(18) NOT NULL UNIQUE,
              cep VARCHAR(9) NOT NULL,
              country VARCHAR(50) NOT NULL,
              password VARCHAR(100) NOT NULL
);
""")
    }

    def setup() {
        empresa = new Empresa(
                empresa_name: "nike",
                email: "nike@example.com",
                CNPJ: "9999990000",
                CEP: "01001000",
                country: "brasil",
                description: "Uma empresa promissora",
                password: "senhaSegura123"
        )

        dao = new EmpresaDAO(connection)
    }


    def cleanup() {
        def statement = connection.createStatement()
        statement.execute("DELETE FROM empresa")
    }

    def cleanupSpec() {
        def statement = connection.createStatement()
        statement.execute("DROP TABLE empresa")
        statement.close()
        connection.close()
    }

    void "testCreateEmpresaThrowsException"(){
        given:
        empresa
        empresa.setEmail(null)

        when:
        dao.create(empresa)

        then:
        def error = thrown(RuntimeException.class)
        error.message.contains("nao foi possivel criar empresa")

        and:
        empresa.setEmail("emailvalido@email")
        empresa.setCNPJ(null)

        when:
        dao.create(empresa)

        then:
        def error2 = thrown(RuntimeException.class)
        error2.message.contains("nao foi possivel criar empresa")
    }

    void "testCreateEmpresaWorksCorrectly"(){
        given:
        empresa

        when:
        long id = dao.create(empresa)

        then:
        id > 0

        and:
        def pstmt = connection.prepareStatement("SELECT * FROM empresa WHERE id = ?")
        pstmt.setLong(1, id)
        ResultSet resultSet = pstmt.executeQuery()

        resultSet.next()
        resultSet.getString("cep") == empresa.getCEP()
        resultSet.getString("empresa_name") == empresa.getEmpresa_name()
        resultSet.getString("cnpj") == empresa.getCNPJ()
        resultSet.getString("description") == empresa.getDescription()
        resultSet.getString("email") == empresa.getEmail()
        resultSet.getString("country") == empresa.getCountry()
        resultSet.getLong("id") == id
    }
    void "testCreateEmpresaThrowsExceptionWhenEmailDuplicate"() {

        given:
        empresa
        def anotherEmpresa = empresa

        when:
        dao.create(empresa)
        dao.create(anotherEmpresa)

        then:
        def error = thrown(RuntimeException.class)
        error.message.contains("nao foi possivel criar empresa")

        and:
        def pstmt = connection.prepareStatement("SELECT * FROM empresa")
        ResultSet resultSet = pstmt.executeQuery()

        resultSet.next()
        resultSet.getRow() == 1
    }

    void "testUpdateEmpresaSuccess"() {
        given:
        def updateEmpresa = empresa
        updateEmpresa.setEmpresa_name("adidas")

        when:
        long id = dao.create(empresa)
        dao.update(updateEmpresa, id)

        then:
        def updatedEmpresa = dao.findById(id)
        updatedEmpresa.empresa_name == "adidas"
        updatedEmpresa.getCNPJ() == empresa.getCNPJ()

    }

    void "testUpdateEmpresaThrowsExceptionWhenEmailInvalid"() {
        given:
        Long id = dao.create(empresa)

        when:
        empresa.setEmail(null)
        dao.update(empresa, id)

        then:
        def error = thrown(RuntimeException.class)
        error.message.contains("ocorreu um erro ao editar")
    }

    void "testUpdateEmpresaThrowsExceptionWhenEmpresaNotFound"() {
        given:
        def mock = Mock(Empresa)
        when:
        dao.update(mock, -1)

        then:
        def error = thrown(RuntimeException.class)
        error.message.contains("Empresa nao encontrada")
    }

    void "testDeleteCorrectly"() {
        given:
        long id = dao.create(empresa)

        when:
        Statement stmt = connection.createStatement()
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM empresa")

        then:
        resultSet.next()
        resultSet.getRow() == 1

        and:
        dao.delete(id)

        when:
        Statement stmt2 = connection.createStatement()
        ResultSet resultSet2 = stmt2.executeQuery("SELECT * FROM empresa")

        then:
        !resultSet2.next()
        resultSet2.getRow() == 0
    }


    void "tesFindByIdWorksCorrectly"() {
        given:
        long id = dao.create(empresa)

        when:
        Empresa result = dao.findById(id)

        then:
        result.getCNPJ() == empresa.getCNPJ()
        result.getId() == id
        result.getEmpresa_name() == empresa.getEmpresa_name()
        result.getEmail() == empresa.getEmail()
        result.getCountry() == empresa.getCountry()
    }

    void "testFindByIdWhenIdDoesNotExists"() {
        when:
        dao.findById(-1)

        then:
        def error = thrown(NoSuchElementException)
        error.message.contains("Empresa nao encontrada")

    }

    void "testListAll"(){
        given:
        def empresa = new Empresa()
        empresa.setDescription("Uma empresa promissora")
        empresa.setCNPJ("9999990000")
        empresa.setEmpresa_name("nike")
        empresa.setCEP("01001000")
        empresa.setCountry("brasil")
        empresa.setPassword("senhaSegura123")
        empresa.setEmail("nike@example.com")

        def anotherEmpresa = new Empresa()
        anotherEmpresa.setDescription("desc")
        anotherEmpresa.setCNPJ("cnpj")
        anotherEmpresa.setEmpresa_name("adidas")
        anotherEmpresa.setCEP("cep")
        anotherEmpresa.setCountry("brazil")
        anotherEmpresa.setPassword("supersenha")
        anotherEmpresa.setEmail("outroEmail")

        when:
        dao.create(empresa)
        dao.create(anotherEmpresa)

        def list = dao.listAll()

        then:
        list.size() == 2


        assert list[0].getEmpresa_name() == empresa.getEmpresa_name()
        assert list[0].getDescription() == empresa.getDescription()
        assert list[0].getCNPJ() == empresa.getCNPJ()

        assert list[1].getDescription() ==anotherEmpresa.getDescription()
        assert list[1].getEmpresa_name() ==anotherEmpresa.getEmpresa_name()
        assert list[1].getCNPJ() ==anotherEmpresa.getCNPJ()
    }

}
