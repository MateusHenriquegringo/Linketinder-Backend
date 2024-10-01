package service

import DTO.Request.CandidatoRequestDTO

import DTO.Response.CandidatoResponseDTO
import enums.CompetenciasENUM
import model.Candidato
import repository.CandidatoDAO
import repository.ModelsCRUD
import repository.auxiliary.AuxiliaryTablesCRUD
import repository.auxiliary.CandidatoCompetenciaDAO

import java.util.stream.Collectors

class CandidatoService implements BuildDTO<CandidatoResponseDTO, Candidato> {

    private ModelsCRUD<Candidato, Long> candidatoRepository = new CandidatoDAO()
    private AuxiliaryTablesCRUD<Candidato, Long> candidatoCompetenciaRepository = new CandidatoCompetenciaDAO()

    void createCandidato(CandidatoRequestDTO request) {
        long returnedID = candidatoRepository.create(new Candidato(request))

        if(request.competences().size() > 0 && request.competences()!==null){
            addCompetencesToCandidato(returnedID, request.competences())
        }
    }

    void addCompetencesToCandidato(Long candidatoID, List<CompetenciasENUM> competences){
        candidatoCompetenciaRepository.create(candidatoID, competences
                .stream()
                .map {it -> it.getId()}
                .collect(Collectors.toList()));
    }

    List<CandidatoResponseDTO> listAll() {
        return candidatoRepository.listAll().forEach {
            it -> buildDTO(it)
        }
    }

    CandidatoResponseDTO findCandidatoAndCompetenciasById(Long id) {
        Candidato model = candidatoCompetenciaRepository.findById(id)
        return buildDTO(model)
    }

    void deleteCandidato(Long id) {
        candidatoRepository.delete(id)
    }

    void updateCandidato(CandidatoRequestDTO request, Long id) {
        candidatoRepository.update(new Candidato(request), id)
    }


    @Override
    CandidatoResponseDTO buildDTO(Candidato model) {
        return new CandidatoResponseDTO(
                model.getId(),
                model.getFirst_name(),
                model.getLast_name(),
                model.getCPF(),
                model.getDescription(),
                model.getEmail(),
                model.getCEP(),
                model.getCity(),
                model.getCompetences()
                        .forEach {it-> it.getDescription()}
        )
    }
}
