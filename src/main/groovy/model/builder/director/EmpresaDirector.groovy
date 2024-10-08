package model.builder.director


import model.Empresa
import model.builder.IBuilder

import java.sql.ResultSet
import java.sql.SQLException

class EmpresaDirector extends AbstractDirector<Empresa> {

    @Override
    Empresa constructFromResultSet(ResultSet resultSet, IBuilder<Empresa> builder) {
        try {
            builder.reset()
            return builder
                    .setEmpresaName(resultSet.getString("empresa_name"))
                    .setDescription(resultSet.getString("description"))
                    .setEmail(resultSet.getString("email"))
                    .setCNPJ(resultSet.getString("cnpj"))
                    .setCEP(resultSet.getString("cep"))
                    .setCountry(resultSet.getString("country"))
                    .setId(resultSet.getLong("id"))
                    .build()

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao busca empresas " + e.getMessage())
        }
    }

    @Override
    Empresa constructFromResultSetWithCompetences(ResultSet resultSet, IBuilder<Empresa> builder) {
        return null
    }

}
