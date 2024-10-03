package app

import DTO.Request.CandidatoRequestDTO
import enums.CompetenciaENUM

import service.CandidatoService

class Main {

    static CandidatoService service = new CandidatoService()
    static void main(String[] args) {

        service.createCandidato(new CandidatoRequestDTO("mateus","derossi", "041888900", "descricao", "obrabogordin@gmail.com", "tape", "033939303", "senah1243", List.of(CompetenciaENUM.JAVASCRIPT, CompetenciaENUM.ANGULAR)))

        service.listAll().forEach {
            print(it.toString())
        }

    }
}
