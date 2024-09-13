package DAO

import main.groovy.model.Competencia

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

class CandidatoCompetenciaDAO {

    private Connection connection;

    public CandidatoCompetenciaDAO(Connection connection) {
        this.connection = connection;
    }

    void insertCompetenciaToCandidato (long candidatoId, List<Competencia> competencias){

        String command = "INSERT INTO Candidato_Competencia (candidato_id, competencia_id)" +
                "VALUES(?, ?)"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            competencias.forEach {
                pstmt.setLong(1, candidatoId)
                pstmt.setLong(2, it.getId())
                pstmt.addBatch()
            }
            pstmt.executeBatch()
        } catch (SQLException e) {
            throw new RuntimeException("erro ao salvar competencias do candidato")
        }
    }
}
