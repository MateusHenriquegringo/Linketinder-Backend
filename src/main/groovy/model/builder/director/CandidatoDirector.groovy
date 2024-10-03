package model.builder.director

import DTO.Request.CandidatoRequestDTO
import model.Candidato
import model.builder.IBuilder

import java.sql.ResultSet
import java.sql.SQLException

class CandidatoDirector extends AbstractDirector<Candidato, CandidatoRequestDTO> {

    @Override
    Candidato constructFromResultSet(ResultSet resultSet, IBuilder<? extends IBuilder> builder) {

        try {
            return builder
                    .setFirstName(resultSet.getString('first_name'))
                    .setLastName(resultSet.getString('last_name'))
                    .setCEP(resultSet.getString('cep'))
                    .setCPF(resultSet.getString('cpf'))
                    .setId(resultSet.getLong('id'))
                    .setDescription(resultSet.getString('description'))
                    .build()

        } catch (SQLException e) {
            throw new RuntimeException(e)
        }
    }

    @Override
    Candidato constructFromResultSetWithCompetences(ResultSet resultSet, IBuilder<? extends IBuilder> builder) {
        try {
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

    @Override
    Candidato constructFromRequestDTO(CandidatoRequestDTO dto, IBuilder<? extends IBuilder> builder) {

        return builder
                .setFirstName(dto.first_name())
                .setLastName(dto.last_name())
                .setCity(dto.city())
                .setCompetences(dto.competences())
                .setEmail(dto.email())
                .setDescription(dto.email())
                .setPassword(dto.password())
                .setCPF(dto.CPF())
                .setCEP(dto.CEP())
                .build()

    }


}
