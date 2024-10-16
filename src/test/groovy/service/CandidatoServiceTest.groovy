package service

import enums.CompetenciaENUM
import model.Candidato
import repository.CandidatoRepository
import service.dto.CandidatoResponseDTO
import spock.lang.Specification
import spock.lang.Subject

class CandidatoServiceTest extends Specification{

    CandidatoRepository repository = Mock(CandidatoRepository)

    @Subject
    CandidatoService candidatoService = new CandidatoService(repository)

    def "should create a candidato"() {
        given:
        Candidato candidato = new Candidato(
                id: 1L,
                first_name: "John",
                last_name: "Doe",
                cpf: "12345678900",
                description: "Software Developer",
                email: "john.doe@example.com",
                cep: "12345678",
                city: "Tapejara",
                competences: [CompetenciaENUM.JAVA, CompetenciaENUM.ANGULAR]
        )

        when:
        candidatoService.createCandidato(candidato)

        then:
        1 * repository.createCandidato(candidato)
    }

    def "should list all candidatos without competences"() {
        given:
        List<Candidato> candidatos = [
                new Candidato(id: 1L, first_name: "John", last_name: "Doe"),
                new Candidato(id: 2L, first_name: "Jane", last_name: "Doe")
        ]
        repository.listAllWithoutCompetences() >> candidatos

        when:
        List<CandidatoResponseDTO> result = candidatoService.listAllWithoutCompetences()

        then:
        result.size() == 2
        result[0].getFirst_name() == "John"
        result[1].getFirst_name() == "Jane"
    }

    def "should list all candidatos"() {
        given:
        List<Candidato> candidatos = [
                new Candidato(id: 1L, first_name: "John", last_name: "Doe"),
                new Candidato(id: 2L, first_name: "Jane", last_name: "Doe")
        ]
        repository.listAll() >> candidatos

        when:
        List<CandidatoResponseDTO> result = candidatoService.listAll()

        then:
        result.size() == 2
        result[0].getFirst_name() == "John"
        result[1].getFirst_name() == "Jane"
    }

    def "should find a candidato by id"() {
        given:
        Candidato candidato = new Candidato(id: 1L, first_name: "John", last_name: "Doe")
        repository.findCandidatoById(1L) >> candidato

        when:
        CandidatoResponseDTO result = candidatoService.findCandidatoById(1L)

        then:
        result.getId() == candidato.getId()
        result.getFirst_name() == candidato.getFirst_name()
    }

    def "should remove a competence from candidato"() {
        given:
        Long candidatoId = 1L
        CompetenciaENUM competence = CompetenciaENUM.JAVA

        when:
        candidatoService.removeCompetence(candidatoId, competence)

        then:
        1 * repository.removeCompetence(candidatoId, competence)
    }

    def "should add competences to candidato"() {
        given:
        Long candidatoId = 1L
        List<CompetenciaENUM> competences = [CompetenciaENUM.JAVA, CompetenciaENUM.ANGULAR]

        when:
        candidatoService.addCompetence(candidatoId, competences)

        then:
        1 * repository.addCompetence(candidatoId, competences)
    }

    def "should delete a candidato"() {
        given:
        Long candidatoId = 1L

        when:
        candidatoService.deleteCandidato(candidatoId)

        then:
        1 * repository.deleteCandidato(candidatoId)
    }

    def "should update a candidato"() {
        given:
        Candidato candidato = new Candidato(id: 1L, first_name: "John", last_name: "Doe")
        Long candidatoId = 1L

        when:
        candidatoService.updateCandidato(candidato, candidatoId)

        then:
        1 * repository.updateCandidato(candidato, candidatoId)
    }

    def "should update competences of candidato"() {
        given:
        Long candidatoId = 1L
        List<CompetenciaENUM> competences = [CompetenciaENUM.JAVA, CompetenciaENUM.ANGULAR]

        when:
        candidatoService.updateCompetences(candidatoId, competences)

        then:
        1 * repository.updateCompetences(candidatoId, competences)
    }

    def "should find Candidato by id and map to CandidatoResponseDTO"() {
        given:
        Candidato candidato = new Candidato(
                id: 1L,
                first_name: "John",
                last_name: "Doe",
                cpf: "12345678900",
                description: "Software Developer",
                email: "john.doe@example.com",
                cep: "12345678",
                city: "Tapejara",
                competences: [CompetenciaENUM.JAVA, CompetenciaENUM.ANGULAR]
        )

        repository.findCandidatoById(1L) >> candidato

        when:
        CandidatoResponseDTO result = candidatoService.findCandidatoById(1L)

        then:
        result.getId() == candidato.getId()
        result.getFirst_name() == candidato.getFirst_name()
        result.getLast_name() == candidato.getLast_name()
        result.getCPF() == candidato.getCpf()
        result.getDescription() == candidato.getDescription()
        result.getEmail() == candidato.getEmail()
        result.getCEP() == candidato.getCep()
        result.getCity() == candidato.getCity()
        result.getCompetences() == candidato.getCompetences()
    }

    def "should list all Candidatos and map to CandidatoResponseDTO"() {
        given:
        List<Candidato> candidatos = [
                new Candidato(id: 1L, first_name: "John", last_name: "Doe", cpf: "12345678900",
                        description: "Software Developer", email: "john.doe@example.com",
                        cep: "12345678", city: "Tapejara",
                        competences: [CompetenciaENUM.JAVA, CompetenciaENUM.ANGULAR]),
                new Candidato(id: 2L, first_name: "Jane", last_name: "Smith", cpf: "98765432100",
                        description: "Frontend Developer", email: "jane.smith@example.com",
                        cep: "87654321", city: "Tapejara",
                        competences: [CompetenciaENUM.ANGULAR])
        ]

        repository.listAll() >> candidatos

        when:
        List<CandidatoResponseDTO> results = candidatoService.listAll()

        then:
        results.size() == 2
        results[0].getId() == candidatos[0].getId()
        results[0].getFirst_name() == candidatos[0].getFirst_name()
        results[1].getId() == candidatos[1].getId()
        results[1].getFirst_name() == candidatos[1].getFirst_name()
    }
}
