package dao

import DB.ConnectionFactory
import DB.DBTypes
import DB.H2DatabaseConnection
import enums.CompetenciaENUM
import repository.auxiliary.CandidatoCompetenciaDAO
import spock.lang.Shared
import spock.lang.Specification

import java.sql.Connection

class CandidatoCompetenciasTest extends Specification {

    @Shared
    Connection connection

    @Shared
    CandidatoCompetenciaDAO dao

    def setupSpec() {
        connection = ConnectionFactory.getConnection(DBTypes.H2DATABASE)
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
                """
CREATE TYPE competencia_enum AS ENUM (
    'JavaScript',
    'TypeScript',
    'Python',
    'Java',
    'C#',
    'PHP',
    'Kotlin',
    'Swift',
    'SQL',
    'NoSQL'
);

CREATE TABLE candidato_competencia (
    id SERIAL PRIMARY KEY,
    candidato_id INT NOT NULL,
    competences competencia_enum NOT NULL,
    FOREIGN KEY (candidato_id) REFERENCES candidato(id) ON DELETE CASCADE
);
""")

        statement.close()
    }

    def setup() {
        connection = H2DatabaseConnection.getConnection()
        dao = new CandidatoCompetenciaDAO(connection)
    }

    def cleanup() {
        if (connection != null && !connection.isClosed()) {
            def statement = connection.createStatement()
            statement.execute("DELETE FROM candidato_competencia")
            statement.execute("DELETE FROM candidato")
            statement.close()
        }
    }

    def cleanupSpec() {
        def statement = connection.createStatement()
        statement.execute("DROP TABLE candidato_competencia")
        statement.execute("DROP TABLE candidato")
        statement.execute("DROP TYPE competencia_enum")
        statement.close()
    }

    def "verifyAssociateCandidatoToCompetencies"() {
        given:
        def statement = connection.createStatement()
        statement.execute("""
INSERT INTO candidato (CPF, first_name, last_name, email, city, CEP, description, password)
VALUES 
('12345678900', 'John', 'Doe', 'john.doe@example.com', 'New York', '10001', 'Full Stack Developer', 'securepassword123');
""")

        def candidatoId = 1
        List<CompetenciaENUM> competencias = List.of(CompetenciaENUM.JAVASCRIPT, CompetenciaENUM.TYPESCRIPT, CompetenciaENUM.PYTHON)

        when:
        dao.create(candidatoId, competencias)

        then:
        notThrown(RuntimeException)
    }

    def "verifyThrownsExceptionWhenCandidatoDoesNotExists"() {
        given:
        def candidatoId = -1
        List<CompetenciaENUM> competencias = List.of(CompetenciaENUM.JAVASCRIPT, CompetenciaENUM.TYPESCRIPT, CompetenciaENUM.PYTHON)

        when:
        dao.create(candidatoId, competencias)

        then:
        def e = thrown(RuntimeException)
    }


    def "verifyFindByIdReturnCorrectly"() {
        // Implementar um teste para buscar candidato por ID
    }
}
