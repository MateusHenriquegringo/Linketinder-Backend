package service

import DTO.Request.CandidatoRequestDTO
import model.Candidato
import model.Competencia
import repository.CRUD
import repository.CandidatoDAO

class CandidatoService {

    private CRUD<Candidato, Long> candidatoRepository = new CandidatoDAO();

    void createCandidato(CandidatoRequestDTO request) {
        candidatoRepository.create(new Candidato(request))
    }

    void createCandidato(CandidatoRequestDTO request, List<Competencia> competencias) {
        long id = candidatoRepository.create(new Candidato(request))

    }

}
