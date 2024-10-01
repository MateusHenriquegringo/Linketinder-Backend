package service

import DTO.Request.CandidatoRequestDTO
import DTO.Request.VagaRequestDTO
import DTO.Response.CandidatoCompetenciaResponseDTO
import DTO.Response.CandidatoResponseDTO
import enums.CompetenciasENUM
import model.Candidato
import model.Vaga
import repository.ModelsCRUD
import repository.VagaDAO
import repository.auxiliary.AuxiliaryTablesCRUD
import repository.auxiliary.VagaCompetenciaDAO

import java.util.stream.Collectors

class VagaService {

    private ModelsCRUD<Vaga, Long> vagaRepository = new VagaDAO()
    private AuxiliaryTablesCRUD<Vaga, Long> vagaCompetenciaRepository = new VagaCompetenciaDAO()

    void createVaga(VagaRequestDTO request) {
        Long returnedID = vagaRepository.create(new Vaga(request))

        if (request.competences().size() > 0 && request.competences() !== null) {
            addCompetencesToVaga(returnedID, request.competences())
        }
    }

    void addCompetencesToVaga(Long vagaId, List<CompetenciasENUM> competences) {
        vagaCompetenciaRepository.create(vagaId, competences
                .stream()
                .map { it -> it.getId() }
                .collect(Collectors.toList()))
    }


    CandidatoResponseDTO findCandidatoById(Long id) {
        Candidato model = candidatoRepository.findById(id)

        return buildCandidatoDTO(model)
    }

    List<CandidatoResponseDTO> listAll() {
        return candidatoRepository.listAll().forEach {
            it -> buildCandidatoDTO(it)
        }
    }

    CandidatoCompetenciaResponseDTO findCandidatoAndCompetenciasById(Long id) {
        Candidato model = candidatoCompetenciaRepository.findById(id)
        return buildCandidatoWithCompetenciasDTO(buildCandidatoDTO(model), model.getCompetences())
    }

    void deleteCandidato(Long id) {
        candidatoRepository.delete(id)
    }

    void updateCandidato(CandidatoRequestDTO request, Long id) {
        candidatoRepository.update(request, id)
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

    private static buildCandidatoWithCompetenciasDTO(CandidatoResponseDTO dto, List<CompetenciasENUM> competencias) {
        return new CandidatoCompetenciaResponseDTO(
                dto,
                competencias
        )
    }
}
