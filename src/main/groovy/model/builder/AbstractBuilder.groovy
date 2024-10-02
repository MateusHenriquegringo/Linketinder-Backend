package model.builder

import enums.CompetenciaENUM

import java.sql.ResultSet
import java.sql.SQLException

abstract class AbstractBuilder<T> {

    T buildModelFromResultSet(ResultSet resultSet) throws SQLException {
        List<CompetenciaENUM> competences = extractCompetences(resultSet)
        return createModel(resultSet, competences)
    }

    private List<CompetenciaENUM> extractCompetences(ResultSet resultSet) throws SQLException {
        List<CompetenciaENUM> competences = new ArrayList<>()

        try {
            String competenciasStr = resultSet.getString("competences")
            if (competenciasStr != null && !competenciasStr.trim().isEmpty()) {
                for (String competenciaString : competenciasStr.split(", ")) {
                    competences.add(CompetenciaENUM.fromString(competenciaString))
                }
            }
        } catch (SQLException ignored) {
            ignored.printStackTrace()
        }

        return competences
    }


    protected abstract T createModel(ResultSet resultSet, List<CompetenciaENUM> competences) throws SQLException;
}
