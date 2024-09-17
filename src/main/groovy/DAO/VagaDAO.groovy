package DAO

import DB.DatabaseConnection
import DTO.Response.CandidatoCompetenciaDTO
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
        String command = "INSERT INTO \"Vaga\" (name, description, city, state, empresa_id) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement pstm = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, vaga.getName());
            pstm.setString(2, vaga.getDescription());
            pstm.setString(3, vaga.getCity());
            pstm.setString(4, vaga.getState());
            pstm.setLong(5, vaga.getEmpresaId());

            pstm.executeUpdate();

            ResultSet generateKey = pstm.getGeneratedKeys();
            if (generateKey.next()) {
                vaga.setId(generateKey.getLong(1));
            }

            if (vaga.getCompetences() != null && !vaga.getCompetences().isEmpty()) {
                VagaCompetenciaDAO vagaCompetenciaDAO = new VagaCompetenciaDAO(connection);
                vagaCompetenciaDAO.insertCompetenciaToVaga(vaga.getId(), vaga.getCompetences());
            }

        } catch (SQLException e) {
            throw new RuntimeException("falha ao salvar vaga: " + e.getMessage(), e);
        }
    }

    List<VagaDTO> listAllVagas() {
        String command = "SELECT * FROM \"Vaga\";"

        try (Statement stmt = connection.createStatement();
             ResultSet setVagas = stmt.executeQuery(command)
        ) {
            List<VagaDTO> vagasResponse = new ArrayList<>();
            while (setVagas.next()) {
                vagasResponse.add(new VagaDTO(
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

    List<VagaCompetenciasDTO> listAllVagasAndCompetences() {
        String command = "SELECT vaga.id, vaga.name, vaga.description, vaga.city, " +
                "vaga.state, vaga.empresa_id, " +
                "comp.id AS competencia_id, comp.description AS competencia_name " +
                "FROM \"Vaga\" AS vaga " +
                "LEFT JOIN \"Vaga_Competencia\" AS vaga_comp ON vaga_comp.vaga_id = vaga.id " +
                "LEFT JOIN competencia_by_enum AS comp ON vaga_comp.competencia_id = comp.id;";

        List<VagaCompetenciasDTO> vagasECompetencias = new ArrayList<>()

        try (Statement stmt = connection.prepareStatement(command)
             ResultSet resultSet = stmt.executeQuery()) {

            Map<Long, VagaCompetenciasDTO> vagasMap = new HashMap<>()

            while (resultSet.next()) {
                long vagaId = resultSet.getLong("id")
                VagaCompetenciasDTO vaga = vagasMap.get(vagaId)

                if (vaga == null) {
                    vaga = new VagaCompetenciasDTO(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("city"),
                            resultSet.getString("state"),
                            resultSet.getLong("empresa_id"),
                            new ArrayList<String>()
                    )
                    vagasMap.put(vagaId, vaga)
                }
                String competenciaName = resultSet.getString("competencia_name")
                if (competenciaName != null && !competenciaName.isBlank()) {
                    vaga.competences().add(competenciaName)
                }
            }
            vagasECompetencias.addAll(vagasMap.values())
        } catch (SQLException e) {
            e.printStackTrace()
        }
        return vagasECompetencias
    }

    VagaCompetenciasDTO findVagaById(long id) {
        String command = "SELECT vaga.id, vaga.name, vaga.description, vaga.city, " +
                "vaga.state, vaga.empresa_id, " +
                "comp.id AS competencia_id, comp.description AS competencia_name " +
                "FROM \"Vaga\" AS vaga " +
                "LEFT JOIN \"Vaga_Competencia\" AS vaga_comp ON vaga_comp.vaga_id = vaga.id " +
                "LEFT JOIN competencia_by_enum AS comp ON vaga_comp.competencia_id = comp.id " +
                "WHERE vaga.id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id);
            ResultSet resultSet = pstmt.executeQuery();

            VagaCompetenciasDTO vaga = null;

            while (resultSet.next()) {
                if (vaga == null) {
                    vaga = new VagaCompetenciasDTO(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("city"),
                            resultSet.getString("state"),
                            resultSet.getLong("empresa_id"),
                            new ArrayList<>()
                    );
                }
                String competenciaName = resultSet.getString("competencia_name");
                if (competenciaName != null && !competenciaName.isBlank()) {
                    vaga.competences().add(competenciaName);
                }
            }

            if (vaga == null) {
                throw new NoSuchElementException("Vaga não encontrada");
            }

            return vaga;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar a vaga: " + e.getMessage());
        }
    }

    void updateVaga(Vaga vagaUpdate, long id) {
        String command = "UPDATE \"Vaga\" SET name=?, description=?, city=?, state=?, empresa_id=? WHERE id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, vagaUpdate.getName())
            pstmt.setString(2, vagaUpdate.getDescription())
            pstmt.setString(3, vagaUpdate.getCity())
            pstmt.setString(4, vagaUpdate.getState())
            pstmt.setLong(5, vagaUpdate.getEmpresaId())
            pstmt.setLong(6, id)

            pstmt.executeUpdate()

            if (vagaUpdate.getCompetences() != null && !vagaUpdate.getCompetences().isEmpty()) {
                VagaCompetenciaDAO vagaCompetenciaDAO = new VagaCompetenciaDAO(connection);
                vagaCompetenciaDAO.updateCompetencias(id, vagaUpdate.getCompetences());
            }
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao editar " + e.getMessage())
        }
    }

    void deleteVagaById(long id) {
        String command = "DELETE FROM \"Vaga\" WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel excluir " + e.getMessage())
        }
    }

}
