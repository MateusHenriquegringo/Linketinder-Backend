package DAO

import enums.CompetenciasENUM
import model.Competencia

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.util.stream.Collectors

class VagaCompetenciaDAO {
    private Connection connection;

    public VagaCompetenciaDAO(Connection connection) {
        this.connection = connection;
    }

    void insertCompetenciaToVaga(long vagaId, List<CompetenciasENUM> competencias) {
        connection.setAutoCommit(false);
        String command = "INSERT INTO \"Vaga_Competencia\" (vaga_id, competencia_id) VALUES(?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            for (CompetenciasENUM comp : competencias) {
                pstmt.setLong(1, vagaId);
                pstmt.setLong(2, comp.getId());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException("erro ao salvar competencias da vaga: " + e.getMessage(), e);
        } finally {
            connection.setAutoCommit(true)
        }
    }

    void updateCompetencias(long vagaId, List<CompetenciasENUM> competencias) {
        try {
            connection.setAutoCommit(false);

            deleteCompetenciasOfVaga(vagaId);

            insertCompetenciaToVaga(vagaId, competencias);

            connection.commit();
        } catch (SQLException e) {

            connection.rollback();
            throw new RuntimeException("Erro ao atualizar competências: " + e.getMessage(), e);

        } finally {
            connection.setAutoCommit(true)
        }
    }

    void deleteCompetenciasOfVaga(long id) {
        String command = "DELETE FROM \"Vaga_Competencia\" WHERE vaga_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao deletar " + e.getMessage())
        }
    }
}
