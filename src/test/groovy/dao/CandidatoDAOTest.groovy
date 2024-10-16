package dao

import DB.ConnectionFactory
import DB.DBTypes
import model.Candidato
import spock.lang.Shared
import spock.lang.Specification

import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class CandidatoDAOTest extends Specification {

    @Shared
    private Connection connection = ConnectionFactory.getConnection(DBTypes.H2DATABASE)

    @Shared
    Candidato candidato

    @Shared
    CandidatoDAO dao

    def setupSpec() {
        def statement = connection.createStatement()

        statement.execute(
                """
        CREATE TABLE candidato (
         id SERIAL PRIMARY KEY,
         CPF VARCHAR NOT NULL,
         first_name VARCHAR(50) NOT NULL,
         last_name VARCHAR(50) NOT NULL,
         email VARCHAR(100) NOT NULL UNIQUE,
         city VARCHAR(100) NOT NULL,
         CEP VARCHAR(9) NOT NULL,
         description TEXT,
         password VARCHAR(100) NOT NULL
);
""")
    }

    def setup() {
        candidato = new Candidato(
                cpf: "12345678901",
                first_name: "João",
                last_name: "Silva",
                email: "joao.silva@example.com",
                city: "São Paulo",
                cep: "01001000",
                description: "Um candidato promissor",
                password: "senhaSegura123"
        )

        dao = new CandidatoDAO(connection)
    }


    def cleanup() {
        def statement = connection.createStatement()
        statement.execute("DELETE FROM candidato")
    }

    def cleanupSpec() {
        def statement = connection.createStatement()
        statement.execute("DROP TABLE candidato")
        statement.close()
        connection.close()
    }

    void "testCreateCandidatoWorksCorrectly"() {
        given:
        candidato

        when:
        long id = dao.create(candidato)

        then:
        id > 0

        and:
        def pstmt = connection.prepareStatement("SELECT * FROM candidato WHERE id = ?")
        pstmt.setLong(1, id)
        ResultSet resultSet = pstmt.executeQuery()

        resultSet.next()
        resultSet.getString("CPF") == candidato.getCpf()
        resultSet.getString("CEP") == candidato.getCep()
        resultSet.getString("first_name") == candidato.getFirst_name()
        resultSet.getString("last_name") == candidato.getLast_name()
        resultSet.getString("description") == candidato.getDescription()
        resultSet.getString("email") == candidato.getEmail()
        resultSet.getString("city") == candidato.getCity()

    }

    void "testCreateCandidatoThrowsException"() {
        given:
        candidato
        candidato.setEmail(null)

        when:
        dao.create(candidato)

        then:
        def error = thrown(RuntimeException.class)
        error.message.contains("ocorreu um erro ao salvar")

        and:
        candidato.setEmail("emailvalido@email")
        candidato.setCpf(null)

        when:
        dao.create(candidato)

        then:
        def error2 = thrown(RuntimeException.class)
        error2.message.contains("ocorreu um erro ao salvar")


    }

    void "testCreateCandidatoThrowsExceptionWhenEmailDuplicate"() {
        given:
        candidato
        def anotherCandidato = candidato

        when:
        dao.create(candidato)
        dao.create(anotherCandidato)

        then:
        def error = thrown(RuntimeException.class)
        error.message.contains("ocorreu um erro ao salvar")

        and:
        def pstmt = connection.prepareStatement("SELECT * FROM candidato")
        ResultSet resultSet = pstmt.executeQuery()

        resultSet.next()
        resultSet.getRow() == 1
    }

    void "testUpdateCandidatoSuccess"() {
        given:
        def updateCandidato = candidato
        updateCandidato.setFirst_name("mateus")

        when:
        long id = dao.create(candidato)
        dao.update(updateCandidato, id)

        then:
        def updatedCandidato = dao.findById(id)
        updatedCandidato.first_name == "mateus"
        updatedCandidato.last_name == candidato.getLast_name()

    }

    void "testUpdateCandidatoThrowsExceptionWhenEmailInvalid"() {
        given:
        Long id = dao.create(candidato)

        candidato.setEmail(null)

        when:
        dao.update(candidato, id)

        then:
        def error = thrown(RuntimeException.class)
        error.message.contains("ocorreu um erro ao editar")
    }

    void "testUpdateCandidatoThrowsExceptionWhenCandidatoNotFound"() {
        given:
        def mock = Mock(Candidato)
        when:
        dao.update(mock, -1)

        then:
        def error = thrown(RuntimeException.class)
        error.message.contains("Candidato nao encontrado")
    }

    void "testDeleteCorrectly"() {
        given:
        long id = dao.create(candidato)

        when:
        Statement stmt = connection.createStatement()
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM candidato")

        then:
        resultSet.next()
        resultSet.getRow() == 1

        and:
        dao.delete(id)

        when:
        Statement stmt2 = connection.createStatement()
        ResultSet resultSet2 = stmt2.executeQuery("SELECT * FROM candidato")

        then:
        !resultSet2.next()
        resultSet2.getRow() == 0
    }


    void "testListAllAFterAndBeforeDelete"() {
        given:
        dao.create(candidato)
        def anotherCandidato = candidato
        anotherCandidato.setEmail("outroemail")
        long id = dao.create(anotherCandidato)

        when:
        def list = dao.listAll()

        then:
        list.size() == 2

        and:
        dao.delete(id)

        when:
        def list2 = dao.listAll()

        then:
        list2.size() == 1
    }

    void "tesFindByIdWorksCorrectly"() {
        given:
        long id = dao.create(candidato)

        when:
        Candidato result = dao.findById(id)

        then:
        result.getLast_name() == candidato.getLast_name()
        result.getCompetences() == null
        result.getFirst_name() == candidato.getFirst_name()
        result.getEmail() == candidato.getEmail()
        result.getId() == id

    }

    void "tesFindByIdWhenIdDoesNotExists"() {
        when:
        dao.findById(-1)

        then:
        def error = thrown(NoSuchElementException)
        error.message.contains("Candidato nao encontrado")

    }

    void "testFindByIdAfterDelete"() {
        given:
        long id = dao.create(candidato)

        when:
        Candidato result = dao.findById(id)

        then:
        result.getLast_name() == candidato.getLast_name()
        result.getCompetences() == null
        result.getFirst_name() == candidato.getFirst_name()
        result.getEmail() == candidato.getEmail()
        result.getId() == id

        when:
        dao.delete(id)
        dao.findById(id)


        then:
        def error = thrown(NoSuchElementException)
        error.message.contains("Candidato nao encontrado")
    }

    void "testListAll"(){
        given:
        dao.create(candidato)

        def anotherCandidato = new Candidato()

        anotherCandidato.setDescription("desc")
        anotherCandidato.setFirst_name("name")
        anotherCandidato.setLast_name("last_name")
        anotherCandidato.setCity("city")
        anotherCandidato.setCep("cep")
        anotherCandidato.setCpf("cpf")
        anotherCandidato.setPassword("supersenha")
        anotherCandidato.setEmail("outroEmail")

        dao.create(anotherCandidato)

        when:
        def list = dao.listAll()

        then:
        list.size()==2
        list[0].getEmail() == candidato.getEmail()
        list[0].getFirst_name() == candidato.getFirst_name()
        list[1].getEmail() == anotherCandidato.getEmail()
        list[1].getFirst_name() == anotherCandidato.getFirst_name()
    }
}


