package service


import enums.CompetenciaENUM
import model.Candidato
import repository.CandidatoRepository
import service.dto.CandidatoResponseDTO
import service.match.LikeMediator

import java.util.stream.Collectors

class CandidatoService {

    CandidatoRepository repository;
    private LikeMediator mediator;

    EmpresaService(LikeMediator mediator, CandidatoRepository repository) {
        this.mediator = mediator;
        this.repository = repository;
    }

    CandidatoService(CandidatoRepository repository) {
        this.repository = repository
    }
    CandidatoService( ) {

    }

    void createCandidato(Candidato request) {
        repository.createCandidato(request)
    }

    List<CandidatoResponseDTO> listAllWithoutCompetences() {
        return repository.listAllWithoutCompetences()
                .stream()
                .map {
                    it -> mapToDto(it)
                }
                .collect(Collectors.toList())
    }

    List<CandidatoResponseDTO> listAll() {
        return repository.listAll()
                .stream()
                .map {
                    it -> mapToDto(it)
                }
                .collect(Collectors.toList())
    }

    CandidatoResponseDTO findCandidatoById(Long id) {
        return mapToDto(repository.findCandidatoById(id))
    }

    void removeCompetence(Long id, CompetenciaENUM competence) {
        repository.removeCompetence(id, competence)
    }

    void addCompetence(Long id, List<CompetenciaENUM> competences) {
        repository.addCompetence(id, competences)
    }

    void deleteCandidato(Long id) {
        repository.deleteCandidato(id)
    }

    void updateCandidato(Candidato request, Long id) {
        repository.updateCandidato(request, id)
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

    private static CandidatoResponseDTO mapToDto(Candidato candidato){
        return new CandidatoResponseDTO (
                candidato.getId(),
                candidato.getFirst_name(),
                candidato.getLast_name(),
                candidato.getCpf(),
                candidato.getDescription(),
                candidato.getEmail(),
                candidato.getCep(),
                candidato.getCity(),
                candidato.getCompetences()
        )
    }

}
