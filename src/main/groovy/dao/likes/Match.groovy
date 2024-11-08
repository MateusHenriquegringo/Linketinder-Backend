package dao.likes

class Match {

    Match(long empresaId, long candidatoId) {
        this.empresaId = empresaId
        this.candidatoId = candidatoId
    }

    long empresaId;
    long candidatoId;
}
