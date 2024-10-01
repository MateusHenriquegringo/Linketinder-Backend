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
                """
        CREATE TABLE competencia_input (
              id SERIAL PRIMARY KEY,
              description VARCHAR(50) NOT NULL
);

"""
        )

        competencia = new Competencia("Analise de Requisitos")
        dao = new CompetenciasDAO(connection)
    }

    def cleanup() {
        def statement = connection.createStatement()
        statement.execute("DROP TABLE competencia_input")
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
        list[0].description == "test"
        list[1].description == "test2"
    }

    def "verify create method"() {
        when: "create a new competencia"
        dao.create(competencia)

        then: "method should save correctly"
        def resultSet = connection.createStatement().executeQuery("SELECT * FROM competencia_input;")
        resultSet.next()
        assert resultSet.getString("description") == competencia.getDescription()

    }

    def "verify update method"() {
        given:
        Competencia ogComp = competencia
        dao.create(ogComp)
        Long idToUpdate = 1L

        when:
        ogComp.setDescription("update")
        dao.update(ogComp, idToUpdate)

        then:
        Competencia updated = dao.findById(idToUpdate)
        updated.description == "update"

    }

    def "verify findById method"() {
        given:
        Long id = 1L;

        when:
        dao.create(competencia)

        then:
        Competencia comp = dao.findById(id)
        assert comp.description == competencia.getDescription()
    }

    def "verify delete method"() {
        given:
        Long id = 1L;

        when:
        dao.create(competencia)

        and:
        dao.delete(id)

        then:
        def deletedResultSet = connection.createStatement().executeQuery("SELECT * FROM competencia_input WHERE id = ${id}")
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
