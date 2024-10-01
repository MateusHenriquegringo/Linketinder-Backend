package dao

import DB.H2DatabaseConnection
import model.Candidato
import repository.auxiliary.CandidatoCompetenciaDAO
import spock.lang.*
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import java.sql.Connection
import java.sql.ResultSet

class CandidatoCompetenciasTest extends Specification {

    @Shared
    Connection connection;

    @Shared
    CandidatoCompetenciaDAO dao

    def setupSpec(){
        connection = H2DatabaseConnection.getConnection()
        def statement = connection.createStatement()

        statement.execute(
                """
CREATE TABLE IF NOT EXISTS candidato (
    id SERIAL PRIMARY KEY,
    CPF VARCHAR NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    CEP VARCHAR(9) NOT NULL,
    description TEXT,
    password VARCHAR(100) NOT NULL
)
""")

        statement.execute(
                " CREATE TABLE competencia_by_enum ( id SERIAL PRIMARY KEY, description VARCHAR(50) NOT NULL);")

        statement.execute(
                """
                      CREATE TABLE candidato_competencia ( id SERIAL PRIMARY KEY,  candidato_id INT NOT NULL, competencia_id INT NOT NULL,
                      FOREIGN KEY (candidato_id) REFERENCES candidato(id) ON DELETE CASCADE,
                      FOREIGN KEY (competencia_id) REFERENCES competencia_by_enum(id) ON DELETE CASCADE
);""")
        statement.execute(
                """ INSERT INTO competencia_by_enum (description) VALUES('JavaScript'),('TypeScript'),('Python'),('Java'),('C#'),
                                                        ('PHP'),('Kotlin'),('Swift'),('SQL'),('NoSQL')
"""
        )
        statement.close()

    }

    def setup() {
        connection = H2DatabaseConnection.getConnection();
        dao = new CandidatoCompetenciaDAO(connection)
    }

    def cleanup(){
        if (connection != null && !connection.isClosed()) {
            def statement = connection.createStatement()
            statement.execute("DELETE FROM candidato_competencia")
            statement.execute("DELETE FROM candidato")
            statement.execute("DELETE FROM competencia_by_enum")
            statement.close()
        }
    }

    def cleanupSpec(){
        def statement = connection.createStatement()
        statement.execute("DROP TABLE candidato_competencia")
        statement.execute("DROP TABLE candidato")
        statement.execute("DROP TABLE competencia_by_enum")
        statement.close()
    }

    def "verifyAssociateCandidatoToCompetencies"(){
        given:
        def statement = connection.createStatement()
        statement.execute( """
INSERT INTO candidato (CPF, first_name, last_name, email, city, CEP, description, password)
VALUES 
('12345678900', 'John', 'Doe', 'john.doe@example.com', 'New York', '10001', 'Full Stack Developer', 'securepassword123');

""")

        def candidatoId = 1
        List<Long> competenciasId = List.of(1L,2L,3L)

        when:
        dao.create(candidatoId, competenciasId)

        then:
        notThrown(RuntimeException)
    }

    def "verifyThrownsExceptionWhenCandidatoDoesNotExists"(){
        given:
        def candidatoId = -1
        List<Long> competenciasId = List.of(1L,2L,3L)

        when:
        dao.create(candidatoId, competenciasId)

        then:
        def e = thrown(RuntimeException)
        e.message.contains("erro ao relacionar competencias")
    }

    def "verifyThrownsExceptionWhenCompetenciaDoesNotExists"(){
        given:
        def statement = connection.createStatement()
        statement.execute( """
INSERT INTO candidato (CPF, first_name, last_name, email, city, CEP, description, password)
VALUES 
('12345678900', 'John', 'Doe', 'john.doe@example.com', 'New York', '10001', 'Full Stack Developer', 'securepassword123');

""")
        def candidatoId = 1
        List<Long> competenciasId = List.of(1L,2L,-3L)

        when:
        dao.create(candidatoId, competenciasId)

        then:
        def e = thrown(RuntimeException)
        e.message.contains("erro ao relacionar competencias")
    }

    def "verifyFindByIdReturnCorrectly"(){
    }
}
