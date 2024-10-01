package model.builder

import java.sql.ResultSet
import java.sql.SQLException

abstract class AbstractBuilder<T> {

    T buildModelFromResultSet(ResultSet resultSet) throws SQLException {
        List<String> competences = extractCompetences(resultSet)
        return createModel(resultSet, competences)
    }

    private List<String> extractCompetences (ResultSet resultSet) throws SQLException {
        List<String> competences = null;

        try {
            String competenciasStr = resultSet.getString("competences");
            if (competenciasStr != null && !competenciasStr.trim().isEmpty()) {
                competences = Arrays.asList(competenciasStr.split(", "));
            }
        } catch (SQLException ignored) {
            competences = null;
        }

        return competences
    }

    protected abstract T createModel(ResultSet resultSet, List<String> competences) throws SQLException;
}
