package builder

import model.Candidato
import model.Empresa
import model.builder.CandidatoBuilder
import model.builder.EmpresaBuilder
import model.builder.director.CandidatoDirector
import model.builder.director.EmpresaDirector
import spock.lang.Shared
import spock.lang.Specification

import java.sql.ResultSet

class EmpresaDirectorTest extends Specification {

    @Shared
    EmpresaDirector director

    @Shared
    EmpresaBuilder basicBuilder

    def setup() {
        this.basicBuilder = new EmpresaBuilder()
        this.director = new EmpresaDirector()
    }

    void "testCandidatoBuilder"() {

        when:
        def empresa = basicBuilder.setEmail("email")
                .setCountry("brasil")
                .setCNPJ("999333000")
                .setPassword("senhaforte")
                .setDescription("empresa promissora")
                .setEmpresaName("adidas")
                .setEmail("adidas@gmail")
                .build()

        then:
        empresa
        empresa.getCountry() == "brasil"
        empresa.getEmpresa_name() == "adidas"
        empresa.getDescription() == "empresa promissora"
        empresa.getEmail() == "adidas@gmail"
        empresa.getCNPJ() == "999333000"
        empresa.class == Empresa.class
    }

    void "testBuildFromResultSet"() {

        def mockResultSet = Mock(ResultSet)

        when:
        mockResultSet.getString("empresa_name") >> "empresa name"
        mockResultSet.getString("cep") >> "cep"
        mockResultSet.getString("cnpj") >> "cnpj"
        mockResultSet.getLong("id") >> 1L
        mockResultSet.getString("description") >> "description"
        mockResultSet.getString("email") >> "email"
        mockResultSet.getString("country") >> "country"

        def empresa = director.constructFromResultSet(mockResultSet, basicBuilder)

        then:
        empresa.getEmpresa_name() =="empresa name"
        empresa.getCNPJ() == "cnpj"
        empresa.getId() == 1L
        empresa.getCEP() == "cep"
        empresa.getDescription() == "description"
        empresa.getEmail() == "email"
        empresa.getPassword() == null
    }

    void "testConstructWithCompetencesReturnNull" () {
        given:
        def mockResultSet = Mock(ResultSet)

        when:
        def result = director.constructFromResultSetWithCompetences(mockResultSet, basicBuilder)

        then:
        result == null
    }
}
