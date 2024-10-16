package dao.auxiliaryTables

import DB.ConnectionFactory
import DB.DBTypes
import dao.CandidatoDAO
import dao.auxiliary.CandidatoCompetenciaDAO
import enums.CompetenciaENUM
import model.Candidato
import spock.lang.Shared
import spock.lang.Specification

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class CandidatoCompetenciaTest extends Specification {

    @Shared
    Connection connection

    @Shared
    Candidato candidato

    @Shared
    CandidatoDAO candidatoDAO

    @Shared
    CandidatoCompetenciaDAO dao

    void setupSpec() {
        connection = ConnectionFactory.getConnection(DBTypes.H2DATABASE)

        Statement stmt = connection.createStatement()
        stmt.execute(
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
""")

        stmt.execute(
                """
CREATE TABLE candidato_competencia (
    id SERIAL PRIMARY KEY,
    candidato_id INT NOT NULL,
    competences VARCHAR(50) NOT NULL CHECK (competences IN (
        'JAVASCRIPT', 'TYPESCRIPT', 'PYTHON', 'JAVA', 'C#', 'PHP', 
        'KOTLIN', 'SWIFT', 'SQL', 'NOSQL', 'HTML', 'CSS', 'REACT', 
        'ANGULAR', 'VUE', 'NODE.JS', 'SPRING BOOT', 'DJANGO', 'DOCKER', 
        'KUBERNETES', 'AWS', 'AZURE', 'GIT', 'DEVOPS', 
        'TEST-DRIVEN DEVELOPMENT', 'CI/CD', 'GO', 'RUST', 'SCALA', 'C++', 
        'GRAPHQL', 'MACHINE LEARNING', 'CYBERSECURITY'
    )),
    FOREIGN KEY (candidato_id) REFERENCES candidato(id) ON DELETE CASCADE
);
"""
        )
    }

    void setup(){
        candidato = new Candidato(
                cpf: '12345678900',
                first_name: 'João',
                last_name: 'Silva',
                email: 'joao.silva@email.com',
                city: 'Cidade X',
                cep: '12345678',
                description: 'Descrição',
                password: 'senha123'
        )
        connection = ConnectionFactory.getConnection(DBTypes.H2DATABASE)
        dao = new CandidatoCompetenciaDAO(connection)
        candidatoDAO = new CandidatoDAO(connection)
    }

    void cleanup(){
        Statement statement = connection.createStatement()
        statement.execute("DELETE FROM candidato")
        statement.execute("DELETE FROM candidato_competencia")

    }

    void cleanupSpec(){
        Statement statement = connection.createStatement()
        statement.execute("DROP TABLE candidato_competencia")
        statement.execute("DROP TABLE candidato")
        statement.close()
        if(!connection.isClosed()) connection.close()
    }


    def "UnitTestCreateAssociationAddCompetênciasCandidato"() {
        given:
        Connection mockConnection = Mock(Connection)
        PreparedStatement mockPreparedStatement = Mock(PreparedStatement)

        def candidatoCompetenciaDAO = new CandidatoCompetenciaDAO(mockConnection)

        Long candidatoID = 1L
        List<CompetenciaENUM> competencias = [CompetenciaENUM.JAVASCRIPT, CompetenciaENUM.PYTHON]

        when:
        candidatoCompetenciaDAO.createAssociation(candidatoID, competencias)

        then:
        1 * mockConnection.prepareStatement(_ as String) >> mockPreparedStatement

        and:
        1 * mockPreparedStatement.setLong(1, candidatoID)
        1 * mockPreparedStatement.setString(2, "JAVASCRIPT")
        1 * mockPreparedStatement.addBatch()

        1 * mockPreparedStatement.setLong(1, candidatoID)
        1 * mockPreparedStatement.setString(2, "PYTHON")
        1 * mockPreparedStatement.addBatch()

        and:
        1 * mockPreparedStatement.executeBatch()

        and:
        1 * mockPreparedStatement.close()
    }

    def "TestCreateAssociationSaveCompetênciasCorrectly"() {
        given:
        long returnedId = candidatoDAO.create(candidato)
        List<CompetenciaENUM> competencias = [CompetenciaENUM.JAVASCRIPT, CompetenciaENUM.PYTHON]

        when:
        dao.createAssociation(returnedId, competencias)

        then:
        def pstmt = connection.prepareStatement("SELECT competences FROM candidato_competencia WHERE candidato_id = ?")
        pstmt.setLong(1, returnedId)
        ResultSet rs = pstmt.executeQuery()

        rs.next() && rs.getString("competences") == "JAVASCRIPT"
        rs.next() && rs.getString("competences") == "PYTHON"
        !rs.next()
    }

    void "testDeleteCompetencesFromCandidato"(){
        given:
        long returnedId = candidatoDAO.create(candidato)
        dao.createAssociation(returnedId, [CompetenciaENUM.JAVASCRIPT, CompetenciaENUM.PYTHON])

        when:
        dao.deleteCompetence(returnedId, CompetenciaENUM.JAVASCRIPT)

        def pstmt = connection.prepareStatement("SELECT competences FROM candidato_competencia WHERE candidato_id = ?")
        pstmt.setLong(1, returnedId)
        ResultSet rs = pstmt.executeQuery()

        then:
        rs.next() && rs.getString("competences") == "PYTHON"
        !rs.next()

    }

    void "testDeleteAllCompetences"(){
        given:
        long returnedId = candidatoDAO.create(candidato)
        dao.createAssociation(returnedId, [CompetenciaENUM.JAVASCRIPT, CompetenciaENUM.PYTHON])

        when:
        dao.deleteAllCompetences(returnedId)

        def pstmt = connection.prepareStatement("SELECT competences FROM candidato_competencia WHERE candidato_id = ?")
        pstmt.setLong(1, returnedId)
        ResultSet rs = pstmt.executeQuery()

        then:
        !rs.next()
    }

    void "testUpdateCompetences"(){
        given:
        long returnedId = candidatoDAO.create(candidato)
        dao.createAssociation(returnedId, [CompetenciaENUM.JAVASCRIPT, CompetenciaENUM.PYTHON])

        when:
        dao.updateCompetences(returnedId, [CompetenciaENUM.ANGULAR, CompetenciaENUM.REACT])

        def pstmt = connection.prepareStatement("SELECT competences FROM candidato_competencia WHERE candidato_id = ?")
        pstmt.setLong(1, returnedId)
        ResultSet rs = pstmt.executeQuery()

        then:
        rs.next() && rs.getString("competences") == "ANGULAR"
        rs.next() && rs.getString("competences") == "REACT"
        !rs.next()

        and:
        when:
        dao.updateCompetences(returnedId, [CompetenciaENUM.PHP])
        def pstmt2 = connection.prepareStatement("SELECT competences FROM candidato_competencia WHERE candidato_id = ?")
        pstmt2.setLong(1, returnedId)
        ResultSet rs2 = pstmt2.executeQuery()

        then:
        rs2.next() && rs2.getString("competences") == "PHP"
        !rs2.next()
    }

    void "testFindById"(){
        given:
        long id = candidatoDAO.create(candidato)
        dao.createAssociation(id, [CompetenciaENUM.ANGULAR, CompetenciaENUM.AWS])

        when:
        def returned = dao.findById(id)

        then:
        returned.getCompetences().size()==2
        with(returned){
            getFirst_name() == candidato.getFirst_name()
            getEmail() == candidato.getEmail()
            getLast_name() == candidato.getLast_name()
            getDescription() == candidato.getDescription()
            getCity() == candidato.getCity()
            getCep() == candidato.getCep()
            getCompetences().contains(CompetenciaENUM.ANGULAR)
            getCompetences().contains(CompetenciaENUM.AWS)
        }
    }

    void "testListAll"(){
        given:
        long returnedId1 = candidatoDAO.create(candidato)
        long returnedId2 = candidatoDAO.create(new Candidato(
                cpf: "0000000",
                cep: "9995000",
                first_name: "mateus",
                last_name: "henrique",
                description: "description",
                city: "city",
                email: "email@user",
                password: "senhaforte"
        ))
        dao.createAssociation(returnedId1, [CompetenciaENUM.AWS, CompetenciaENUM.ANGULAR])
        dao.createAssociation(returnedId2, [CompetenciaENUM.JAVA, CompetenciaENUM.SQL])

        when:
        def list = dao.listAll()

        then:
        list.size() == 2

        def firstCandidate = list.find { it.getId() == returnedId1 }
        assert firstCandidate != null
        assert firstCandidate.getCompetences() == [CompetenciaENUM.AWS, CompetenciaENUM.ANGULAR]
        assert firstCandidate.getEmail() == candidato.getEmail()
        assert firstCandidate.getFirst_name() == candidato.getFirst_name()
        assert firstCandidate.getLast_name() == candidato.getLast_name()

        def secondCandidate = list.find { it.getId() == returnedId2 }
        assert secondCandidate != null
        assert secondCandidate.getCompetences() == [CompetenciaENUM.JAVA, CompetenciaENUM.SQL]
        assert secondCandidate.getEmail() == "email@user"
        assert secondCandidate.getFirst_name() == "mateus"
        assert secondCandidate.getLast_name() == "henrique"

    }
}
