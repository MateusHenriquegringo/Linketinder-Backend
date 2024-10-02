package service


import DTO.Request.VagaRequestDTO
import DTO.Response.VagaResponseDTO
import enums.CompetenciaENUM
import model.Vaga
import repository.ModelsCRUD
import repository.VagaDAO
import repository.auxiliary.AuxiliaryTablesCRUD
import repository.auxiliary.VagaCompetenciaDAO

import java.util.stream.Collectors

class VagaService implements BuildDTO<VagaResponseDTO, Vaga> {

    private ModelsCRUD<Vaga, Long> vagaRepository = new VagaDAO()
    private AuxiliaryTablesCRUD<Vaga, Long> vagaCompetenciaRepository = new VagaCompetenciaDAO()

    void createVaga(VagaRequestDTO request) {
        Long returnedID = vagaRepository.create(new Vaga(request))

        if (request.competences().size() > 0 && request.competences() !== null) {
            addCompetencesToVaga(returnedID, request.competences())
        }
    }

    void addCompetencesToVaga(Long vagaId, List<CompetenciaENUM> competences) {
        vagaCompetenciaRepository.create(vagaId, competences
                .stream()
                .map { it -> it.getId() }
                .collect(Collectors.toList()))
    }

    List<VagaResponseDTO> findVagasFromEmpresa(Long id) {
        return vagaRepository.findAllByEmpresaId(id)
                .forEach {
                    it -> buildDTO(it)
                }
    }

    VagaResponseDTO findVagaById(Long id) {
        Vaga model = vagaRepository.findById(id)
        return buildDTO(model)
    }

    List<VagaResponseDTO> listAll() {
        return vagaRepository.listAll().forEach {
            it -> buildDTO(it)
        }
    }

    void deleteVaga(Long id) {
        vagaRepository.delete(id)
    }

    void updateVaga(VagaRequestDTO request, Long id) {
        vagaRepository.update(new Vaga(request), id)
    }

    @Override
    VagaResponseDTO buildDTO(Vaga model) {
        return new VagaResponseDTO(
                model.getId(),
                model.getVaga_name(),
                model.getDescription(),
                model.getState(),
                model.getCity(),
                model.getEmpresaId(),
                model.getCompetences().forEach {
                    it -> it.getDescription()
                }
        )
    }
}
