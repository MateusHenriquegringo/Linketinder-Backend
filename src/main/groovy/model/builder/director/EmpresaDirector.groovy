package model.builder.director

import DTO.Request.EmpresaRequestDTO
import model.Empresa
import model.builder.IBuilder

import java.sql.ResultSet
import java.sql.SQLException

class EmpresaDirector extends AbstractDirector<Empresa, EmpresaRequestDTO>{

    @Override
    Empresa constructFromResultSet(ResultSet resultSet, IBuilder<? extends IBuilder> builder) {
        try {
            return builder
                    .setId(resultSet.getLong("id"))
                    .setEmpresaName(resultSet.getString("empresa_name"))
                    .setDescription(resultSet.getString("description"))
                    .setEmail(resultSet.getString("email"))
                    .setCNPJ(resultSet.getString("cnpj"))
                    .setCEP(resultSet.getString("cep"))
                    .setCountry(resultSet.getString("country"))
                    .build()

        } catch (SQLException e){
            throw new RuntimeException("Erro ao busca empresas "+  e.getMessage())
        }
    }



    @Override
    Empresa constructFromRequestDTO(EmpresaRequestDTO dto, IBuilder<? extends IBuilder> builder) {
        return buider
                .setEmpresaName(dto.empresa_name())
                .setDescription(dto.description())
                .setEmail(dto.email())
                .setCNPJ(dto.cnpj())
                .setCEP(dto.cep())
                .setCountry(dto.country())
                .setPassword(dto.password())
                .build()
    }

    @Override
    Empresa constructFromResultSetWithCompetences(ResultSet resultSet, IBuilder<? extends IBuilder> builder) {
        return null
    }
}
