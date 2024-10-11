package dao.likes

import DB.ConnectionFactory
import DB.DBTypes

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class MatchesDAO {

    private Connection connection = ConnectionFactory.getConnection(DBTypes.POSTGRES)

    MatchesDAO(Connection connection) {
        this.connection = connection
    }

    void addMatch(Match match){

        String command = "INSERT INTO matches (candidato_id, empresa_id) VALUES (?, ?)"

        try(PreparedStatement pstmt = connection.prepareStatement(command)){

            pstmt.setLong(1, match.getCandidatoId())
            pstmt.setLong(2, match.getEmpresaId())
            pstmt.executeUpdate()

        } catch (SQLException e) {
            throw new RuntimeException("erro ao adicionar match "+ e.getMessage())
        }
    }

    void removeMatch(Match match){

        String command = "DELETE FROM matches WHERE candidato_id = ? AND empresa_id = ?;"

        try(PreparedStatement pstmt = connection.prepareStatement(command)){

            pstmt.setLong(1, match.getCandidatoId())
            pstmt.setLong(2, match.getEmpresaId())
            pstmt.executeUpdate()

        } catch (SQLException e) {
            throw new RuntimeException("erro ao adicionar match "+ e.getMessage())
        }
    }
}
