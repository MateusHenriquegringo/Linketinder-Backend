package model.builder.director

import model.Vaga
import model.builder.IBuilder

import java.sql.ResultSet
import java.sql.SQLException

class VagaDirector extends AbstractDirector<Vaga> {

    @Override
    Vaga constructFromResultSet(ResultSet resultSet, IBuilder<Vaga> builder) {

        try {
            builder.reset()
            return builder
                    .setId(resultSet.getLong("id"))
                    .setVagaName(resultSet.getString("vaga_name"))
                    .setDescription(resultSet.getString("description"))
                    .setCity(resultSet.getString("city"))
                    .setState(resultSet.getString("state"))
                    .setEmpresaID(resultSet.getLong("empresa_id"))
                    .build()

        } catch (SQLException e) {
            throw new RuntimeException("erro ao buscar vagas "+ e.getMessage())
        }
    }

    @Override
    Vaga constructFromResultSetWithCompetences(ResultSet resultSet, IBuilder<Vaga> builder) {

        try {
            builder.reset()
            return builder
                    .setId(resultSet.getLong("id"))
                    .setVagaName(resultSet.getString("vaga_name"))
                    .setDescription(resultSet.getString("description"))
                    .setCity(resultSet.getString("city"))
                    .setState(resultSet.getString("state"))
                    .setEmpresaID(resultSet.getLong("empresa_id"))
                    .setCompetences(this.extractCompetencesFromResultSet(resultSet))
                    .build()
        } catch (SQLException e) {
            throw new RuntimeException("erro ao buscar vagas "+ e.getMessage())
        }
    }

}
