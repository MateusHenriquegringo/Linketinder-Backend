package DAO

import DB.DatabaseConnection
import model.Competencia

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class CompetenciasDAO {

    private Connection connection = DatabaseConnection.getConnection();

    List<Competencia> listAllCompetencias() {
        String command = "SELECT * FROM \"Competencia\";"

        try (Statement stmt = connection.createStatement();
             ResultSet setCompetencia = stmt.executeQuery(command)) {

            List<Competencia> competenciasResponse = new ArrayList<>();
            while (setCompetencia.next()) {
                competenciasResponse.add(new Competencia(
                        setCompetencia.getLong("id"),
                        setCompetencia.getString("name")
                ))
            }

            return competenciasResponse;
        } catch (SQLException e) {
            throw new RuntimeException("ocorreu um erro ao listar as vagas " + e.getMessage())
        }
    }

    Competencia findCompetenciaById(long id) {
        String command = "SELECT * FROM \"Competencia\" WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            ResultSet setCompetencia = pstmt.executeQuery()

            while (setCompetencia.next()) {
                return new Competencia(
                        setCompetencia.getLong("id"),
                        setCompetencia.getString("name")
                )
            }
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel buscar a Competencia " + e.getMessage())
        }
    }

    void createCompetencia(Competencia competencia) {
        String command = "INSERT INTO \"Competencia\" (name) VALUES ( ? )";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, competencia.getName())
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel adicionar a competencia " + e.getMessage())
        }
    }

    void editCompetencia(Competencia competencia) {
        String command = "UPDATE \"Competencia\" SET name = ? WHERE id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setString(1, competencia.getName())
            pstmt.setLong(2, competencia.getId())
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel editar a competencia " + e.getMessage())
        }
    }

    void deleteCompetencia(long id) {
        String command = "DELETE FROM \"Competencia\" WHERE id = ?"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, id)
            pstmt.executeUpdate()
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel excluir " + e.getMessage())
        }
    }
}
