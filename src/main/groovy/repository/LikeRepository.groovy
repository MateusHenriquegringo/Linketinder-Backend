package repository

import DB.ConnectionFactory
import DB.DBTypes
import dao.likes.CandidatoLikesDAO
import dao.likes.EmpresaLikesDAO
import dao.likes.Like

class LikeRepository {

    LikeRepository() {
    }

    private CandidatoLikesDAO candidatoLikesDAO = new CandidatoLikesDAO(
            ConnectionFactory.getConnection(DBTypes.POSTGRES)
    )

    private EmpresaLikesDAO empresaLikesDAO= new EmpresaLikesDAO(
            ConnectionFactory.getConnection(DBTypes.POSTGRES)
    )

    void likeVaga(Like like){
        candidatoLikesDAO.addLike(like)
    }

    void dislikeVaga(Like like){
        candidatoLikesDAO.deleteLike(like)
    }

    void likeCandidato(Like like){
        empresaLikesDAO.addLike(like)
    }

    void dislikeCandidato(Like like){
        empresaLikesDAO.deleteLike(like)
    }

    boolean empresaExistsOnCandidatoLikes(Like like){
        return candidatoLikesDAO.likeToAnyVagaFromEmpresaExists(like)
    }

    boolean candidatoExistsOnEmpresaLikes(Like like){
        return empresaLikesDAO.likeExists(like)
    }
}
