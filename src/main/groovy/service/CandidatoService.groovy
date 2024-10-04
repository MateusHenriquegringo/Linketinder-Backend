package service


import enums.CompetenciaENUM
import model.Candidato
import repository.CandidatoDAO
import repository.ModelsCRUD
import repository.auxiliary.AuxiliaryTablesCRUD
import repository.auxiliary.CandidatoCompetenciaDAO

import java.util.stream.Collectors

class CandidatoService {

    private ModelsCRUD<Candidato, Long> candidatoRepository = new CandidatoDAO()
    private AuxiliaryTablesCRUD<Candidato, Long, CompetenciaENUM> candidatoCompetenciaRepository = new CandidatoCompetenciaDAO()


    void createCandidato(Candidato request) {
        long returnedID = candidatoRepository.create(request)
        addCompetencesIfPresent(returnedID, request.getCompetences())
    }


    List<Candidato> listAll() {
        return candidatoRepository.listAll()
    }


    Candidato findCandidatoById(Long id) {
        return candidatoCompetenciaRepository.findById(id)
    }

    void deleteCandidato(Long id) {
        candidatoRepository.delete(id)
    }

    void updateCandidato(Candidato request, Long id) {
        candidatoRepository.update(request, id)

        candidatoCompetenciaRepository.deleteAllCompetences(id)
        addCompetencesIfPresent(id, request.getCompetences())
    }

    private void addCompetencesIfPresent(Long candidatoID, List<CompetenciaENUM> competences) {
        Optional.ofNullable(competences)
                .filter(comp -> !comp.isEmpty() && comp !== null)
                .ifPresent(comp -> candidatoCompetenciaRepository.create(candidatoID, comp))
    }

}
