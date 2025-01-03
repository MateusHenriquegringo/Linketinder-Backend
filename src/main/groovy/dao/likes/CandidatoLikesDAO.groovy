package dao.likes

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class CandidatoLikesDAO {


    private Connection connection

    CandidatoLikesDAO (Connection connection) {
        this.connection = connection
    }

    void addLike(long from, long to){

        String command = "INSERT INTO candidato_likes (candidato_id, vaga_id) VALUES (?, ?)"

        try(PreparedStatement pstmt = connection.prepareStatement(command)){

            pstmt.setLong(1, from)
            pstmt.setLong(2, to)
            pstmt.executeUpdate()

        } catch (SQLException e) {
            throw new RuntimeException("erro ao adicionar like "+ e.getMessage())
        }
    }

    void removeLike(long from, long to){

        String command = "DELETE FROM candidato_likes WHERE candidato_id = ? AND vaga_id = ?;"

        try(PreparedStatement pstmt = connection.prepareStatement(command)){

            pstmt.setLong(1, from)
            pstmt.setLong(2, to)
            pstmt.executeUpdate()

        } catch (SQLException e) {
            throw new RuntimeException("erro ao adicionar match "+ e.getMessage())
        }
    }

}
