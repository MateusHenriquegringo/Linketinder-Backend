package model.builder.director

import enums.CompetenciaENUM
import model.builder.IBuilder

import java.sql.ResultSet
import java.sql.SQLException

abstract class AbstractDirector<T> {

    abstract T constructFromResultSet(ResultSet resultSet, IBuilder<T> builder);

    abstract T constructFromResultSetWithCompetences(ResultSet resultSet, IBuilder<T> builder);

    protected List<CompetenciaENUM> extractCompetencesFromResultSet(ResultSet resultSet) throws SQLException {

        String competences = resultSet.getString('competences')

        List<CompetenciaENUM> result = new ArrayList<>()

        if (competences !== null && !competences.trim().isEmpty()) {
            List<String> competencesList = competences.split(", ")
            competencesList.forEach {
                result.add(CompetenciaENUM.valueOf(it.trim()))
            }
        }

        return result
    }

}