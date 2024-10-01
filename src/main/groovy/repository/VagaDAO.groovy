package repository

import DB.PostgresDatabaseConnection
import model.Vaga
import model.builder.Builder
import model.builder.VagaBuilder

import java.sql.*

class VagaDAO implements ModelsCRUD<Vaga, Long> {

    private Builder<Vaga> builder = new VagaBuilder()

    private Connection connection =  PostgresDatabaseConnection.getConnection();

    VagaDAO(Connection connection) {
        this.connection = connection
    }
    VagaDAO() {
    }

    @Override
    long create(Vaga vaga) {
        String command = "INSERT INTO vaga (vaga_name, description, city, state, empresa_id) VALUES(?, ?, ?, ?, ?)"

        try (PreparedStatement pstm = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, vaga.getVaga_name())
            pstm.setString(2, vaga.getDescription())
            pstm.setString(3, vaga.getCity())
            pstm.setString(4, vaga.getState())
            pstm.setLong(5, vaga.getEmpresaId())

            pstm.executeUpdate()

            ResultSet keys = pstm.getGeneratedKeys()
            return keys.next() ? keys.getLong(1) : -1

        } catch (SQLException e) {
            throw new RuntimeException("falha ao salvar vaga: " + e.getMessage(), e)
        }
    }

    @Override
    void update(Vaga vagaUpdate, Long id) {
        String command = "UPDATE vaga SET vaga_name=?, description=?, city=?, state=?, empresa_id=? WHERE id=?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, vagaUpdate.getVaga_name())
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

    @Override
    void delete(Long id) {
        String command = "DELETE FROM vaga WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel excluir " + e.getMessage())
        }
    }

    @Override
    List<Vaga> listAll() {
        String command = "SELECT * FROM vaga;"

        try (Statement stmt = connection.createStatement()
             ResultSet resultSet = stmt.executeQuery(command)
        ) {
            List<Vaga> responseList = new ArrayList<>()
            while (resultSet.next()) {
                responseList.add(
                        builder.buildModelFromResultSet(resultSet)
                )
            }

            return responseList
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao listar as vagas " + e.getMessage())
        }
    }

    @Override
    Vaga findById(Long id) {
        String command = "SELECT * FROM vaga WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            ResultSet resultSet = pstmt.executeQuery()

            if (resultSet.next()) {

                return builder.buildModelFromResultSet(resultSet)

            } else {
                throw new NoSuchElementException("Essa vaga nao existe")
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar a vaga: " + e.getMessage())
        }
    }


}
