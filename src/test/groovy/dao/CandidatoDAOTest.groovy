package dao

import repository.CandidatoDAO
import DB.H2DatabaseConnection
import model.Candidato
import spock.lang.Shared
import spock.lang.Specification
import java.sql.Connection

class CandidatoDAOTest extends Specification {

    Connection connection = H2DatabaseConnection.getConnection()

    @Shared
    Candidato candidato

    @Shared
    CandidatoDAO dao

    def setup() {
        def statement = connection.createStatement()

        statement.execute(
                """
         CREATE TABLE candidato (
    id SERIAL PRIMARY KEY,
    CPF VARCHAR NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    CEP VARCHAR(9) NOT NULL,
    description TEXT,
    password VARCHAR(100) NOT NULL
);
"""
        )
        dao = new CandidatoDAO(connection)
    }


    def cleanup() {
        def statement = connection.createStatement()
        statement.execute("DROP TABLE candidato")
        statement.close()
        connection.close()
    }

}


