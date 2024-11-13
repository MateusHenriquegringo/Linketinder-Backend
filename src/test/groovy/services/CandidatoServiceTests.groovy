package services

import enums.CompetenciaENUM
import model.Candidato
import repository.CandidatoRepository
import service.CandidatoService
import service.dto.CandidatoResponseDTO
import spock.lang.Specification
import spock.lang.Subject

class CandidatoServiceTests extends Specification {


    CandidatoRepository repository = Mock(CandidatoRepository)
    @Subject
    CandidatoService service = new CandidatoService(repository)

    def "deve criar um novo candidato"() {
        given:
        Candidato candidato = new Candidato(id: 1L, first_name: "João", last_name: "Silva", cpf: "12345678901")

        when:
        service.createCandidato(candidato)

        then:
        1 * repository.createCandidato(candidato)
    }

    void "deve listar todos os candidatos sem competências"() {
        given:
        List<Candidato> candidatos = [
                new Candidato(id: 1L, first_name: "João", last_name: "Silva"),
                new Candidato(id: 2L, first_name: "Maria", last_name: "Oliveira")
        ]
        repository.listAllWithoutCompetences() >> candidatos

        when:
        List<CandidatoResponseDTO> result = service.listAllWithoutCompetences()

        then:
        result.size() == 2
        result[0].first_name == "João"
        result[1].first_name == "Maria"
    }

    void "deve listar todos os candidatos com competências"() {
        given:
        List<Candidato> candidatos = [
                new Candidato(id: 1L, first_name: "Carlos", last_name: "Santos", competences: [CompetenciaENUM.JAVA]),
                new Candidato(id: 2L, first_name: "Ana", last_name: "Lima", competences: [CompetenciaENUM.PYTHON])
        ]
        repository.listAll() >> candidatos

        when:
        List<CandidatoResponseDTO> result = service.listAll()

        then:
        result.size() == 2
        result[0].competences.contains(CompetenciaENUM.JAVA)
        result[1].competences.contains(CompetenciaENUM.PYTHON)
    }

    void "deve encontrar um candidato pelo ID"() {
        given:
        Candidato candidato = new Candidato(id: 1L, first_name: "Laura", last_name: "Mendes")
        repository.findCandidatoById(1L) >> candidato

        when:
        CandidatoResponseDTO result = service.findCandidatoById(1L)

        then:
        result.id == 1L
        result.first_name == "Laura"
    }

    void "deve remover uma competência de um candidato"() {
        given:
        Long candidatoId = 1L
        CompetenciaENUM competencia = CompetenciaENUM.JAVA

        when:
        service.removeCompetence(candidatoId, competencia)

        then:
        1 * repository.removeCompetence(candidatoId, competencia)
    }

    void "deve adicionar competências a um candidato"() {
        given:
        Long candidatoId = 1L
        List<CompetenciaENUM> competencias = [CompetenciaENUM.JAVA, CompetenciaENUM.PYTHON]

        when:
        service.addCompetence(candidatoId, competencias)

        then:
        1 * repository.addCompetence(candidatoId, competencias)
    }

    void "deve deletar um candidato pelo ID"() {
        given:
        Long candidatoId = 1L

        when:
        service.deleteCandidato(candidatoId)

        then:
        1 * repository.deleteCandidato(candidatoId)
    }

    void "deve atualizar dados de um candidato"() {
        given:
        Long candidatoId = 1L
        Candidato candidato = new Candidato(first_name: "Lucas", last_name: "Pereira")

        when:
        service.updateCandidato(candidato, candidatoId)

        then:
        1 * repository.updateCandidato(candidato, candidatoId)
    }

    void "deve atualizar competências de um candidato"() {
        given:
        Long candidatoId = 1L
        List<CompetenciaENUM> competencias = [CompetenciaENUM.AWS, CompetenciaENUM.DOCKER]

        when:
        service.updateCompetences(candidatoId, competencias)

        then:
        1 * repository.updateCompetences(candidatoId, competencias)
    }
}
