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
        dao = new CompetenciasDAO(connection)
    }

    def cleanup() {
        def statement = connection.createStatement()
        statement.execute("DROP TABLE competencia_input")
        statement.close()
        connection.close()
    }




}
