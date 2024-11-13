package repository

import dao.likes.CandidatoLikesDAO
import dao.likes.EmpresaLikesDAO
import dao.likes.MatchesDAO

class LikeRepository {

    CandidatoLikesDAO candidatoLikesDAO
    EmpresaLikesDAO empresaLikesDAO
    MatchesDAO matchesDAO

    LikeRepository( CandidatoLikesDAO candidatoLikesDAO, EmpresaLikesDAO empresaLikesDAO, MatchesDAO matchesDAO ) {
        this.candidatoLikesDAO = candidatoLikesDAO
        this.empresaLikesDAO = empresaLikesDAO
        this.matchesDAO = matchesDAO
    }

    void empresaLikesCandidato(long from, long to){
        empresaLikesDAO.addLike(from, to)
    }

    void empresaDislikesCandidato(long from, long to){
        empresaLikesDAO.removeLike(from, to)
    }

    void candidatoLikesVaga(long from, long to){
        candidatoLikesDAO.addLike(from, to)
    }

    void candidatoDislikesVaga(long from, long to){
        candidatoLikesDAO.removeLike(from, to)
    }



}
