package dao.likes

import DB.ConnectionFactory
import DB.DBTypes

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class MatchesDAO {

    private Connection connection

    MatchesDAO(Connection connection) {
        this.connection = connection
    }

    void addMatch(long candidatoId, long empresaId) {

        String command = "INSERT INTO matches (candidato_id, empresa_id) VALUES (?, ?)"

        try(PreparedStatement pstmt = connection.prepareStatement(command)){

            pstmt.setLong(1, candidatoId)
            pstmt.setLong(2, empresaId)
            pstmt.executeUpdate()

        } catch (SQLException e) {
            throw new RuntimeException("erro ao adicionar match "+ e.getMessage())
        }
    }

    void removeMatch(long candidatoId, long empresaId) {

        String command = "DELETE FROM matches WHERE candidato_id = ? AND empresa_id = ?;"

        try(PreparedStatement pstmt = connection.prepareStatement(command)){

            pstmt.setLong(1, candidatoId)
            pstmt.setLong(2, empresaId)
            pstmt.executeUpdate()

        } catch (SQLException e) {
            throw new RuntimeException("erro ao adicionar match "+ e.getMessage())
        }
    }

    boolean verifyMatchAfterEmpresaLike(long empresaID, long candidatoID) {
        String query = """
    SELECT 1
    FROM candidato_likes cl
    JOIN vaga v ON cl.vaga_id = v.id
    JOIN empresa_likes el ON el.empresa_id = v.empresa_id 
                      AND el.candidato_id = cl.candidato_id
    WHERE cl.candidato_id = ?
        AND v.empresa_id = ?
        AND cl.created_at > el.created_at
    LIMIT 1;
    """
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, candidatoID)
            pstmt.setLong(2, empresaID)

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next()
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar o match: " + e.getMessage())
        }
    }

    boolean verifyMatchAfterCandidatoLike(long candidatoId, long vagaId) {
        String query = """
        SELECT 1
        FROM empresas_like el
        JOIN vaga v ON v.empresa_id = el.empresa_id
        WHERE el.candidato_id = ? AND v.id = ?
        LIMIT 1;
    """
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, candidatoId)
            pstmt.setLong(2, vagaId)

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next()
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar o match: " + e.getMessage())
        }
    }
}
