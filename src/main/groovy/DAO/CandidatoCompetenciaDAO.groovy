package DAO

import enums.CompetenciasENUM
import model.Competencia

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class CandidatoCompetenciaDAO {

    private Connection connection;

    public CandidatoCompetenciaDAO(Connection connection) {
        this.connection = connection;
    }

    void insertCompetenciaToCandidato(long candidatoId, List<CompetenciasENUM> competencias) {
        String command = "INSERT INTO \"Candidato_Competencia\" (candidato_id, competencia_id)" +
                "VALUES(?, ?)"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            for (CompetenciasENUM competencia : competencias) {
                pstmt.setLong(1, candidatoId)
                pstmt.setLong(2, competencia.getId())
                pstmt.addBatch()
            }
            pstmt.executeBatch()
        } catch (SQLException e) {
            throw new RuntimeException("erro ao salvar competencias do candidato " + e.getMessage())
        }
    }

    void updateCompetencias(long candidatoId, List<CompetenciasENUM> competencias) {
        try {

            deleteCompetenciasOfCandidato(candidatoId)

            insertCompetenciaToCandidato(candidatoId, competencias)

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar competências: " + e.getMessage(), e);
        }
    }

    void deleteCompetenciasOfCandidato(long id) {
        String command = "DELETE FROM \"Candidato_Competencia\" WHERE candidato_id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {

            pstmt.setLong(1, id)
            pstmt.executeUpdate()

        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao deletar " + e.getMessage())
        }
    }

}
