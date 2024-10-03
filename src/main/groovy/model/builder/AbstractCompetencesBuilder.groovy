package model.builder

import enums.CompetenciaENUM

import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.SQLException

abstract class AbstractCompetencesBuilder<T> {

/*    T buildModelFromResultSet(ResultSet resultSet) throws SQLException {
            List<CompetenciaENUM> competences = extractCompetences(resultSet)
            return createModel(resultSet, competences)
    }*/

    protected List<CompetenciaENUM> extractCompetences(ResultSet resultSet) throws SQLException {
        List<CompetenciaENUM> competences = new ArrayList<>()

        try {
            String competenciasStr = resultSet.getString("competences")

            if (competenciasStr != null && !competenciasStr.trim().isEmpty()){
                Arrays.stream(competenciasStr.split(", "))
                        .map(CompetenciaENUM::fromString)
                        .forEach(competences::add)
            }

        } catch (SQLException ignored) {
            competences = null
        }

        return competences
    }

}
