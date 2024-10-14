package app

import dao.auxiliary.CandidatoCompetenciaDAO
import service.CandidatoService
import service.EmpresaService

class Main {


    static CandidatoCompetenciaDAO dao = new CandidatoCompetenciaDAO()
    static EmpresaService service  = new EmpresaService()
    static void main(String[] args) {

        print(service.findEmpresaById(3L)
        )
    }
}
