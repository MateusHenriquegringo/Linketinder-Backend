package model.builder.director

import model.Candidato
import model.builder.IBuilder
import model.builder.ICandidatoBuilder

import java.sql.ResultSet
import java.sql.SQLException

class CandidatoDirector extends AbstractDirector<Candidato> {

    @Override
    Candidato constructFromResultSet(ResultSet resultSet, IBuilder<Candidato> builder) {

        try {
            builder.reset()
            return builder
                    .setFirstName(resultSet.getString("first_name"))
                    .setCEP(resultSet.getString("cep"))
                    .setEmail(resultSet.getString("email"))
                    .setCity(resultSet.getString("city"))
                    .setCPF(resultSet.getString("cpf"))
                    .setId(resultSet.getLong("id"))
                    .setLastName(resultSet.getString("last_name"))
                    .setDescription(resultSet.getString("description"))
                    .build()

        } catch (SQLException e) {
            throw new RuntimeException(e)
        }
    }

    @Override
    Candidato constructFromResultSetWithCompetences(ResultSet resultSet, IBuilder<Candidato> builder) {
        try {
            builder.reset()
            return builder
                    .setFirstName(resultSet.getString('first_name'))
                    .setLastName(resultSet.getString('last_name'))
                    .setCEP(resultSet.getString('cep'))
                    .setCPF(resultSet.getString('cpf'))
                    .setEmail(resultSet.getString('email'))
                    .setId(resultSet.getLong('id'))
                    .setCity(resultSet.getString('city'))
                    .setDescription(resultSet.getString('description'))
                    .setCompetences(this.extractCompetencesFromResultSet(resultSet))
                    .build()

        } catch (SQLException e) {
            throw new RuntimeException(e)
        }

    }


}
