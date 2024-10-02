package model.builder

import enums.CompetenciaENUM
import model.Vaga

import java.sql.ResultSet
import java.sql.SQLException

class VagaBuilder extends AbstractBuilder<Vaga> {

    @Override
    protected Vaga createModel(ResultSet resultSet, List<CompetenciaENUM> competences) throws SQLException {

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


}
