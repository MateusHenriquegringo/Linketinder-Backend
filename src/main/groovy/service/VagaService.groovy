package service


import enums.CompetenciaENUM
import model.Vaga
import repository.ModelsCRUD
import repository.VagaDAO
import repository.auxiliary.AuxiliaryTablesCRUD
import repository.auxiliary.VagaCompetenciaDAO


class VagaService {

    private ModelsCRUD<Vaga, Long> vagaRepository = new VagaDAO()
    private AuxiliaryTablesCRUD<Vaga, Long, CompetenciaENUM> vagaCompetenciaRepository = new VagaCompetenciaDAO()

    void createVaga(Vaga request) {
        Long returnedID = vagaRepository.create(request)
        addCompetencesIfPresent(returnedID, request.getCompetences())
    }

    Vaga findVagaById(Long id) {
        return vagaRepository.findById(id)
    }

    List<Vaga> listAll() {
        return vagaRepository.listAll()
    }

    void deleteVaga(Long id) {
        vagaRepository.delete(id)
    }

    void updateVaga(Vaga request, Long id) {
        vagaRepository.update(request, id)

        vagaCompetenciaRepository.deleteAllCompetences(id)
        addCompetencesIfPresent(request.getCompetences())
    }


    private void addCompetencesIfPresent(Long vagaID, List<CompetenciaENUM> competences) {
        Optional.ofNullable(competences)
                .filter(comp -> !comp.isEmpty() && comp !== null)
                .ifPresent(comp -> vagaCompetenciaRepository.create(vagaID, comp))
    }

}
