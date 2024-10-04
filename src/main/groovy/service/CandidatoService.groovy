package service


import enums.CompetenciaENUM
import model.Candidato
import dao.CandidatoDAO
import dao.ModelsCRUD
import dao.auxiliary.AuxiliaryTablesCRUD
import dao.auxiliary.CandidatoCompetenciaDAO

class CandidatoService {

    private ModelsCRUD<Candidato, Long> candidatoRepository = new CandidatoDAO()
    private AuxiliaryTablesCRUD<Candidato, Long, CompetenciaENUM> candidatoCompetenciaRepository = new CandidatoCompetenciaDAO()




}
