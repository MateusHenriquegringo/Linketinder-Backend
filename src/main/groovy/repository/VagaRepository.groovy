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

    void deleteAllVagasFromEmpresa(Long empresaId){
        vagaDAO.deleteFromEmpresaId(empresaId)
    }


    private void addCompetencesIfPresent(Long candidatoID, List<CompetenciaENUM> competences) {
        Optional.ofNullable(competences)
                .filter(comp -> !comp.isEmpty() && comp !== null)
                .ifPresent(comp -> vagaCompetenciaDAO.createAssociation(candidatoID, comp))
    }
}
