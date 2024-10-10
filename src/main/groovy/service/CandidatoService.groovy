package service


import enums.CompetenciaENUM
import model.Candidato
import org.modelmapper.ModelMapper
import repository.CandidatoRepository
import service.dto.CandidatoResponseDTO

import java.util.stream.Collectors

class CandidatoService {

    private CandidatoRepository repository = new CandidatoRepository()
    private ModelMapper mapper = new ModelMapper();

    void createCandidato(Candidato request) {
        repository.createCandidato(request)
    }

    List<CandidatoResponseDTO> listAllWithoutCompetences() {
        return repository.listAllWithoutCompetences()
                .stream()
                .map {
                    it -> mapper.map(it, CandidatoResponseDTO.class)
                }
                .collect(Collectors.toList())
    }

    List<CandidatoResponseDTO> listAll() {
        return repository.listAll()
                .stream()
                .map {
                    it -> mapper.map(it, CandidatoResponseDTO.class)
                }
                .collect(Collectors.toList())
    }

    CandidatoResponseDTO findCandidatoById(Long id) {
        return mapper.map(
                repository.findCandidatoById(id), CandidatoResponseDTO.class
        )
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

}
