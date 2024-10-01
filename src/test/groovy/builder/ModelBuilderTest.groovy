package builder

import model.Candidato
import model.Vaga
import model.builder.Builder
import model.builder.CandidatoBuilder
import model.builder.VagaBuilder
import spock.lang.Specification

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.sql.ResultSet

class ModelBuilderTest extends Specification {

    private Builder<Candidato> candidatoBuilder = new CandidatoBuilder()
    private Builder<Vaga> vagaBuilder = new VagaBuilder()

    void "testBuildModelFromResultSetForCandidatoWhenCompetencesEmpty"(){
        given:
        ResultSet resultSet = mock(ResultSet.class)
        when(resultSet.getLong("id")).thenReturn(1L)
        when(resultSet.getString("first_name")).thenReturn("Mateus")
        when(resultSet.getString("last_name")).thenReturn("Derossi")
        when(resultSet.getString("email")).thenReturn("mateus@gmail.com")
        when(resultSet.getString("description")).thenReturn("description")
        when(resultSet.getString("cep")).thenReturn("99950000")
        when(resultSet.getString("city")).thenReturn("city")
        when(resultSet.getString("cpf")).thenReturn("0000000000")

        when(resultSet.getString("competences")).thenReturn(null)

        when:
        Candidato candidato = candidatoBuilder.buildModelFromResultSet(resultSet)

        then:
        assertNotNull(candidato);
        assertEquals(1L, candidato.getId());
        assertEquals("Mateus", candidato.getFirst_name());
        assertEquals("Derossi", candidato.getLast_name());
        assertEquals("mateus@gmail.com", candidato.getEmail());
        assertEquals("description", candidato.getDescription());
        assertEquals("99950000", candidato.getCEP());
        assertEquals("city", candidato.getCity());
        assertEquals("0000000000", candidato.getCPF());
        assertNull(candidato.getCompetences())
    }


    void "testBuildModelFromResultSetWhenCompetencesIsNotEmpty"(){
        given:
        ResultSet resultSet = mock(ResultSet.class)
        when(resultSet.getLong("id")).thenReturn(1L)
        when(resultSet.getString("first_name")).thenReturn("Mateus")
        when(resultSet.getString("last_name")).thenReturn("Derossi")
        when(resultSet.getString("email")).thenReturn("mateus@gmail.com")
        when(resultSet.getString("description")).thenReturn("description")
        when(resultSet.getString("cep")).thenReturn("99950000")
        when(resultSet.getString("city")).thenReturn("city")
        when(resultSet.getString("cpf")).thenReturn("0000000000")

        when(resultSet.getString("competences")).thenReturn("Java, Python")

        when:
        Candidato candidato = candidatoBuilder.buildModelFromResultSet(resultSet)

        then:
        assertNotNull(candidato);
        assertEquals(1L, candidato.getId());
        assertEquals("Mateus", candidato.getFirst_name());
        assertEquals("Derossi", candidato.getLast_name());
        assertEquals("mateus@gmail.com", candidato.getEmail());
        assertEquals("description", candidato.getDescription());
        assertEquals("99950000", candidato.getCEP());
        assertEquals("city", candidato.getCity());
        assertEquals("0000000000", candidato.getCPF());
        assertTrue(candidato.getCompetences().size()==2);
        assertTrue(candidato.getCompetences().contains("Java"));
        assertTrue(candidato.getCompetences().contains("Python"));
    }


    void "testBuildModelFromResultSetForVagaWhenCompetencesEmpty"(){
        given:
        ResultSet resultSet = mock(ResultSet.class)
        when(resultSet.getLong("id")).thenReturn(1L)
        when(resultSet.getString("vaga_name")).thenReturn("vaga")
        when(resultSet.getString("description")).thenReturn("description")
        when(resultSet.getString("state")).thenReturn("state")
        when(resultSet.getString("city")).thenReturn("city")
        when(resultSet.getLong("empresa_id")).thenReturn(2L)

        when(resultSet.getString("competences")).thenReturn(null)

        when:
        Vaga vaga = vagaBuilder.buildModelFromResultSet(resultSet)

        then:
        assertNotNull(vaga)
        assertEquals(1L, vaga.getId())
        assertEquals("vaga", vaga.getVaga_name())
        assertEquals("description", vaga.getDescription())
        assertEquals("state", vaga.getState())
        assertEquals("city", vaga.getCity())
        assertEquals(2L, vaga.getEmpresaId())
        assertNull(vaga.getCompetences())
    }

    void "testBuildModelFromResultSetForVagaWhenCompetencesIsNotEmpty"(){
        given:
        ResultSet resultSet = mock(ResultSet.class)
        when(resultSet.getLong("id")).thenReturn(1L)
        when(resultSet.getString("vaga_name")).thenReturn("vaga")
        when(resultSet.getString("description")).thenReturn("description")
        when(resultSet.getString("state")).thenReturn("state")
        when(resultSet.getString("city")).thenReturn("city")
        when(resultSet.getLong("empresa_id")).thenReturn(2L)

        when(resultSet.getString("competences")).thenReturn("Java, Python")

        when:
        Vaga vaga = vagaBuilder.buildModelFromResultSet(resultSet)

        then:
        assertNotNull(vaga)
        assertEquals(1L, vaga.getId())
        assertEquals("vaga", vaga.getVaga_name())
        assertEquals("description", vaga.getDescription())
        assertEquals("state", vaga.getState())
        assertEquals("city", vaga.getCity())
        assertEquals(2L, vaga.getEmpresaId())
        assertTrue(vaga.getCompetences().size() == 2)
        assertTrue(vaga.getCompetences().contains("Java"))
        assertTrue(vaga.getCompetences().contains("Python"))

    }


}
