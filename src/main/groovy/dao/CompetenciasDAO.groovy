package dao

import DB.ConnectionFactory
import DB.DBTypes
import model.Competencia

import java.sql.*

class CompetenciasDAO implements ModelsCRUD<Competencia, Long>{

    private Connection connection

    CompetenciasDAO(Connection connection) {
        this.connection = connection
    }

    @Override
    long create(Competencia competencia) {
        String command = "INSERT INTO competencia_input (description) VALUES ( ? )"

        try (PreparedStatement pstmt = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, competencia.getDescription())
            pstmt.executeUpdate()

            ResultSet keys = pstmt.getGeneratedKeys()
            return keys.next() ? keys.getLong(1) : -1

        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel adicionar a competencia: " + e.getMessage())
        }
    }

    @Override
    void update(Competencia competencia, Long id) {
        String command = "UPDATE competencia_input SET description = ? WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, competencia.getDescription())
            pstmt.setLong(2, id)
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel editar a competencia: " + e.getMessage())
        }
    }

    @Override
    void delete(Long id) {
        String command = "DELETE FROM competencia_input WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel excluir: " + e.getMessage())
        }
    }

    @Override
    List<Competencia> listAll() {
        String command = "SELECT * FROM competencia_by_enum;"

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(command)) {

            List<Competencia> responseList = new ArrayList<>()
            while (resultSet.next()) {
                responseList.add(new Competencia(
                        resultSet.getLong("id"),
                        resultSet.getString("description")
                ))
            }

            return responseList
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao listar as competencias: " + e.getMessage())
        }
    }

    @Override
    Competencia findById(Long id) {
        String command = "SELECT * FROM competencia_by_enum WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            ResultSet resultSet = pstmt.executeQuery()

            if (resultSet.next()) {
                return new Competencia(
                        resultSet.getLong("id"),
                        resultSet.getString("description")
                )
            } else throw new NoSuchElementException("Essa competencia nao existe")

        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel buscar a competencia: " + e.getMessage())
        }
    }

}
