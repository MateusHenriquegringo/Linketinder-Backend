package model.builder.director

import DTO.Request.VagaRequestDTO
import model.Vaga
import model.builder.IBuilder

import java.sql.ResultSet
import java.sql.SQLException

class VagaDirector extends AbstractDirector<Vaga, VagaRequestDTO> {

    @Override
    Vaga constructFromResultSet(ResultSet resultSet, IBuilder<? extends IBuilder> builder) {

        try {
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
    Vaga constructFromResultSetWithCompetences(ResultSet resultSet, IBuilder<? extends IBuilder> builder) {

        try {
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

    @Override
    Vaga constructFromRequestDTO(VagaRequestDTO dto, IBuilder<? extends IBuilder> builder) {
        return builder
                .setVagaName(dto.vaga_name())
                .setDescription(dto.description())
                .setCity(dto.city())
                .setState(dto.state())
                .setEmpresaID(dto.empresa_id())
                .setCompetences(dto.competences())
                .build()
    }
}
