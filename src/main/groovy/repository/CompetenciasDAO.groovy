package repository


import model.Competencia

import java.sql.*

class CompetenciasDAO implements CRUD<Competencia, Long>{

    private Connection connection;

    CompetenciasDAO(Connection connection) {
        this.connection = connection
    }

    @Override
    void create(Competencia competencia) {
        String command = "INSERT INTO \"Competencia\" (name) VALUES ( ? )"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, competencia.getName())
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel adicionar a competencia: " + e.getMessage())
        }
    }

    @Override
    void update(Competencia competencia, Long id) {
        String command = "UPDATE \"Competencia\" SET name = ? WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, competencia.getName())
            pstmt.setLong(2, id)
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel editar a competencia: " + e.getMessage())
        }
    }

    @Override
    void delete(Long id) {
        String command = "DELETE FROM \"Competencia\" WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel excluir: " + e.getMessage())
        }
    }

    @Override
    List<Competencia> listAll() {
        String command = "SELECT * FROM \"Competencia\";"

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(command)) {

            List<Competencia> responseList = new ArrayList<>()
            while (resultSet.next()) {
                responseList.add(new Competencia(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                ))
            }

            return responseList
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao listar as competencias: " + e.getMessage())
        }
    }

    @Override
    Competencia findById(Long id) {
        String command = "SELECT * FROM \"Competencia\" WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            ResultSet resultSet = pstmt.executeQuery()

            if (resultSet.next()) {
                return new Competencia(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                )
            } else throw new NoSuchElementException("Essa competencia nao existe")

        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel buscar a competencia: " + e.getMessage())
        }
    }

}
