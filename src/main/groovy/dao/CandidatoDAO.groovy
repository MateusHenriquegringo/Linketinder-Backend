package dao

import DB.ConnectionFactory
import DB.DBTypes
import model.Candidato
import model.builder.CandidatoBuilder
import model.builder.ICandidatoBuilder
import model.builder.director.CandidatoDirector

import java.sql.*

class CandidatoDAO implements ModelsCRUD<Candidato, Long> {

    ICandidatoBuilder builder = new CandidatoBuilder()
    CandidatoDirector director = new CandidatoDirector()

    private Connection connection

    CandidatoDAO(Connection connection) {
        this.connection = connection
    }

    @Override
    long create(Candidato candidato) {
        String command = "INSERT INTO candidato (first_name, last_name, email, cpf, city, cep, description, password)" +
                "VALUES (?, ?, ?, ? , ?, ?, ?, ?);"

        try (PreparedStatement pstmt = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, candidato.getFirst_name())
            pstmt.setString(2, candidato.getLast_name())
            pstmt.setString(3, candidato.getEmail())
            pstmt.setString(4, candidato.getCpf())
            pstmt.setString(5, candidato.getCity())
            pstmt.setString(6, candidato.getCep())
            pstmt.setString(7, candidato.getDescription())
            pstmt.setString(8, candidato.getPassword())

            pstmt.executeUpdate()

            ResultSet keys = pstmt.getGeneratedKeys()

            return keys.next() ? keys.getLong("id") : -1

        }
        catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao salvar " + e.getMessage())
        }
    }

    @Override
    void update(Candidato candidato, Long id) {
        String command = "UPDATE candidato SET first_name=?, last_name=?, email=?, cep=?, cpf=?, city=?, description=? WHERE id=?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, candidato.getFirst_name())
            pstmt.setString(2, candidato.getLast_name())
            pstmt.setString(3, candidato.getEmail())
            pstmt.setString(4, candidato.getCep())
            pstmt.setString(5, candidato.getCpf())
            pstmt.setString(6, candidato.getCity())
            pstmt.setString(7, candidato.getDescription())
            pstmt.setLong(8, id)

            int rowsAffected = pstmt.executeUpdate()

            if (rowsAffected == 0) throw new RuntimeException("Candidato nao encontrado")

        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao editar " + e.getMessage())
        }
    }

    @Override
    void delete(Long id) {
        String command = "DELETE FROM candidato WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel excluir " + e.getMessage())
        }
    }

    @Override
    List<Candidato> listAll() {
        String command = "SELECT * FROM candidato;"

        try (Statement stmt = connection.createStatement()
             ResultSet resultSet = stmt.executeQuery(command)) {

            List<Candidato> responseList = new ArrayList<>()
            while (resultSet.next()) {
                responseList.add(
                        director.constructFromResultSet(resultSet, builder)
                )
            }

            return responseList
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao listar candidatos " + e.getMessage())
        }
    }

    @Override
    Candidato findById(Long id) {
        String command = "SELECT * FROM candidato WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            ResultSet resultSet = pstmt.executeQuery()

            if (resultSet.next()) {

                return director.constructFromResultSet(resultSet, builder)

            } else throw new NoSuchElementException("Candidato nao encontrado")

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar " + e.getMessage())
        }
    }

}
