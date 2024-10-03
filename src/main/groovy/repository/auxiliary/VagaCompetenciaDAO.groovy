package repository.auxiliary

import DB.PostgresDatabaseConnection
import enums.CompetenciaENUM
import model.Candidato
import model.Vaga
import model.builder.IVagaBuilder
import model.builder.VagaBuilder
import model.builder.director.VagaDirector

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class VagaCompetenciaDAO implements AuxiliaryTablesCRUD<Vaga, Long, CompetenciaENUM> {

    Connection connection = PostgresDatabaseConnection.getConnection();

    VagaDirector director = new VagaDirector()
    IVagaBuilder builder = new VagaBuilder()

    VagaCompetenciaDAO(Connection connection) {
        this.connection = connection
    }
    VagaCompetenciaDAO() {
    }

    @Override
    void create(Long vagaID, List<CompetenciaENUM> competences) {
        String command = "INSERT INTO vaga_competencia (vaga_id, competences) VALUES (?, ?);"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            for (CompetenciaENUM competencia : competences) {
                pstmt.setLong(1, vagaID)
                pstmt.setString(2, competencia.toString())
                pstmt.addBatch()
            }
            pstmt.executeBatch()

        } catch (SQLException e) {
            throw new RuntimeException("erro ao relacionar competencias " + e.getMessage())
        }
    }

    @Override
    void delete(Long vagaID, List<CompetenciaENUM> competences) {
        String command = "DELETE FROM candidato_competencia WHERE candidato_id = ? AND competences = ?;"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {

            for (CompetenciaENUM competence : competences) {
                pstmt.setLong(1, vagaID)
                pstmt.setString(2, competence.toString())
                pstmt.addBatch()
            }

            pstmt.executeBatch()

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover competências da vaga com ID " + vagaID.toString() + e)
        }
    }

    @Override
    Vaga findById(Long vagaID) {
        String command = SQLQuerys.RETURN_VAGA_WITH_COMPETENCES.getQuery()

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, vagaID)
            ResultSet resultSet = pstmt.executeQuery()

            if (resultSet.next()) {

                return director.constructFromResultSetWithCompetences(resultSet, builder)

            }
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel retornar a vaga e as competencias " + e)
        }
    }

    @Override
    List<Vaga> listAll(){
        String command = SQLQuerys.LIST_ALL_VAGAS_JOIN_COMPETENCIAS.getQuery()

        List<Vaga> resultList = new ArrayList<>()
        try (Statement stmt = connection.createStatement()
             ResultSet resultSet = stmt.executeQuery(command)) {

            while (resultSet.next()) {
                resultList.add(
                        director.constructFromResultSetWithCompetences(resultSet, builder)
                )
            }

            return resultList

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao retornar as vagas e as competencias exigidas " + e.getMessage())
        }
    }

}