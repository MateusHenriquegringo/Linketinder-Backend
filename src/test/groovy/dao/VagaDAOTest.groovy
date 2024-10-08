package dao

import DB.ConnectionFactory
import DB.DBTypes
import model.Candidato
import model.Vaga
import spock.lang.Shared
import spock.lang.Specification

import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class VagaDAOTest extends Specification {

    @Shared
    private Connection connection = ConnectionFactory.getConnection(DBTypes.H2DATABASE)

    @Shared
    Vaga vaga

    @Shared
    VagaDAO dao

    def setupSpec() {
        def statement = connection.createStatement()

        statement.execute(
                """
        CREATE TABLE IF NOT EXISTS vaga (
            id SERIAL PRIMARY KEY,
            vaga_name VARCHAR(100) NOT NULL,
            description TEXT,
            empresa_id INT NOT NULL,
            state VARCHAR(2) NOT NULL,
            city VARCHAR(100) NOT NULL
);
"""
        )
    }

    def setup() {
        vaga = new Vaga(
                vaga_name: "dev",
                empresaId: 1L,
                state: "RS",
                city: "Tapejara",
                description: "Uma empresa promissora"
        )

        dao = new VagaDAO(connection)
    }


    def cleanup() {
        def statement = connection.createStatement()
        statement.execute("DELETE FROM vaga")
    }

    def cleanupSpec() {
        def statement = connection.createStatement()
        statement.execute("DROP TABLE vaga")
        statement.close()
        connection.close()
    }

    void "testCreateVagaThrowsException"(){
        given:
        vaga
        vaga.setVaga_name(null)

        when:
        dao.create(vaga)

        then:
        def error = thrown(RuntimeException.class)
        error.message.contains("falha ao salvar vaga:")

        and:
        vaga.setVaga_name("dev")
        vaga.setState(null)

        when:
        dao.create(vaga)

        then:
        def error2 = thrown(RuntimeException.class)
        error2.message.contains("falha ao salvar vaga:")
    }

    void "testCreateVagaWorksCorrectly"(){
        given:
        vaga

        when:
        long id = dao.create(vaga)

        then:
        id > 0

        and:
        def pstmt = connection.prepareStatement("SELECT * FROM vaga WHERE id = ?")
        pstmt.setLong(1, id)
        ResultSet resultSet = pstmt.executeQuery()

        resultSet.next()
        resultSet.getString("vaga_name")==vaga.getVaga_name()
        resultSet.getString("description")==vaga.getDescription()
        resultSet.getString("state")==vaga.getState()
        resultSet.getString("city")==vaga.getCity()
        resultSet.getLong("empresa_id")==vaga.getEmpresaId()
    }

    void "testUpdateVagaSuccess"() {
        given:
        def updateVaga = vaga
        updateVaga.setVaga_name("front")

        when:
        long id = dao.create(vaga)
        dao.update(updateVaga, id)

        then:
        def updatedVaga = dao.findById(id)
        updatedVaga.getVaga_name() == "front"
        updatedVaga.getDescription() == vaga.getDescription()

    }

    void "testUpdateVagaThrowsExceptionWhenVagaNotFound"() {
        given:
        def mock = Mock(Vaga)
        when:
        dao.update(mock, -1)

        then:
        def error = thrown(RuntimeException.class)
        error.message.contains("Vaga nao encontrada")
    }


    void "testDeleteCorrectly"() {
        given:
        long id = dao.create(vaga)

        when:
        Statement stmt = connection.createStatement()
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM vaga")

        then:
        resultSet.next()
        resultSet.getRow() == 1

        and:
        dao.delete(id)

        when:
        Statement stmt2 = connection.createStatement()
        ResultSet resultSet2 = stmt2.executeQuery("SELECT * FROM vaga")

        then:
        !resultSet2.next()
        resultSet2.getRow() == 0
    }

    void "tesFindByIdWorksCorrectly"() {
        given:
        long id = dao.create(vaga)

        when:
        Vaga result = dao.findById(id)

        then:
        result.getDescription() == vaga.getDescription()
        result.getEmpresaId() == vaga.getEmpresaId()
        result.getId() == id
        result.getVaga_name() == vaga.getVaga_name()
    }

    void "tesFindByIdWhenIdDoesNotExists"() {
        when:
        dao.findById(-1)

        then:
        def error = thrown(NoSuchElementException)
        error.message.contains("Vaga nao encontrada")
    }

    void "testListAll"(){
            given:
            dao.create(vaga)

            def anotherVaga = new Vaga()
            anotherVaga.setDescription("desc")
            anotherVaga.setCity("city")
            anotherVaga.setVaga_name("back")
            anotherVaga.setEmpresaId(3L)
            anotherVaga.setState("SP")

            dao.create(anotherVaga)

            when:
            def list = dao.listAll()

            then:
            list.size()==2
            list[0].getDescription() == vaga.getDescription()
            list[0].getVaga_name() == vaga.getVaga_name()
            list[1].getDescription() == anotherVaga.getDescription()
            list[1].getVaga_name() == anotherVaga.getVaga_name()
        }
}
