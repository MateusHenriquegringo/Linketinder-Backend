package service

import enums.CompetenciaENUM
import model.Vaga
import org.modelmapper.ModelMapper
import repository.VagaRepository
import service.dto.VagaResponseDTO

import java.util.stream.Collectors


class VagaService {

    private VagaRepository repository = new VagaRepository()

    VagaService(VagaRepository repository) {
        this.repository = repository
    }
    VagaService() {
    }

    void createVaga(Vaga vaga){
        repository.createVaga(vaga)
    }

    void deleteVaga(Long id){
        repository.deleteVaga(id)
    }



    List<VagaResponseDTO> listAll(){
        return repository.listAll()
                .stream()
                .map {
                    it -> mapToDto(it)
                }
        .collect(Collectors.toList())
    }

    VagaResponseDTO findById(Long id){
        return mapToDto(repository.findById(id))
    }

    void editCompetences(Long id, List<CompetenciaENUM> competences){
        repository.editCompetences(id, competences)
    }

    List<VagaResponseDTO> listAllFromEmpresa(Long id){
        return repository.listAllFromEmpresa(id)
                .stream()
                .map {
                    it -> mapToDto(it)
                }
                .collect(Collectors.toList())
    }

    void updateVaga(Vaga update, Long id){
        repository.updateVaga(update, id)
    }

    private static VagaResponseDTO mapToDto(Vaga it){
        return new VagaResponseDTO(
                id: it.getId(),
                vaga_name: it.getVaga_name(),
                description: it.getDescription(),
                city: it.getCity(),
                state: it.getState(),
                empresaId: it.getEmpresaId(),
                competences: it.getCompetences()
        )
    }
}
