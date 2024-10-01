package dao

import repository.VagaDAO
import DB.H2DatabaseConnection
import model.Vaga
import spock.lang.Shared
import spock.lang.Specification

import java.sql.Connection

class VagaDAOTest extends Specification {

    Connection connection = H2DatabaseConnection.getConnection()

    @Shared
    Vaga vaga

    @Shared
    VagaDAO dao

    def setup() {

        def statement = connection.createStatement()
        statement.execute("""
        CREATE TABLE vaga (
            id SERIAL PRIMARY KEY,
            vaga_name VARCHAR(100) NOT NULL,
            description TEXT,
            empresa_id INT NOT NULL,
            state VARCHAR(2) NOT NULL,
            city VARCHAR(100) NOT NULL
                );"""
        )
        vaga = new Vaga("Desenvolvedor Java", "Vaga para desenvolvedor Java pleno.", 1L, "SP", "São Paulo")
        dao = new VagaDAO(connection)
    }

    def cleanup() {
        def statement = connection.createStatement()
        statement.execute("DROP TABLE vaga")
        statement.close()
        connection.close()
    }

    def "verify list all method"() {
        given:
        Vaga vaga1 = new Vaga("Desenvolvedor Java",
                "Vaga para desenvolvedor Java pleno.",
                1L,
                "SP",
                "São Paulo")
        dao.create(vaga1)

        Vaga vaga2 = new Vaga("Desenvolvedor JS",
                "Vaga para desenvolvedor Javascript.",
                1L,
                "SP",
                "São Paulo")
        dao.create(vaga2)

        when:
        List<Vaga> list = dao.listAll()

        then:
        assert list.size() == 2
        list[0].vaga_name == vaga1.getVaga_name()
        list[1].vaga_name == vaga2.getVaga_name()
        list[0].description == vaga1.getDescription()
        list[1].description == vaga2.getDescription()
    }

    def "verify create method"() {
        when:
        dao.create(vaga)

        then:
        def resultSet = connection.createStatement().executeQuery("SELECT * FROM vaga")
        resultSet.next()
        assert resultSet.getString("vaga_name") == vaga.getVaga_name()
        assert resultSet.getString("description") == vaga.getDescription()

    }

    def "verify update method"() {
        given:
        Vaga ogVaga = vaga
        dao.create(ogVaga)
        Long idToUpdate = 1L

        when:
        ogVaga.setVaga_name("update")
        ogVaga.setDescription("update")

        dao.update(ogVaga, idToUpdate)

        then:
        Vaga updated = dao.findById(idToUpdate)
        updated.vaga_name == "update"
        updated.description == "update"

    }

    def "verify findById method"() {
        when:
        dao.create(vaga)

        def resultSet = connection.createStatement().executeQuery("SELECT * FROM vaga")
        resultSet.next()
        Long id = resultSet.getLong("id")

        then:
        Vaga foundVaga = dao.findById(id)
        assert foundVaga.vaga_name == vaga.getVaga_name()
        assert foundVaga.description == vaga.getDescription()
        assert foundVaga.empresaId == vaga.getEmpresaId()
    }

    def "verify delete method"() {
        when:
        dao.create(vaga)

        def resultSet = connection.createStatement().executeQuery("SELECT * FROM vaga")
        resultSet.next()
        Long id = resultSet.getLong("id")

        and:
        dao.delete(id)

        then:
        def deletedResultSet = connection.createStatement().executeQuery("SELECT * FROM vaga WHERE id = ${id}")
        assert !deletedResultSet.next()
    }

    def "verify findById throws exception"() {
        when:
        dao.findById(-1)

        then:
        def exception = thrown(NoSuchElementException)
        exception.message == "Essa vaga nao existe"
    }

}
