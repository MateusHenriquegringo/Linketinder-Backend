package dao

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



    def "verify create method"() {
        when: "create a new competencia"
        dao.create(competencia)

        then: "method should save correctly"
        def resultSet = connection.createStatement().executeQuery("SELECT * FROM competencia_input;")
        resultSet.next()
        assert resultSet.getString("description") == competencia.getDescription()

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

}
