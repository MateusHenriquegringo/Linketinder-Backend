package DAO

import model.Competencia

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class VagaCompetenciaDAO {
    private Connection connection;

    public VagaCompetenciaDAO(Connection connection) {
        this.connection = connection;
    }

    void insertCompetenciaToVaga (long vagaId, List<Competencia> competencias){

        String command = "INSERT INTO Vaga_Competencia (vaga_id, competencia_id)" +
                "VALUES(?, ?)"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            competencias.forEach {
                pstmt.setLong(1, vagaId)
                pstmt.setLong(2, it.getId())
                pstmt.addBatch()
            }
            pstmt.executeBatch()
        } catch (SQLException e) {
            throw new RuntimeException("erro ao salvar competencias do candidato " + e.getMessage())
        }
    }
}
