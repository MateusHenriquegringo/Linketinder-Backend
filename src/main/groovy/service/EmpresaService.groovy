package service

import model.Empresa
import repository.EmpresaRepository
import service.dto.EmpresaResponseDTO

import java.util.stream.Collectors

class EmpresaService {

    private EmpresaRepository repository = new EmpresaRepository()

    EmpresaService(EmpresaRepository repository) {
        this.repository = repository
    }

    EmpresaService( ) {
    }

    void createEmpresa(Empresa empresa) {
        repository.createEmpresa(empresa)
    }

    void deleteEmpresa(Long id) {
        repository.deleteEmpresa(id)
    }

    EmpresaResponseDTO findEmpresaById(Long id) {
        return mapToDto(repository.findEmpresaById(id))

    }

    List<EmpresaResponseDTO> listAll() {
        return repository.listAll()
                .stream()
                .map {
                    it -> mapToDto(it)
                }
                .collect(Collectors.toList())
    }

    void updateEmpresa(Empresa empresa, Long id) {
        repository.updateEmpresa(empresa, id)
    }

    private static mapToDto(Empresa it) {
        return new EmpresaResponseDTO(
                id: it.getId(),
                empresa_name: it.getEmpresa_name(),
                email: it.getEmail(),
                description: it.getDescription(),
                cnpj: it.getCNPJ(),
                cep: it.getCEP(),
                country: it.getCountry()
        )
    }
}

