package model.builder

import model.Candidato

import java.sql.ResultSet
import java.sql.SQLException

class CandidatoBuilder implements Builder<Candidato> {

    @Override
    Candidato buildModelFromResultSet(ResultSet resultSet) throws SQLException {

        List<String> competences = null;
        try {
            String competenciasStr = resultSet.getString("competences");
            if (competenciasStr != null && !competenciasStr.trim().isEmpty()) {
                competences = Arrays.asList(competenciasStr.split(", "));
            }
        } catch (SQLException ignored) {
            competences = null;
        }

        return buildCandidato(resultSet, competences)
    }


    private static Candidato buildCandidato(ResultSet resultSet, List<String> competences) throws SQLException {

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
