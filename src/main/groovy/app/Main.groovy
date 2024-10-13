package app

import dao.auxiliary.CandidatoCompetenciaDAO
import service.CandidatoService

class Main {


    static CandidatoCompetenciaDAO dao = new CandidatoCompetenciaDAO()
    static CandidatoService service  = new CandidatoService()
    static void main(String[] args) {

        print(dao.findById(1L)
        )

    }
}
