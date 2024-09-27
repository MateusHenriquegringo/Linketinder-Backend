package repository.auxiliary

import model.Candidato
import model.Competencia
import model.builder.Builder
import model.builder.CandidatoBuilder

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class CandidatoCompetenciaDAO implements AuxiliaryTablesCRUD<Candidato, Competencia, Long> {

    Connection connection;

    Builder<Candidato> builder = new CandidatoBuilder()

    CandidatoCompetenciaDAO(Connection connection) {
        this.connection = connection
    }

    @Override
    void create(Long candidatoID, List<Long> competenciasID) {

        String command = "INSERT INTO \"Candidato_Competencia\" (candidato_id, competencia_id) VALUES (?, ?);"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            for (Long id : competenciasID) {
                pstmt.setLong(1, candidatoID)
                pstmt.setLong(2, id)
                pstmt.addBatch()
            }
            pstmt.executeBatch()

        } catch (SQLException e) {
            throw new RuntimeException("erro ao relacionar competencias " + e.getMessage())
        }
    }

    @Override
    void delete(Long candidatoID, List<Long> competenciasID) {

        String command = "DELETE FROM \"Candidato_Competencia\" WHERE candidato_id = ? AND competencia_id = ?;"

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {

            for (Long id : competenciasID) {
                pstmt.setLong(1, candidatoID)
                pstmt.setLong(2, id)
                pstmt.addBatch()
            }

            pstmt.executeBatch()

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover competências do candidato com ID " + candidatoID.toString() + e)
        }
    }

    @Override
    Candidato findById ( Long candidatoID ) {
        String command = SQL.LIST_WITH_COMPETENCES.getQuery()

        try (PreparedStatement pstmt = connection.prepareStatement(command)) {
            pstmt.setLong(1, candidatoID)
            ResultSet resultSet = pstmt.executeQuery()

            if (resultSet.next()) {

                return builder.buildModelFromResultSet(resultSet)

            }
        } catch (SQLException e) {
            throw new RuntimeException("nao foi possivel retornar o candidato e as competencias " + e)
        }
    }

    @Override
    List<Candidato> findAllWithCompetence(Long competenceId) {
        String command = SQL.FIND_ALL_CANDIDATOS_WITH_COMPETENCE.getQuery();

        try(PreparedStatement pstmt = connection.prepareStatement(command)){
            pstmt.setLong(1, competenceId)
            ResultSet resultSet = pstmt.executeQuery()

            List<Candidato> responseList = new ArrayList<>()
            while (resultSet.next()) {

                responseList.add(builder.buildModelFromResultSet(resultSet))

            }
            return responseList

        } catch (SQLException e) {
            throw new RuntimeException("erro ao buscar candidatos por competencia "+ e)
        }


    }

    private enum SQL {

        LIST_WITH_COMPETENCES(
                """SELECT c.id, c.first_name, c.last_name, c.CPF, c.email, c.CEP, c.city, c.description
                STRING_AGG(comp.description::text, ', ') AS competences
            FROM \"Candidato\" c
            JOIN \"Candidato_Competencia\" cc ON c.id = cc.candidato_id
            JOIN competencia_by_enum comp ON cc.competencia_id = comp.id
            WHERE c.id = ? 
            GROUP BY c.id, c.first_name, c.last_name, c.CPF, c.email, c.CEP, c.city, c.description;
                """
        ),

        FIND_ALL_CANDIDATOS_WITH_COMPETENCE(
          """
        SELECT  c.id, c.first_name, c.last_name, c.CPF, c.email, c.CEP, c.city, c.description, 
            STRING_AGG(comp.description::text, ', ') AS competences
            FROM "Candidato" c
            JOIN "Candidato_Competencia" cc ON c.id = cc.candidato_id
            JOIN competencia_by_enum comp ON cc.competencia_id = comp.id
            
        WHERE c.id IN (
             SELECT c.id
            FROM "Candidato" c
            JOIN "Candidato_Competencia" cc ON c.id = cc.candidato_id
           WHERE cc.competencia_id = ? )   
           
           GROUP BY c.id, c.first_name, c.last_name, c.CPF, c.email, c.CEP, c.city, c.description;          
"""
        )

        String getQuery() {
            return query
        }
        private String query

        SQL(String query) {
            this.query = query
        }
    }
}
