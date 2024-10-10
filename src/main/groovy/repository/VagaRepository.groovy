package repository

import dao.ModelsCRUD
import dao.VagaDAO
import dao.auxiliary.AuxiliaryTablesCRUD
import dao.auxiliary.VagaCompetenciaDAO
import enums.CompetenciaENUM
import model.Vaga

class VagaRepository {

    ModelsCRUD<Vaga, Long> vagaDAO = new VagaDAO()
    AuxiliaryTablesCRUD<Vaga, Long, CompetenciaENUM> vagaCompetenciaDAO = new VagaCompetenciaDAO()

    void createVaga(Vaga vaga){
        Long returnedId = vagaDAO.create(vaga)
        addCompetencesIfPresent(returnedId, vaga.getCompetences())
    }

    void deleteVaga(Long id){
        vagaDAO.delete(id)
    }

    List<Vaga> listVagasWithNoCompetences(){
        return vagaDAO.listAll()
    }

    List<Vaga> listAll(){
        return vagaCompetenciaDAO.listAll()
    }

    Vaga findById(Long id){
        return vagaCompetenciaDAO.findById(id)
    }

    void editCompetences(Long id, List<CompetenciaENUM> enumList){
        vagaCompetenciaDAO.updateCompetences(id, enumList)
    }


    List<Vaga> listAllFromEmpresa(Long id) {
        return vagaCompetenciaDAO.listAllByEmpresaId(id)
    }

    void updateVaga(Vaga vaga, Long id){
        vagaDAO.update(vaga, id)
    }

    private void addCompetencesIfPresent(Long candidatoID, List<CompetenciaENUM> competences) {
        Optional.ofNullable(competences)
                .filter(comp -> !comp.isEmpty() && comp !== null)
                .ifPresent(comp -> vagaCompetenciaDAO.createAssociation(candidatoID, comp))
    }
}
