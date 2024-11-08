package service.match

interface LikeMediator {

    void likeFromEmpresaToCandidato(long empresaId, long candidatoId)
    void likeFromCandidatoToVaga(long candidatoId, long vagaId)

    void dislikeFromEmpresaToCandidato(long empresaId, long candidatoId)
    void dislikeFromCandidatoToVaga(long candidatoId, long vagaId)

}
