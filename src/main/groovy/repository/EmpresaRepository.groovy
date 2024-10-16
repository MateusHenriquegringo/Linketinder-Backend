package repository

import DB.ConnectionFactory
import DB.DBTypes
import dao.EmpresaDAO
import dao.ModelsCRUD
import model.Empresa

class EmpresaRepository {

    ModelsCRUD<Empresa, Long> empresaDAO = new EmpresaDAO(
            ConnectionFactory.getConnection(DBTypes.POSTGRES)
    )

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

}
