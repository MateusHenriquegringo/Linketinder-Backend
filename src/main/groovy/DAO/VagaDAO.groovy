package DAO

import DB.DatabaseConnection
import DTO.Response.VagaCompetenciasDTO
import DTO.Response.VagaDTO
import model.Vaga

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class VagaDAO {

    private VagaCompetenciaDAO vagaCompetenciaDAO;
    private Connection connection = DatabaseConnection.getConnection()

    void createVaga(Vaga vaga) {
        String command = "INSERT INTO \"Vaga\" (name, description, city, state, empresa_id)" +
                "VALUES(?, ?, ?, ?, ?)"

        try (PreparedStatement pstm = connection.prepareStatement(command)) {

            pstm.setString(1, vaga.getName())
            pstm.setString(2, vaga.getDescription())
            pstm.setString(3, vaga.getCity())
            pstm.setString(4, vaga.getState())
            pstm.setLong(5, vaga.getEmpresaId())

            pstm.executeUpdate()

            ResultSet generateKey = pstmt.getGeneratedKeys()

            if(vaga.getCompetences()!=null) {
                if(vaga.getCompetences().size()> 0)
                    vagaCompetenciaDAO = new VagaCompetenciaDAO(connection)

                if(generateKey.next()) vaga.setId(generateKey.getLong(1))

                vagaCompetenciaDAO.insertCompetenciaToVaga(vaga.getId(), vaga.getCompetences())
            }
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel salvar a vaga " + e.getMessage())
        }
    }

    List<VagaDTO> listAllVagas() {
        String command = "SELECT * FROM \"Vaga\";"

        try (Statement stmt = connection.createStatement();
             ResultSet setVagas = stmt.executeQuery(command)
        ) {
            List<VagaDTO> vagasResponse = new ArrayList<>();
            while (setVagas.next()) {
                vagasResponse.add(new VagaDTO (
                        setVagas.getLong("id"),
                        setVagas.getString("name"),
                        setVagas.getString("description"),
                        setVagas.getString("city"),
                        setVagas.getString("state"),
                        setVagas.getLong("empresa_id")))
            }

            return vagasResponse;
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao listar as vagas " + e.getMessage())
        }
    }

    List<VagaCompetenciasDTO> listAllVagasAndCompetences () {
        String command = "SELECT vaga.id, vaga.name, vaga.description, vaga.city, " +
                "vaga.state, vaga.email" +
                "comp.id AS competencia_id, comp.name AS competencia_name " +
                "FROM \"Vaga\" AS vaga " +
                "LEFT JOIN \"Vaga_Competencia\" AS vaga_comp ON vaga_comp.vaga_id = vaga.id " +
                "LEFT JOIN \"Competencia\" AS comp ON cand_comp.competencia_id = comp.id;";

        List<VagaCompetenciasDTO> vagasECompetencias = new ArrayList<>()

        return vagasECompetencias
    }

    VagaDTO findVagaById(long id) {
        String command = "SELECT * FROM \"Vaga\" WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {

            pstmt.setLong(1, id)
            ResultSet result = pstmt.executeQuery()

            if (result.next()) {
                new VagaDTO(
                        result.getLong("id"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("city"),
                        result.getString("state"),
                        result.getLong("empresa_id")
                )

            } else throw new NoSuchElementException("Candidato nao encontrado")
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar " + e.getMessage())
        }
    }

    void updateVaga(Vaga vagaUpdate, long id){
        String command = "UPDATE \"Vaga\" SET name=?, description=?, city=?, state=?, empresa_id=? WHERE id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, vagaUpdate.getName())
            pstmt.setString(2, vagaUpdate.getDescription())
            pstmt.setString(3, vagaUpdate.getCity())
            pstmt.setString(4, vagaUpdate.getState())
            pstmt.setLong(5, vagaUpdate.getEmpresaId())
            pstmt.setLong(6, id)

            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao editar")
        }
    }

    void updateVaga(Vaga vagaUpdate){
        String command = "UPDATE \"Vaga\" SET name=?, description=?, city=?, state=?, empresa_id=? WHERE id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, vagaUpdate.getName())
            pstmt.setString(2, vagaUpdate.getDescription())
            pstmt.setString(3, vagaUpdate.getCity())
            pstmt.setString(4, vagaUpdate.getState())
            pstmt.setLong(5, vagaUpdate.getEmpresaId())
            pstmt.setLong(6, vagaUpdate.getId())

            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao editar")
        }
    }

    void deleteVagaById(long id) {
        String command = "DELETE FROM \"Vaga\" WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel excluir " +e.getMessage())
        }
    }

}
