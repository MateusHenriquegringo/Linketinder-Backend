package builder

import enums.CompetenciaENUM
import model.Vaga
import model.builder.VagaBuilder
import model.builder.director.VagaDirector
import spock.lang.Shared
import spock.lang.Specification

import java.sql.ResultSet

class VagaDirectorTest extends Specification {

    @Shared
    VagaDirector director

    @Shared
    VagaBuilder builder

    def setup() {
        this.director = new VagaDirector()
        this.builder = new VagaBuilder()
    }

    void "testVagaBuilder"() {
        when:
        def vaga = builder
                .setId(2L)
                .setDescription("description")
                .setCity("city")
                .setState("RS")
                .setVagaName("nome")
                .setEmpresaID(1L)
                .build()

        then:
        vaga
        vaga.getVaga_name() == "nome"
        vaga.getDescription() == "description"
        vaga.getCity() == "city"
        vaga.getState() == "RS"
        vaga.getId() == 2L
        vaga.getEmpresaId() == 1L
        vaga.class == Vaga.class
    }

    void "testBuildFromResultSet"() {
        given:
        def mockResultSet = Mock(ResultSet)

        when:
        mockResultSet.getString("vaga_name") >> "primeiro nome"
        mockResultSet.getLong("id") >> 1L
        mockResultSet.getLong("empresa_id") >> 1L
        mockResultSet.getString("description") >> "description"
        mockResultSet.getString("state") >> "RS"
        mockResultSet.getString("city") >> "city"

        def vaga = director.constructFromResultSet(mockResultSet, builder)

        then:
        vaga.getId() == 1L
        vaga.getVaga_name() == "primeiro nome"
        vaga.getEmpresaId() == 1L
        vaga.getState() == "RS"
        vaga.getCity() == "city"
        vaga.getDescription() == "description"
        vaga.getCompetences() == null
    }

    void "testBuildFromResultSetWithCompetences"() {
        given:
        def mockResultSet = Mock(ResultSet)

        when:
        mockResultSet.getString("vaga_name") >> "primeiro nome"
        mockResultSet.getLong("id") >> 1L
        mockResultSet.getLong("empresa_id") >> 1L
        mockResultSet.getString("description") >> "description"
        mockResultSet.getString("state") >> "RS"
        mockResultSet.getString("city") >> "city"
        mockResultSet.getString("competences") >> "JAVA, ANGULAR"

        def vaga = director.constructFromResultSetWithCompetences(mockResultSet, builder)

        then:
        vaga.getId() == 1L
        vaga.getVaga_name() == "primeiro nome"
        vaga.getEmpresaId() == 1L
        vaga.getState() == "RS"
        vaga.getCity() == "city"
        vaga.getDescription() == "description"
        vaga.getCompetences().contains(CompetenciaENUM.JAVA)
        vaga.getCompetences().contains(CompetenciaENUM.ANGULAR)
        vaga.getCompetences().size() == 2
    }


}
