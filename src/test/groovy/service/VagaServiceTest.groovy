package service

import enums.CompetenciaENUM
import model.Vaga
import repository.VagaRepository
import service.dto.VagaResponseDTO
import spock.lang.Specification
import spock.lang.Subject

class VagaServiceTest extends Specification{
    VagaRepository vagaRepository = Mock(VagaRepository)
    @Subject VagaService vagaService = new VagaService(vagaRepository)

    def "deve criar uma nova vaga"() {
        given:
        Vaga vaga = new Vaga(id: 1L, vaga_name: "Desenvolvedor Java", description: "Vaga para Dev Java", city: "São Paulo", state: "SP", empresaId: 1L, competences: [CompetenciaENUM.JAVA])

        when:
        vagaService.createVaga(vaga)

        then:
        1 * vagaRepository.createVaga(vaga)
    }

    def "deve deletar uma vaga existente"() {
        given:
        Long id = 1L

        when:
        vagaService.deleteVaga(id)

        then:
        1 * vagaRepository.deleteVaga(id)
    }

    def "deve listar todas as vagas"() {
        given:
        Vaga vaga1 = new Vaga(id: 1L, vaga_name: "Desenvolvedor Java", description: "Vaga para Dev Java", city: "São Paulo", state: "SP", empresaId: 1L, competences: [CompetenciaENUM.JAVA])
        Vaga vaga2 = new Vaga(id: 2L, vaga_name: "Desenvolvedor Kotlin", description: "Vaga para Dev Kotlin", city: "Rio de Janeiro", state: "RJ", empresaId: 2L, competences: [CompetenciaENUM.KOTLIN])

        vagaRepository.listAll() >> [vaga1, vaga2]

        when:
        def result = vagaService.listAll()

        then:
        result.size() == 2
        result[0].vaga_name == "Desenvolvedor Java"
        result[1].vaga_name == "Desenvolvedor Kotlin"
    }

    def "deve encontrar uma vaga pelo id"() {
        given:
        Long id = 1L
        Vaga vaga = new Vaga(id: id, vaga_name: "Desenvolvedor Java", description: "Vaga para Dev Java", city: "São Paulo", state: "SP", empresaId: 1L, competences: [CompetenciaENUM.JAVA])
        vagaRepository.findById(id) >> vaga

        when:
        VagaResponseDTO result = vagaService.findById(id)

        then:
        result.getId() == id
        result.getVaga_name() == "Desenvolvedor Java"
    }

    def "deve editar as competências de uma vaga"() {
        given:
        Long id = 1L
        List<CompetenciaENUM> novasCompetencias = [CompetenciaENUM.PYTHON, CompetenciaENUM.ANGULAR]

        when:
        vagaService.editCompetences(id, novasCompetencias)

        then:
        1 * vagaRepository.editCompetences(id, novasCompetencias)
    }

    def "deve listar todas as vagas de uma empresa"() {
        given:
        Long empresaId = 1L
        Vaga vaga1 = new Vaga(id: 1L, vaga_name: "Desenvolvedor Java", description: "Vaga para Dev Java", city: "São Paulo", state: "SP", empresaId: empresaId, competences: [CompetenciaENUM.JAVA])
        vagaRepository.listAllFromEmpresa(empresaId) >> [vaga1]

        when:
        def result = vagaService.listAllFromEmpresa(empresaId)

        then:
        result.size() == 1
        result[0].getVaga_name() == "Desenvolvedor Java"
    }

    def "deve atualizar uma vaga existente"() {
        given:
        Long id = 1L
        Vaga vagaAtualizada = new Vaga(id: id, vaga_name: "Desenvolvedor Java", description: "Vaga para Dev Java", city: "São Paulo", state: "SP", empresaId: 1L, competences: [CompetenciaENUM.JAVA])

        when:
        vagaService.updateVaga(vagaAtualizada, id)

        then:
        1 * vagaRepository.updateVaga(vagaAtualizada, id)
    }

    def "deve mapear uma Vaga para VagaResponseDTO corretamente"() {
        given:
        Long id = 1L
        Vaga vaga = new Vaga(id: id, vaga_name: "Desenvolvedor Java", description: "Vaga para Dev Java", city: "São Paulo", state: "SP", empresaId: 1L, competences: [CompetenciaENUM.JAVA])
        vagaRepository.findById(id) >> vaga

        when:
        VagaResponseDTO result = vagaService.findById(id)

        then:
        result.getId() == vaga.getId()
        result.getVaga_name() == vaga.getVaga_name()
        result.getDescription() == vaga.getDescription()
        result.getCity() == vaga.getCity()
        result.getState() == vaga.getState()
        result.getEmpresaId() == vaga.getEmpresaId()
        result.getCompetences() == vaga.getCompetences()
    }

}
