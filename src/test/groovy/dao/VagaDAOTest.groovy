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
        dao = new VagaDAO(connection)
    }

    def cleanup() {
        def statement = connection.createStatement()
        statement.execute("DROP TABLE vaga")
        statement.close()
        connection.close()
    }



}
