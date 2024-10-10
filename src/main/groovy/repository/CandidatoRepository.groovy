package repository

import dao.CandidatoDAO
import dao.ModelsCRUD
import dao.auxiliary.AuxiliaryTablesCRUD
import dao.auxiliary.CandidatoCompetenciaDAO
import enums.CompetenciaENUM
import model.Candidato

class CandidatoRepository {

    ModelsCRUD<Candidato, Long> candidatoDAO = new CandidatoDAO()
    AuxiliaryTablesCRUD<Candidato, Long, CompetenciaENUM> competenciaCandidatoDAO = new CandidatoCompetenciaDAO()

    void createCandidato(Candidato request) {
        long returnedID = candidatoDAO.create(request)
        addCompetencesIfPresent(returnedID, request.getCompetences())
    }

    List<Candidato> listAllWithoutCompetences() {
        return candidatoDAO.listAll()
    }

    List<Candidato> listAll() {
        return competenciaCandidatoDAO.listAll()
    }

    Candidato findCandidatoById(Long id) {
        return competenciaCandidatoDAO.findById(id)
    }

    void removeCompetence(Long id, CompetenciaENUM competence) {
        competenciaCandidatoDAO.deleteCompetence(id, competence)
    }

    void addCompetence(Long id, List<CompetenciaENUM> competences) {
        competenciaCandidatoDAO.createAssociation(id, competences)
    }

    void deleteCandidato(Long id) {
        candidatoDAO.delete(id)
    }

    void updateCandidato(Candidato request, Long id) {
        candidatoDAO.update(request, id)

        competenciaCandidatoDAO.deleteAllCompetences(id)
        addCompetencesIfPresent(id, request.getCompetences())
    }

    void updateCompetences(Long id, List<CompetenciaENUM> competences) {
        competenciaCandidatoDAO.updateCompetences(id, competences)
    }

    private void addCompetencesIfPresent(Long candidatoID, List<CompetenciaENUM> competences) {
        Optional.ofNullable(competences)
                .filter(comp -> !comp.isEmpty() && comp !== null)
                .ifPresent(comp -> competenciaCandidatoDAO.createAssociation(candidatoID, comp))
    }
}
