package DAO

import DB.DatabaseConnection
import DTO.Response.VagaDTO
import model.Vaga

import java.sql.*

class VagaDAO {

    private Connection connection = DatabaseConnection.getConnection()

    void createVaga(Vaga vaga) {

        String command = "INSERT INTO \"Vaga\" (name, description, city, state, empresa_id) VALUES(?, ?, ?, ?, ?)"

        try (PreparedStatement pstm = connection.prepareStatement(command)) {
            pstm.setString(1, vaga.getName())
            pstm.setString(2, vaga.getDescription())
            pstm.setString(3, vaga.getCity())
            pstm.setString(4, vaga.getState())
            pstm.setLong(5, vaga.getEmpresaId())

            pstm.executeUpdate()

        } catch (SQLException e) {
            throw new RuntimeException("falha ao salvar vaga: " + e.getMessage(), e)
        }
    }

    List<VagaDTO> listAllVagas() {
        String command = "SELECT * FROM \"Vaga\";"

        try (Statement stmt = connection.createStatement()
             ResultSet setVagas = stmt.executeQuery(command)
        ) {
            List<VagaDTO> vagasResponse = new ArrayList<>()
            while (setVagas.next()) {
                vagasResponse.add(new VagaDTO(
                        setVagas.getLong("id"),
                        setVagas.getString("name"),
                        setVagas.getString("description"),
                        setVagas.getString("city"),
                        setVagas.getString("state"),
                        setVagas.getLong("empresa_id")))
            }

            return vagasResponse
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao listar as vagas " + e.getMessage())
        }
    }

    VagaDTO findVagaById(long id) {

        String command = "SELECT * FROM \"Vaga\" WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            ResultSet resultSet = pstmt.executeQuery()

            if (resultSet.next()) {
                return new VagaDTO(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("city"),
                        resultSet.getString("state"),
                        resultSet.getLong("empresa_id")
                )
            } else throw new NoSuchElementException("Essa vaga nao existe")

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar a vaga: " + e.getMessage())
        }
    }


    void updateVaga(Vaga vagaUpdate, long id) {
        String command = "UPDATE \"Vaga\" SET name=?, description=?, city=?, state=?, empresa_id=? WHERE id=?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, vagaUpdate.getName())
            pstmt.setString(2, vagaUpdate.getDescription())
            pstmt.setString(3, vagaUpdate.getCity())
            pstmt.setString(4, vagaUpdate.getState())
            pstmt.setLong(5, vagaUpdate.getEmpresaId())
            pstmt.setLong(6, id)

            pstmt.executeUpdate()

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
