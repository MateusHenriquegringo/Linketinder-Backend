package service.match


import dao.likes.Like
import dao.likes.MatchDAO
import repository.LikeRepository
import service.NotificationService

class LikeMatchMediator implements LikeMediator{

    private NotificationService notificationService
    private MatchDAO matchDAO
    private LikeRepository repository

    LikeMatchMediator(LikeRepository repository, MatchDAO matchDAO, NotificationService notificationService) {
        this.repository = repository
        this.matchDAO = matchDAO
        this.notificationService = notificationService
    }

    @Override
    void likeFromEmpresaToCandidato(long empresaId, long candidatoId) {
        Like like = new Like(empresaId, candidatoId)
        repository.likeCandidato(like)

        if(repository.empresaExistsOnCandidatoLikes (like)) {
            createMatch(empresaId, candidatoId)
        }
    }

    @Override
    void likeFromCandidatoToVaga(long candidatoId, long vagaId) {
        Like like = new Like(candidatoId, vagaId)
        repository.likeVaga(like)

        if(repository.candidatoExistsOnEmpresaLikes(like)) {
            createMatch(candidatoId, vagaId)
        }
    }

    @Override
    void dislikeFromEmpresaToCandidato(long empresaId, long candidatoId) {
        Like like = new Like(empresaId, candidatoId)
        repository.dislikeCandidato(like)
    }

    @Override
    void dislikeFromCandidatoToVaga(long candidatoId, long vagaId) {
        Like like = new Like(candidatoId, vagaId)
        repository.dislikeVaga(like)
    }

    private void createMatch(long candidatoId, long empresaId) {
        if (!matchDAO.existsMatch(candidatoId, empresaId)) {
            matchDAO.addMatch(candidatoId, empresaId);
            notificationService.notifyMatch(candidatoId, empresaId);
        }
    }
}
