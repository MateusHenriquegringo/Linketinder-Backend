package service


import enums.CompetenciaENUM
import model.Vaga
import repository.ModelsCRUD
import repository.VagaDAO
import repository.auxiliary.AuxiliaryTablesCRUD
import repository.auxiliary.VagaCompetenciaDAO

import java.util.stream.Collectors

class VagaService {

    private ModelsCRUD<Vaga, Long> vagaRepository = new VagaDAO()
    private AuxiliaryTablesCRUD<Vaga, Long, CompetenciaENUM> vagaCompetenciaRepository = new VagaCompetenciaDAO()

    void createVaga(Vaga request) {
        Long returnedID = vagaRepository.create(new Vaga(request))

        if (request.competences().size() > 0 && request.competences() !== null) {
            addCompetencesToVaga(returnedID, request.competences())
        }
    }

    void addCompetencesToVaga(Long vagaId, List<CompetenciaENUM> competences) {
        vagaCompetenciaRepository.create(vagaId, competences
                .stream()
                .map { it -> it.getId() }
                .collect(Collectors.toList()))
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
        vagaRepository.update(new Vaga(request), id)
    }
}
