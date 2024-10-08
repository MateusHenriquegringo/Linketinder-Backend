package repository

import dao.EmpresaDAO
import dao.ModelsCRUD
import dao.auxiliary.AuxiliaryTablesCRUD
import dao.auxiliary.VagaCompetenciaDAO
import enums.CompetenciaENUM
import model.Empresa
import model.Vaga

class EmpresaRepository {

    ModelsCRUD<Empresa, Long> empresaDAO = new EmpresaDAO()

    AuxiliaryTablesCRUD<Vaga, Long, CompetenciaENUM> vagaCompetenciaDAO = new VagaCompetenciaDAO()

    void createEmpresa(Empresa empresa) {
        empresaDAO.create(empresa)
    }

    void deleteEmpresa(Long id) {
        empresaDAO.delete(id)
    }

    Empresa findEmpresaById(Long id) {
        return empresaDAO.findById(id)
    }

    List<Empresa> listAll() {
        return empresaDAO.listAll()
    }

    void updateEmpresa(Empresa empresa, long id) {
        empresaDAO.update(empresa, id)
    }

    List<Vaga> findAllVagasOfEmpresa(Long empresaId) {
        return vagaCompetenciaDAO.listAllByEmpresaId(empresaId)
    }

}
