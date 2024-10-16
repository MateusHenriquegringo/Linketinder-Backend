package builder

import enums.CompetenciaENUM
import model.Candidato
import model.builder.CandidatoBuilder
import model.builder.director.CandidatoDirector
import spock.lang.Shared
import spock.lang.Specification

import java.sql.ResultSet

class CandidatoDirectorTest extends Specification {

    @Shared
    CandidatoDirector director

    @Shared
    CandidatoBuilder basicBuilder

    def setup(){
        this.basicBuilder = new CandidatoBuilder()
        this.director = new CandidatoDirector()
    }

    void "testCandidatoBuilder"(){

        when:
        def candidato = basicBuilder.setEmail("email")
                .setCPF("cpf")
                .setCEP("cep")
                .setId(2L)
                .setDescription("description")
                .setCity("city")
                .setFirstName("nome")
                .setLastName("sobrenome")
                .build()

        then:
        candidato
        candidato.getFirst_name() == "nome"
        candidato.getLast_name() == "sobrenome"
        candidato.getId() == 2L
        candidato.getEmail() == "email"
        candidato.class == Candidato.class
    }

    void "testBuildFromResultSet"() {

        def mockResultSet = Mock(ResultSet)

        when:
        mockResultSet.getString("first_name") >> "primeiro nome"
        mockResultSet.getString("last_name") >> "último nome"
        mockResultSet.getString("cep") >> "cep"
        mockResultSet.getString("cpf") >> "cpf"
        mockResultSet.getLong("id") >> 1L
        mockResultSet.getString("description") >> "description"
        mockResultSet.getString("email") >> "email"
        mockResultSet.getString("city") >> "city"

        def candidato = director.constructFromResultSet(mockResultSet, basicBuilder)

        then:
        candidato.getId() == 1L
        candidato.getFirst_name() == "primeiro nome"
        candidato.getLast_name() == "último nome"
        candidato.getCep() == "cep"
        candidato.getCpf() == "cpf"
        candidato.getDescription() == "description"
        candidato.getEmail() == "email"
        candidato.getCity() == "city"
        candidato.getCompetences() == null
        candidato.getPassword() == null
    }

    void "testBuildFromResultSetWithCompetences"() {
        given:
        def mockResultSet = Mock(ResultSet)

        when:
        mockResultSet.getString("first_name") >> "primeiro nome"
        mockResultSet.getString("last_name") >> "último nome"
        mockResultSet.getString("cep") >> "cep"
        mockResultSet.getString("cpf") >> "cpf"
        mockResultSet.getLong("id") >> 1L
        mockResultSet.getString("description") >> "description"
        mockResultSet.getString("email") >> "email"
        mockResultSet.getString("city") >> "city"
        mockResultSet.getString("competences") >> "JAVA, PYTHON"

        def candidato = director.constructFromResultSetWithCompetences(mockResultSet, basicBuilder)

        then:
        candidato.getId() == 1L
        candidato.getFirst_name() == "primeiro nome"
        candidato.getLast_name() == "último nome"
        candidato.getCep() == "cep"
        candidato.getCpf() == "cpf"
        candidato.getDescription() == "description"
        candidato.getEmail() == "email"
        candidato.getCity() == "city"
        candidato.getCompetences().contains(CompetenciaENUM.JAVA)
        candidato.getCompetences().contains(CompetenciaENUM.PYTHON)
        candidato.getPassword() == null
    }

}
