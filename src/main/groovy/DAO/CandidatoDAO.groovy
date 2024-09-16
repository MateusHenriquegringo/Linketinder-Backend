package DAO

import DB.DatabaseConnection
import DTO.Response.CandidatoCompetenciaDTO
import DTO.Response.CandidatoResponseDTO
import groovy.sql.Sql
import model.Candidato

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

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

            if (candidato.getCompetencies() != null) {
                if (candidato.getCompetencies().size() > 0)
                    candidatoCompetenciaDAO = new CandidatoCompetenciaDAO(connection)

                if (generateKey.next()) candidato.setId(generateKey.getLong(1))

                candidatoCompetenciaDAO.insertCompetenciaToCandidato(candidato.getId(), candidato.getCompetencies())
            }

        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao salvar " + e.getMessage())
        }
    }

    List<CandidatoResponseDTO> listAllCandidatos() {

        String command = "SELECT * FROM \"Candidato\";"

        try (Statement stmt = connection.createStatement();
             ResultSet setCandidatos = stmt.executeQuery(command)
        ) {
            List<CandidatoResponseDTO> candidatosResponse = new ArrayList<>();
            while (setCandidatos.next()) {

                candidatosResponse.add(
                        new CandidatoResponseDTO.Builder()
                                .CEP(setCandidatos.getString("cep"))
                                .city(setCandidatos.getString("city"))
                                .CPF(setCandidatos.getString("cpf"))
                                .description(setCandidatos.getString("description"))
                                .email(setCandidatos.getString("email"))
                                .firstName(setCandidatos.getString("first_name"))
                                .lastName(setCandidatos.getString("last_name"))
                                .id(setCandidatos.getLong("id"))
                                .build())
            }

            return candidatosResponse;
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao listar candidatos " + e.getMessage())
        }

    }

    // TODO
    List<CandidatoCompetenciaDTO> listAllCandidatosAndCompetencias() {

        String command = "SELECT candidato.id, candidato.first_name, candidato.last_name, candidato.cpf, " +
                "candidato.description, candidato.email, candidato.cep, candidato.city, " +
                "comp.id AS competencia_id, comp.name AS competencia_name " +
                "FROM \"Candidato\" AS candidato " +
                "LEFT JOIN \"Candidato_Competencia\" AS cand_comp ON cand_comp.candidato_id = candidato.id " +
                "LEFT JOIN \"Competencia\" AS comp ON cand_comp.competencia_id = comp.id;";

        try (Statement stmt = connection.prepareStatement(command)
             ResultSet candidatosCompetenciasSet = stmt.executeQuery()) {
            List<CandidatoCompetenciaDTO> candidadosComeptencias = new ArrayList<>()

        }
        return candidadosComeptencias
    }

    CandidatoResponseDTO findCandidatoById(long id) {

        String command = "SELECT * FROM \"Candidato\" WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {

            pstmt.setLong(1, id)
            ResultSet result = pstmt.executeQuery()

            if (result.next()) {
                return new CandidatoResponseDTO.Builder()
                        .CEP(result.getString("cep"))
                        .city(result.getString("city"))
                        .CPF(result.getString("cpf"))
                        .description(result.getString("description"))
                        .email(result.getString("email"))
                        .firstName(result.getString("first_name"))
                        .lastName(result.getString("last_name"))
                        .id(result.getLong("id"))
                        .build()

            } else throw new NoSuchElementException("Candidato nao encontrado")
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar " + e.getMessage())
        }
    }

    void updateCandidato(Candidato candidato, long id) {
        String command = "UPDATE \"Candidato\" SET first_name=?, last_name=?, email=?, cep=?, cpf=?, city=?, description=? WHERE id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, candidato.getFirst_name())
            pstmt.setString(2, candidato.getLast_name())
            pstmt.setString(3, candidato.getEmail())
            pstmt.setString(4, candidato.getCEP())
            pstmt.setString(5, candidato.getCPF())
            pstmt.setString(6, candidato.getCity())
            pstmt.setString(7, candidato.getDescription())
            pstmt.setLong(8, id)

            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao editar")
        }

    }

    void updateCandidato(Candidato candidato) {
        String command = "UPDATE \"Candidato\" SET first_name=?, last_name=?, email=?, cep=?, cpf=?, city=?, description=? WHERE id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, candidato.getFirst_name())
            pstmt.setString(2, candidato.getLast_name())
            pstmt.setString(3, candidato.getEmail())
            pstmt.setString(4, candidato.getCEP())
            pstmt.setString(5, candidato.getCPF())
            pstmt.setString(6, candidato.getCity())
            pstmt.setString(7, candidato.getDescription())
            pstmt.setLong(8, candidato.getId())

            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao editar")
        }

    }

    void deleteCandidatoById(long id) {
        String command = "DELETE FROM \"Candidato\" WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel excluir " +e.getMessage())
        }
    }
}
