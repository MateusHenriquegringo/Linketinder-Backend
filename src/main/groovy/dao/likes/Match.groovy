package dao.likes

class Match {

    private long candidatoId;
    private long empresaId

    Match(long candidatoId, long empresaId) {
        this.candidatoId = candidatoId
        this.empresaId = empresaId
    }

    long getCandidatoId() {
        return candidatoId
    }

    void setCandidatoId(long candidatoId) {
        this.candidatoId = candidatoId
    }

    long getEmpresaId() {
        return empresaId
    }

    void setEmpresaId(long empresaId) {
        this.empresaId = empresaId
    }
}
