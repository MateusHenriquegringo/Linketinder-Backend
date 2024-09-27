package model.builder

import java.sql.ResultSet

interface Builder<T>{
    T buildModelFromResultSet(ResultSet set);

}