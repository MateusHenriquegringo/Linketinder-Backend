package service


import model.Empresa
import repository.EmpresaDAO
import repository.ModelsCRUD

class EmpresaService {

    private ModelsCRUD<Empresa, Long> empresaRepository = new EmpresaDAO()

    void createEmpresa(Empresa request) {
        empresaRepository.create(request)
    }

    Empresa findEmpresaById(Long id) {
        return empresaRepository.findById(id)
    }

    List<Empresa> listAllEmpresas() {
        return empresaRepository.listAll()
    }

    void delete(long id) {
        empresaRepository.delete(id)
    }

    void update(Empresa request, long id) {
        empresaRepository.update(request, id)
    }
}
