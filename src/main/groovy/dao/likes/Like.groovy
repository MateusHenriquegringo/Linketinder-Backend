package dao.likes;

import enums.LikeDirectionENUM;

public class Like {

    private long candidatoId;

    private long empresaId;

    LikeDirectionENUM direction;

    Like(LikeDirectionENUM direction, long empresaId, long candidatoId) {
        this.direction = direction;
        this.empresaId = empresaId;
        this.candidatoId = candidatoId;
    }

    long getCandidatoId() {
        return candidatoId
    }

    long getEmpresaId() {
        return empresaId
    }

    LikeDirectionENUM getDirection() {
        return direction
    }
}
