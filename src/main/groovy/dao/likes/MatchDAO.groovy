package dao.likes

import DB.ConnectionFactory
import DB.DBTypes

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class MatchDAO {

    private Connection connection

    MatchDAO(Connection connection) {
        this.connection = connection
    }

    void addMatch(long candidatoId, long empresaId){

        String command = "INSERT INTO matches (candidato_id, empresa_id) VALUES (?, ?)"

        try(PreparedStatement pstmt = connection.prepareStatement(command)){

            pstmt.setLong(1, candidatoId)
            pstmt.setLong(2, empresaId)
            pstmt.executeUpdate()

        } catch (SQLException e) {
            throw new RuntimeException("erro ao adicionar match "+ e.getMessage())
        }
    }

    void removeMatch(long candidatoId, long empresaId){
        String command = "DELETE FROM matches WHERE candidato_id = ? AND empresa_id = ?;"

        try(PreparedStatement pstmt = connection.prepareStatement(command)){

            pstmt.setLong(1, candidatoId)
            pstmt.setLong(2, empresaId)
            pstmt.executeUpdate()

        } catch (SQLException e) {
            throw new RuntimeException("erro ao adicionar match "+ e.getMessage())
        }
    }

    boolean existsMatch(long candidatoId, long empresaId){
        String command = "SELECT 1 FROM matches WHERE candidato_id = ? AND empresa_id = ?;"

        try(PreparedStatement pstmt = connection.prepareStatement(command)){

            pstmt.setLong(1, candidatoId)
            pstmt.setLong(2, empresaId)
            return pstmt.executeQuery().next()

        } catch (SQLException e) {
            throw new RuntimeException("erro ao adicionar match "+ e.getMessage())
        }
    }
}
