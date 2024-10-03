package model.builder;

import enums.CompetenciaENUM;
import model.Empresa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmpresaBuilder implements IBuilder<Empresa>{

    private static Empresa createModel(ResultSet resultSet) throws SQLException {
        return new Empresa(
                resultSet.getLong("id"),
                resultSet.getString("empresa_name"),
                resultSet.getString("description"),
                resultSet.getString("email"),
                resultSet.getString("cnpj"),
                resultSet.getString("cep"),
                resultSet.getString("country")
                );
    }

    @Override
    public Empresa buildModelFromResultSet(ResultSet resultSet) throws SQLException {
        return createModel(resultSet);
    }
}
