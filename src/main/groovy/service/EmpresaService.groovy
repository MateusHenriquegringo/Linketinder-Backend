package service

import model.Empresa
import repository.EmpresaRepository
import service.dto.EmpresaResponseDTO
import service.match.LikeMediator

import java.util.stream.Collectors

class EmpresaService {

    private LikeMediator mediator
    private EmpresaRepository repository

    EmpresaService(EmpresaRepository repository, LikeMediator mediator) {
        this.mediator = mediator
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


    void likeCandidato(long id, long idVaga) {
        mediator.likeFromEmpresaToCandidato(id, idVaga)
    }

    void dislikeCandidato(long id, long idVaga) {
        mediator.dislikeFromEmpresaToCandidato(id, idVaga)
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

