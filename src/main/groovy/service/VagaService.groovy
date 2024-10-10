package service

import enums.CompetenciaENUM
import model.Vaga
import org.modelmapper.ModelMapper
import repository.VagaRepository
import service.dto.VagaResponseDTO

import java.util.stream.Collectors


class VagaService {

    private VagaRepository repository = new VagaRepository()
    private ModelMapper mapper = new ModelMapper()

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
                    it -> mapper.map(it, VagaResponseDTO.class)
                }
        .collect(Collectors.toList())
    }

    VagaResponseDTO findById(Long id){
        return mapper.map(
                repository.findById(id), VagaResponseDTO.class
        )
    }

    void editCompetences(Long id, List<CompetenciaENUM> competences){
        repository.editCompetences(id, competences)
    }

    List<VagaResponseDTO> listAllFromEmpresa(Long id){
        return repository.listAllFromEmpresa(id)
                .stream()
                .map {
                    it -> mapper.map(it, VagaResponseDTO.class)
                }
                .collect(Collectors.toList())
    }

    void updateVaga(Vaga update, Long id){
        repository.updateVaga(update, id)
    }
}
