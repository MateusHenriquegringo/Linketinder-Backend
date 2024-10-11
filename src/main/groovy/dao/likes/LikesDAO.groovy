package dao.likes

import DB.ConnectionFactory
import DB.DBTypes
import enums.LikeDirectionENUM
import dao.likes.Like
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class LikesDAO {

    private Connection connection = ConnectionFactory.getConnection(DBTypes.POSTGRES)

    LikesDAO(Connection connection) {
        this.connection = connection
    }

    void addLike(Like like){
        String command = "INSERT INTO likes (candidato_id, empresa_id, direction) VALUES (?, ?, ?);"

        try(PreparedStatement pstmt = connection.prepareStatement(command)) {

            pstmt.setLong(1, like.getCandidatoId())
            pstmt.setLong(2, like.getEmpresaId())
            pstmt.setString(3, like.getDirection().toString())

            pstmt.executeUpdate()
        } catch (SQLException e){
            throw new RuntimeException("Erro ao dar like "+ e.getMessage())
        }
    }

    void removeLike(Like like) {
        String command = "DELETE FROM likes WHERE candidato_id = ? AND empresa_id = ? AND direction = ?;"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, like.getCandidatoId())
            pstmt.setLong(2, like.getEmpresaId())
            pstmt.setString(3, like.getDirection().toString())

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover like: " + e.getMessage(), e);
        }
    }

    boolean existsLike(Like like) {
        String query = "SELECT COUNT(*) FROM likes WHERE candidato_id = ? AND empresa_id = ? AND direction = ?;";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, like.getCandidatoId());
            pstmt.setLong(2, like.getEmpresaId());
            pstmt.setString(3, like.getDirection().toString());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar se o like existe: " + e.getMessage(), e);
        }
        return false;
    }
}
