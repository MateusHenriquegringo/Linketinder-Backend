package service


import enums.CompetenciaENUM
import model.Candidato
import repository.CandidatoRepository
import repository.LikeRepository
import repository.MatchRepository
import service.dto.CandidatoResponseDTO

import java.util.stream.Collectors

class CandidatoService {

    private LikeRepository likeRepository
    private MatchRepository matchRepository

    private CandidatoRepository repository

    CandidatoService(CandidatoRepository repository) {
        this.repository = repository
    }
    CandidatoService( ) {

    }

    void likeVaga(long from, long to){
        likeRepository.candidatoLikesVaga(from, to)
    }

    void dislikeVaga(long from, long to){
        likeRepository.candidatoDislikesVaga(from, to)
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
