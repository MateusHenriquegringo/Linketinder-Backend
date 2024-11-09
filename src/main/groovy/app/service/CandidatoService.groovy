package app.service

import app.enums.CompetenciaENUM
import app.model.Candidato
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import app.repository.CandidatoRepository

@Service
class CandidatoService {

    @Autowired
    private CandidatoRepository repository;

    void createCandidato(Candidato request) {
        repository.save(request)
    }


    List<Candidato> listAll() {
        return repository.findAll()
    }

    Candidato findCandidatoById(Long id) {
        return repository.findById(id).orElse(null)
    }

    void removeCompetence(Long id, CompetenciaENUM competence) {
        repository.deleteById(id, competence)
    }

    void addCompetence(Long id, List<CompetenciaENUM> competences) {
        repository.addCompetence(id, competences)
    }

    void deleteCandidato(Long id) {
        repository.deleteById(id)
    }

    void updateCandidato(Candidato request) {
        repository.save(request)
    }

    void updateCompetences(Long id, List<CompetenciaENUM> competences) {
        repository.updateCompetences(id, competences)
    }


    void likeVaga(long id, long idVaga) {
        mediator.likeFromCandidatoToVaga(id, idVaga)
    }

    void dislikeVaga(long id, long idVaga) {
        mediator.likeFromCandidatoToVaga(id, idVaga)
    }

}
