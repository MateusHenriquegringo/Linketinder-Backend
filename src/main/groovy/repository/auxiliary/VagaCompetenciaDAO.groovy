package repository.auxiliary

import DB.PostgresDatabaseConnection
import model.Vaga
import model.builder.AbstractBuilder
import model.builder.VagaBuilder

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class VagaCompetenciaDAO implements AuxiliaryTablesCRUD<Vaga, Long> {

    Connection connection = PostgresDatabaseConnection.getConnection();

    AbstractBuilder<Vaga> builder = new VagaBuilder()

    VagaCompetenciaDAO(Connection connection) {
        this.connection = connection
    }
    VagaCompetenciaDAO() {
    }

    @Override
    void create(Long vagaID, List<Long> competenciasID) {
        String command = "INSERT INTO vaga_competencia (vaga_id, competencia_id) VALUES (?, ?);"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            for (Long id : competenciasID) {
                pstmt.setLong(1, vagaID)
                pstmt.setLong(2, id)
                pstmt.addBatch()
            }
            pstmt.executeBatch()

        } catch (SQLException e) {
            throw new RuntimeException("erro ao relacionar competencias " + e.getMessage())
        }
    }

    @Override
    void delete(Long vagaID, List<Long> competenciasID) {
        String command = "DELETE FROM candidato_competencia WHERE candidato_id = ? AND competencia_id = ?;"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {

            for (Long id : competenciasID) {
                pstmt.setLong(1, vagaID)
                pstmt.setLong(2, id)
                pstmt.addBatch()
            }

            pstmt.executeBatch()

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover competências da vaga com ID " + vagaID.toString() + e)
        }
    }

    @Override
    Vaga findById(Long vagaID) {
        String command = SQLQuerys.LIST_VAGA_WITH_COMPETENCES.getQuery()

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, vagaID)
            ResultSet resultSet = pstmt.executeQuery()

            if (resultSet.next()) {

                return builder.buildModelFromResultSet(resultSet)

            }
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel retornar a vaga e as competencias " + e)
        }
    }

    @Override
    List<Vaga> listAllWithCompetence(Long competenceId) {
        String command = SQLQuerys.FIND_ALL_VAGAS_WITH_COMPETENCE.getQuery();

        try(PreparedStatement pstmt = connection.prepareStatement(command)){
            pstmt.setLong(1, competenceId)
            ResultSet resultSet = pstmt.executeQuery()

            List<Vaga> responseList = new ArrayList<>()
            while (resultSet.next()) {

                responseList.add(builder.buildModelFromResultSet(resultSet))

            }
            return responseList

        } catch (SQLException e) {
            throw new RuntimeException("erro ao buscar vagas por competencia "+ e)
        }
    }
}