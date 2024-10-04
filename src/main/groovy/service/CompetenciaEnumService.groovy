package service


import model.Competencia
import dao.CompetenciasDAO
import dao.ModelsCRUD

class CompetenciaEnumService {

    ModelsCRUD<Competencia, Long> competenciaRepository = new CompetenciasDAO()

    List<Competencia> listAll() {
        return competenciaRepository.listAll()
    }

    Competencia findById(Long id) {
        return competenciaRepository.findById(id)
    }

}
