package model.builder

import enums.CompetenciaENUM
import model.Vaga

import java.sql.ResultSet
import java.sql.SQLException

class VagaBuilder extends AbstractCompetencesBuilder<Vaga> implements IBuilder<Vaga> {

    private static Vaga createModel(ResultSet resultSet, List<CompetenciaENUM> competences) throws SQLException {

        return new Vaga(
                resultSet.getLong("id"),
                resultSet.getString("vaga_name"),
                resultSet.getString("description"),
                resultSet.getString("city"),
                resultSet.getString("state"),
                resultSet.getLong("empresa_id"),
                competences
        )
    }

    @Override
    Vaga buildModelFromResultSet(ResultSet resultSet) throws SQLException {
        List<CompetenciaENUM> competences = extractCompetences(resultSet)
        return createModel(resultSet, competences)
    }
}
