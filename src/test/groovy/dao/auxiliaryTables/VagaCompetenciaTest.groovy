package dao.auxiliaryTables

import DB.ConnectionFactory
import DB.DBTypes
import dao.VagaDAO
import dao.auxiliary.VagaCompetenciaDAO
import enums.CompetenciaENUM
import model.Vaga
import spock.lang.Shared
import spock.lang.Specification

import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class VagaCompetenciaTest extends Specification {

    @Shared
    Connection connection

    @Shared
    Vaga vaga

    @Shared
    VagaDAO vagaDAO

    @Shared
    VagaCompetenciaDAO dao

    void setupSpec() {
        connection = ConnectionFactory.getConnection(DBTypes.H2DATABASE)

        Statement stmt = connection.createStatement()
        stmt.execute(
                """
CREATE TABLE  vaga (
    id SERIAL PRIMARY KEY,
    vaga_name VARCHAR(100) NOT NULL,
    description TEXT,
    empresa_id INT NOT NULL,
    state VARCHAR(2) NOT NULL,
    city VARCHAR(100) NOT NULL
);
""")

        stmt.execute(
                """
CREATE TABLE vaga_competencia (
    id SERIAL PRIMARY KEY,
    vaga_id INT NOT NULL,
    competences VARCHAR(50) NOT NULL CHECK (competences IN (
        'JAVASCRIPT', 'TYPESCRIPT', 'PYTHON', 'JAVA', 'C#', 'PHP', 
        'KOTLIN', 'SWIFT', 'SQL', 'NOSQL', 'HTML', 'CSS', 'REACT', 
        'ANGULAR', 'VUE', 'NODE.JS', 'SPRING BOOT', 'DJANGO', 'DOCKER', 
        'KUBERNETES', 'AWS', 'AZURE', 'GIT', 'DEVOPS', 
        'TEST-DRIVEN DEVELOPMENT', 'CI/CD', 'GO', 'RUST', 'SCALA', 'C++', 
        'GRAPHQL', 'MACHINE LEARNING', 'CYBERSECURITY'
    )),
    FOREIGN KEY (vaga_id) REFERENCES vaga(id) ON DELETE CASCADE
);
"""
        )
    }

    void setup() {
        vaga = new Vaga(
                city: 'Cidade X',
                vaga_name: "dev",
                empresaId: 1L,
                state: "RS",
                description: 'Descrição'
        )
        connection = ConnectionFactory.getConnection(DBTypes.H2DATABASE)
        dao = new VagaCompetenciaDAO(connection)
        vagaDAO = new VagaDAO(connection)
    }

    void cleanup() {
        Statement statement = connection.createStatement()
        statement.execute("DELETE FROM vaga")
        statement.execute("DELETE FROM vaga_competencia")

    }

    void cleanupSpec() {
        Statement statement = connection.createStatement()
        statement.execute("DROP TABLE vaga_competencia")
        statement.execute("DROP TABLE vaga")
        statement.close()
        if (!connection.isClosed()) connection.close()
    }

    def "TestCreateAssociationSaveCompetênciasCorrectly"() {
        given:
        long returnedId = vagaDAO.create(vaga)
        List<CompetenciaENUM> competencias = [CompetenciaENUM.JAVASCRIPT, CompetenciaENUM.PYTHON]

        when:
        dao.createAssociation(returnedId, competencias)

        then:
        def pstmt = connection.prepareStatement("SELECT competences FROM vaga_competencia WHERE vaga_id = ?")
        pstmt.setLong(1, returnedId)
        ResultSet rs = pstmt.executeQuery()

        rs.next() && rs.getString("competences") == "JAVASCRIPT"
        rs.next() && rs.getString("competences") == "PYTHON"
        !rs.next()
    }

    void "testDeleteCompetencesFromVaga"() {
        given:
        long returnedId = vagaDAO.create(vaga)
        dao.createAssociation(returnedId, [CompetenciaENUM.JAVASCRIPT, CompetenciaENUM.PYTHON])

        when:
        dao.deleteCompetence(returnedId, CompetenciaENUM.JAVASCRIPT)

        def pstmt = connection.prepareStatement("SELECT competences FROM vaga_competencia WHERE vaga_id = ?")
        pstmt.setLong(1, returnedId)
        ResultSet rs = pstmt.executeQuery()

        then:
        rs.next() && rs.getString("competences") == "PYTHON"
        !rs.next()

    }


    void "testDeleteAllCompetences"() {
        given:
        long returnedId = vagaDAO.create(vaga)
        dao.createAssociation(returnedId, [CompetenciaENUM.JAVASCRIPT, CompetenciaENUM.PYTHON])

        when:
        dao.deleteAllCompetences(returnedId)

        def pstmt = connection.prepareStatement("SELECT competences FROM vaga_competencia WHERE vaga_id = ?")
        pstmt.setLong(1, returnedId)
        ResultSet rs = pstmt.executeQuery()

        then:
        !rs.next()
    }

    void "testUpdateCompetences"() {
        given:
        long returnedId = vagaDAO.create(vaga)
        dao.createAssociation(returnedId, [CompetenciaENUM.JAVASCRIPT, CompetenciaENUM.PYTHON])

        when:
        dao.updateCompetences(returnedId, [CompetenciaENUM.ANGULAR, CompetenciaENUM.REACT])

        def pstmt = connection.prepareStatement("SELECT competences FROM vaga_competencia WHERE vaga_id = ?")
        pstmt.setLong(1, returnedId)
        ResultSet rs = pstmt.executeQuery()

        then:
        rs.next() && rs.getString("competences") == "ANGULAR"
        rs.next() && rs.getString("competences") == "REACT"
        !rs.next()

        and:
        when:
        dao.updateCompetences(returnedId, [CompetenciaENUM.PHP])
        def pstmt2 = connection.prepareStatement("SELECT competences FROM vaga_competencia WHERE vaga_id = ?")
        pstmt2.setLong(1, returnedId)
        ResultSet rs2 = pstmt2.executeQuery()

        then:
        rs2.next() && rs2.getString("competences") == "PHP"
        !rs2.next()
    }

    void "testFindById"() {
        given:
        long id = vagaDAO.create(vaga)
        dao.createAssociation(id, [CompetenciaENUM.ANGULAR, CompetenciaENUM.AWS])

        when:
        def returned = dao.findById(id)

        then:
        returned.getCompetences().size() == 2
        with(returned) {
            getVaga_name() == vaga.getVaga_name()
            getEmpresaId() == vaga.getEmpresaId()
            getDescription() == vaga.getDescription()
            getCity() == vaga.getCity()
            getCompetences().contains(CompetenciaENUM.ANGULAR)
            getCompetences().contains(CompetenciaENUM.AWS)
        }
    }

    void "testListAll"() {
        given:
        long returnedId1 = vagaDAO.create(vaga)
        long returnedId2 = vagaDAO.create(new Vaga(
                city: 'Cidade Y',
                vaga_name: "cloud",
                empresaId: 2L,
                state: "SP",
                description: 'teste'
        ))
        dao.createAssociation(returnedId1, [CompetenciaENUM.AWS, CompetenciaENUM.ANGULAR])
        dao.createAssociation(returnedId2, [CompetenciaENUM.JAVA, CompetenciaENUM.SQL])

        when:
        def list = dao.listAll()

        then:
        list.size() == 2

        def firstVaga = list.find { it.getId() == returnedId1 }
        assert firstVaga != null
        assert firstVaga.getCompetences() == [CompetenciaENUM.AWS, CompetenciaENUM.ANGULAR]
        assert firstVaga.getEmpresaId() == vaga.getEmpresaId()
        assert firstVaga.getVaga_name() == vaga.getVaga_name()
        assert firstVaga.getDescription() == vaga.getDescription()

        def secondVaga = list.find { it.getId() == returnedId2 }
        assert secondVaga != null
        assert secondVaga.getCompetences() == [CompetenciaENUM.JAVA, CompetenciaENUM.SQL]
        assert secondVaga.getEmpresaId() == 2L
        assert secondVaga.getVaga_name() == "cloud"
        assert secondVaga.getDescription() == "teste"

    }
}
