package repository


import model.Candidato

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class CandidatoDAO implements CRUD<Candidato, Long> {

    private connection;

    CandidatoDAO(Connection connection) {
        this.connection = connection
    }

    @Override
    void create(Candidato candidato) {
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

        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao salvar " + e.getMessage())
        }
    }

    @Override
    void update(Candidato candidato, Long id) {
        String command = "UPDATE \"Candidato\" SET first_name=?, last_name=?, email=?, cep=?, cpf=?, city=?, description=? WHERE id=?"

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
            throw new RuntimeException("ocorreu um erro ao editar " + e.getMessage())
        }
    }

    @Override
    void delete(Long id) {
        String command = "DELETE FROM \"Candidato\" WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel excluir " + e.getMessage())
        }
    }

    @Override
    List<Candidato> listAll() {
        String command = "SELECT * FROM \"Candidato\";"

        try (Statement stmt = connection.createStatement()
             ResultSet resultSet = stmt.executeQuery(command)) {

            List<Candidato> responseList = new ArrayList<>()

            while (resultSet.next()) {
                responseList.add(
                        new Candidato(
                                resultSet.getLong("id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getString("email"),
                                resultSet.getString("description"),
                                resultSet.getString("cep"),
                                resultSet.getString("city"),
                                resultSet.getString("cpf")
                        )
                )
            }

            return responseList
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao listar candidatos " + e.getMessage())
        }
    }

    @Override
    Candidato findById(Long id) {
        String command = "SELECT * FROM \"Candidato\" WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            ResultSet resultSet = pstmt.executeQuery()

            if (resultSet.next()) {
                return  new Candidato(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("description"),
                        resultSet.getString("cep"),
                        resultSet.getString("city"),
                        resultSet.getString("cpf")
                )
            } else throw new NoSuchElementException("Candidato nao encontrado")

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar " + e.getMessage())
        }
    }

}
