package service


import enums.CompetenciaENUM
import model.Candidato
import repository.CandidatoRepository
import service.dto.CandidatoResponseDTO

import java.util.stream.Collectors

class CandidatoService {

    private CandidatoRepository repository = new CandidatoRepository()

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

    private static CandidatoResponseDTO mapToDto(Candidato candidato){
        return new CandidatoResponseDTO (
                candidato.getId(),
                candidato.getFirst_name(),
                candidato.getLast_name(),
                candidato.getCPF(),
                candidato.getDescription(),
                candidato.getEmail(),
                candidato.getCEP(),
                candidato.getCity(),
                candidato.getCompetences()
        )
    }
}
