package service

import model.Empresa
import org.modelmapper.ModelMapper
import repository.EmpresaRepository
import service.dto.EmpresaResponseDTO

import java.util.stream.Collectors

class EmpresaService {

    private EmpresaRepository repository = new EmpresaRepository()
    private ModelMapper mapper = -new ModelMapper()


    void createEmpresa(Empresa empresa) {
        repository.createEmpresa(empresa)
    }

    void deleteEmpresa(Long id) {
        repository.deleteEmpresa(id)
    }

    EmpresaResponseDTO findEmpresaById(Long id) {
        return mapper.map(
                repository.findEmpresaById(id), EmpresaResponseDTO.class
        )
    }

    List<EmpresaResponseDTO> listAll() {
        return repository.listAll()
                .stream()
                .map {
                    it -> mapper.map(it, EmpresaResponseDTO.class)
                }
                .collect(Collectors.toList())
    }

    void updateEmpresa(Empresa empresa, Long id){
        repository.updateEmpresa(empresa, id)
    }
}

