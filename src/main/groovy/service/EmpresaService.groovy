package service

import DTO.Request.EmpresaRequestDTO
import DTO.Response.EmpresaResponseDTO
import model.Empresa
import repository.EmpresaDAO
import repository.ModelsCRUD

class EmpresaService implements BuildDTO<EmpresaResponseDTO, Empresa> {

    private ModelsCRUD<Empresa, Long> empresaRepository = new EmpresaDAO()

    void createEmpresa(EmpresaRequestDTO request) {
        empresaRepository.create(new Empresa(request))
    }

    EmpresaResponseDTO findEmpresaById(Long id) {
        Empresa model = empresaRepository.findById(id)
        return buildDTO(model)
    }

    List<EmpresaResponseDTO> listAllEmpresas() {
        return empresaRepository.listAll()
                .forEach {
                    it -> buildDTO(it)
                }
    }

    void delete(long id) {
        empresaRepository.delete(id)
    }

    void update(EmpresaRequestDTO request, long id) {
        empresaRepository.update(new Empresa(request), id)
    }

    @Override
    EmpresaResponseDTO buildDTO(Empresa model) {
        return new EmpresaResponseDTO(
                model.getId(),
                model.getEmpresa_name(),
                model.getDescription(),
                model.getEmail(),
                model.getCNPJ(),
                model.getCEP(),
                model.getCountry()
        )
    }

}
