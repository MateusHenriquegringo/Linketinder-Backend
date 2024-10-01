package service

import DTO.Request.CandidatoRequestDTO
import DTO.Response.CandidatoCompetenciaResponseDTO
import DTO.Response.CandidatoResponseDTO
import model.Candidato
import repository.CRUD
import repository.CandidatoDAO
import repository.auxiliary.AuxiliaryTablesCRUD
import repository.auxiliary.CandidatoCompetenciaDAO

class CandidatoService {

    private CRUD<Candidato, Long> candidatoRepository = new CandidatoDAO()
    private AuxiliaryTablesCRUD<Candidato, Long> candidatoCompetenciaRepository = new CandidatoCompetenciaDAO()

    void createCandidato(CandidatoRequestDTO request) {
        candidatoRepository.create(new Candidato(request))
    }

    void createCandidato(CandidatoRequestDTO request, List<Long> competencias) {
        long returnedID = candidatoRepository.create(new Candidato(request))
        candidatoCompetenciaRepository.create(returnedID, competencias)
    }

    CandidatoResponseDTO findCandidatoById(Long id) {
        Candidato model = candidatoRepository.findById(id)

        return buildCandidatoDTO(model)
    }

    CandidatoCompetenciaResponseDTO findCandidatoAndCompetenciasById(Long id) {
        Candidato model = candidatoCompetenciaRepository.findById(id)
        return buildCandidatoWithCompetenciasDTO(buildCandidatoDTO(model), model.getCompetences())
    }

    private static buildCandidatoDTO(Candidato model) {
        return new CandidatoResponseDTO(
                model.getId(),
                model.getFirst_name(),
                model.getLast_name(),
                model.getCPF(),
                model.getDescription(),
                model.getEmail(),
                model.getCEP(),
                model.getCity()
        )
    }

    private static buildCandidatoWithCompetenciasDTO(CandidatoResponseDTO dto, List<String> competencias) {
        return new CandidatoCompetenciaResponseDTO(
                dto,
                competencias
        )
    }
}
