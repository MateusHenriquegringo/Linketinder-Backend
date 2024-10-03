package model.builder;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IBuilder<T> {

    T buildModelFromResultSet(ResultSet resultSet) throws SQLException;

}
