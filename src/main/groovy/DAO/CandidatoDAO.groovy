package DAO

import DB.DatabaseConnection

import model.Candidato
import model.Competencia

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class CandidatoDAO {

    private CandidatoCompetenciaDAO candidatoCompetenciaDAO;
    private connection = DatabaseConnection.getConnection()

    void createCandidato(Candidato candidato) {
        String command = "INSERT INTO \"Candidato\" (first_name, last_name, email, cpf, city, cep, description, password)" +
                "VALUES (?, ?, ?, ? , ?, ?, ?, ?);"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, candidato.getFirst_name())
            pstmt.setString(2, candidato.getLast_name())
            pstmt.setString(3, candidato.getEmail())
            pstmt.setString(4, candidato.getCPF())
            pstmt.setString(5, candidato.getCity())
            pstmt.setString(6, candidato.getCEP())
            pstmt.setString(7, candidato.getDescription())
            pstmt.setString(8, candidato.getPassword())

            pstmt.executeUpdate()
            ResultSet generateKey = pstmt.getGeneratedKeys()

            if(candidato.getCompetencies()!=null) {
                if(candidato.getCompetencies().size()>0 )
                candidatoCompetenciaDAO = new CandidatoCompetenciaDAO(connection)

                if(generateKey.next()) candidato.setId(generateKey.getLong(1))

                candidatoCompetenciaDAO.insertCompetenciaToCandidato(candidato.getId(), candidato.getCompetencies())
            }

        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao salvar " + e.getMessage())
        } finally {
            if (!connection.isClosed()) connection.close()
        }
    }

    void insertCompetencias(long candidatoId, List<Competencia> competencias) {

        String command = "INSERT INTO Candidato_Competencia (candidato_id, competencia_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {

            competencias.forEach {
                pstmt.setLong(1, candidatoId)
                pstmt.setLong(2, it.getId())
                pstmt.addBatch()
            }
            pstmt.executeBatch();

        } finally {
            if (!connection.isClosed()) connection.close()
        }
    }

}
