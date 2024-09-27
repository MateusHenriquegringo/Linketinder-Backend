package model.builder

import model.Vaga

import java.sql.ResultSet
import java.sql.SQLException

class VagaBuilder implements Builder<Vaga>{

    @Override
    Vaga buildModelFromResultSet(ResultSet resultSet) throws SQLException {

        List<String> competences = null;
        try {
            String competenciasStr = resultSet.getString("competences");
            if (competenciasStr != null && !competenciasStr.trim().isEmpty()) {
                competences = Arrays.asList(competenciasStr.split(", "));
            }
        } catch (SQLException ignored) {
            competences = null;
        }

        return buildVaga(resultSet, competences)
    }


    private static Vaga buildVaga(ResultSet resultSet, List<String> competences) throws SQLException {

        return new Vaga (
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("city"),
                resultSet.getString("state"),
                resultSet.getLong("empresa_id"),
                competences
        )
    }

}
