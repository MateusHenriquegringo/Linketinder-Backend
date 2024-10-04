package app


import dao.auxiliary.CandidatoCompetenciaDAO
import dao.auxiliary.VagaCompetenciaDAO
import service.CandidatoService

class Main {

    static CandidatoService service = new CandidatoService()

    static CandidatoCompetenciaDAO dao = new CandidatoCompetenciaDAO()
    static VagaCompetenciaDAO vaga = new VagaCompetenciaDAO()

    static void main(String[] args) {


        vaga.listAll().forEach {
            print(it.toString())
        }


    }
}
