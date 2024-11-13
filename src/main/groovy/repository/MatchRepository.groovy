package repository

import dao.likes.MatchesDAO

class MatchRepository {

    MatchesDAO dao

    boolean verifyMatchAfterEmpresaLike(long empresaID, long candidatoID) {
        return dao.verifyMatchAfterEmpresaLike(empresaID, candidatoID)
    }

    boolean verifyMatchAfterCandidatoLike(long candidatoID, long empresaID) {
        return dao.verifyMatchAfterCandidatoLike(candidatoID, empresaID)
    }

    void addMatch(long candidatoID, long empresaID) {
        dao.addMatch(candidatoID, empresaID)
    }

    void removeMatch(long candidatoID, long empresaID) {
        dao.removeMatch(candidatoID, empresaID)
    }
}
