import repository.CompetenciasDAO
import DB.H2DatabaseConnection
import model.Competencia
import spock.lang.Shared
import spock.lang.Specification

import java.sql.Connection

class CompetenciasDAOTest extends Specification{

    Connection connection = H2DatabaseConnection.getConnection();

    @Shared
    Competencia competencia

    @Shared
    CompetenciasDAO dao

    def setup(){
        def statement = connection.createStatement()
        statement.execute(
                "CREATE TABLE \"Competencia\" (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL" +
                ");"
        )

        competencia = new Competencia("Analise de Requisitos")
        dao = new CompetenciasDAO(connection)
    }

    def cleanup() {
        def statement = connection.createStatement()
        statement.execute("DROP TABLE \"Competencia\"")
        statement.close()
        connection.close()
    }

    def "verify list all method"() {
        given:
        Competencia comp = new Competencia("test")
        dao.create(comp)

        Competencia comp2 = new Competencia("test2")
        dao.create(comp2)

        when:
        List<Competencia> list = dao.listAll()

        then:
        assert list.size() == 2
        list[0].name == "test"
        list[1].name == "test2"
    }

    def "verify create method"() {
        when: "create a new competencia"
        dao.create(competencia)

        then: "method should save correctly"
        def resultSet = connection.createStatement().executeQuery("SELECT * FROM \"Competencia\";")
        resultSet.next()
        assert resultSet.getString("name") == competencia.getName()

    }

    def "verify update method"() {
        given:
        Competencia ogComp = competencia
        dao.create(ogComp)
        Long idToUpdate = 1L

        when:
        ogComp.setName("update")
        dao.update(ogComp, idToUpdate)

        then:
        Competencia updated = dao.findById(idToUpdate)
        updated.name == "update"

    }

    def "verify findById method"() {
        given:
        Long id = 1L;

        when:
        dao.create(competencia)

        then:
        Competencia comp = dao.findById(id)
        assert comp.name == competencia.getName()
    }

    def "verify delete method"() {
        given:
        Long id = 1L;

        when:
        dao.create(competencia)

        and:
        dao.delete(id)

        then:
        def deletedResultSet = connection.createStatement().executeQuery("SELECT * FROM \"Competencia\" WHERE id = ${id}")
        assert !deletedResultSet.next()
    }

    def "verify findById throws exception"() {
        when:
        dao.findById(-1)

        then:
        def exception = thrown(NoSuchElementException)
        exception.message == "Essa competencia nao existe"
    }
}
