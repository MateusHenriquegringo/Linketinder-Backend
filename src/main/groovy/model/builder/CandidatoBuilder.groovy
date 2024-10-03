package model.builder

import enums.CompetenciaENUM
import model.Candidato

import java.sql.ResultSet
import java.sql.SQLException

class CandidatoBuilder extends AbstractCompetencesBuilder<Candidato> implements IBuilder<Candidato>{


    @Override
    Candidato buildModelFromResultSet(ResultSet resultSet) throws SQLException {
        List<CompetenciaENUM> competences = extractCompetences(resultSet)
        return createModel(resultSet, competences)
    }

    private static Candidato createModel(ResultSet resultSet, List<CompetenciaENUM> competences) throws SQLException {
        return new Candidato(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("description"),
                resultSet.getString("cep"),
                resultSet.getString("city"),
                resultSet.getString("cpf"),
                competences
        )
    }

}
