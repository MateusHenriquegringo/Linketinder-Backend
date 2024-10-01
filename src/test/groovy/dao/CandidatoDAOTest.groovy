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

        candidato = new Candidato(
                "mateus",
                "derossi",
                "henrique@gmail.com",
                "new user",
                "99950000",
                "tapecity",
                "00044499909",
                "senha123"
        )
        dao = new CandidatoDAO(connection)
    }


    def cleanup() {
        def statement = connection.createStatement()
        statement.execute("DROP TABLE candidato")
        statement.close()
        connection.close()
    }

    def "verify list all method"() {
        given:
        Candidato candidato1 = new Candidato("mateus", "derossi", "henrique@gmail.com", "new user", "99950000", "tapecity", "00044499909", "senha123")
        dao.create(candidato1)

        Candidato candidato2 = new Candidato("lucas", "silva", "lucas@gmail.com", "another user", "12345678", "tapecity", "00055588899", "senha456")
        dao.create(candidato2)

        when:
        List<Candidato> list = dao.listAll()

        then:
        assert list.size() == 2
        list[0].first_name == "mateus"
        list[1].first_name == "lucas"
        list[0].email == "henrique@gmail.com"
        list[1].email == "lucas@gmail.com"
    }

    def "verify create method"() {
        when: "create a new candidato"
        dao.create(candidato)

        then: "method should save correctly"
        def resultSet = connection.createStatement().executeQuery("SELECT * FROM candidato WHERE cpf = '00044499909'")
        resultSet.next()
        assert resultSet.getString("first_name") == candidato.getFirst_name()
        assert resultSet.getString("last_name") == candidato.getLast_name()
        assert resultSet.getString("email") == candidato.getEmail()
        assert resultSet.getString("city") == candidato.getCity()
        assert resultSet.getString("cep") == candidato.getCEP()
        assert resultSet.getString("description") == candidato.getDescription()
    }

    def "verify update method"() {
        given:
        Candidato originalCandidato = candidato
        dao.create(originalCandidato)
        Long idToUpdate = 1L

        when:
        originalCandidato.setFirst_name("update")
        originalCandidato.setLast_name("update")

        dao.update(originalCandidato, idToUpdate)

        then:
        Candidato updated = dao.findById(idToUpdate)
        updated.first_name == "update"
        updated.last_name == "update"

    }

    def "verify findById method"() {

        when: "create a new candidato and fetch by ID"
        dao.create(candidato)

        def resultSet = connection.createStatement().executeQuery("SELECT * FROM candidato WHERE CPF = '00044499909'")
        resultSet.next()
        Long id = resultSet.getLong("id")

        then: "findById should return the correct candidato"
        Candidato foundCandidato = dao.findById(id)
        foundCandidato.first_name == "mateus"
        foundCandidato.last_name == "derossi"
        foundCandidato.email == "henrique@gmail.com"
        foundCandidato.city == "tapecity"
        foundCandidato.CEP == "99950000"
        foundCandidato.description == "new user"
    }

    def "verify delete method"() {
        when: "create a new candidato"
        dao.create(candidato)

        def resultSet = connection.createStatement().executeQuery("SELECT * FROM candidato WHERE CPF = '00044499909'")
        resultSet.next()
        Long id = resultSet.getLong("id")

        and: "delete the candidato"
        dao.delete(id)

        then: "candidato should no longer exist in the database"
        def deletedResultSet = connection.createStatement().executeQuery("SELECT * FROM candidato WHERE id = ${id}")
        assert !deletedResultSet.next()
    }

    def "verify findById throws exception"() {
        when:
        dao.findById(-1)

        then:
        def exception = thrown(NoSuchElementException)
        exception.message == "Candidato nao encontrado"
    }

}


