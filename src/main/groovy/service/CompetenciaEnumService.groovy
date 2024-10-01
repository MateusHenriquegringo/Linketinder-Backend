package service


import model.Competencia
import repository.CompetenciasDAO
import repository.ModelsCRUD

class CompetenciaEnumService {

    ModelsCRUD<Competencia, Long> competenciaRepository = new CompetenciasDAO()

    List<Competencia> listAll() {
        return competenciaRepository.listAll()
    }

    Competencia findById(Long id) {
        return competenciaRepository.findById(id)
    }

}
