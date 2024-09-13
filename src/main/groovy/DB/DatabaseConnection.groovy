package DB

import groovy.sql.Sql

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres"
    private static final String USER = "mateus"
    private static final String PASSWORD = "gringo"
    private static final String DRIVER = 'org.postgresql.Driver'

    static Connection getConnection(){
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASSWORD)
        } catch (ClassCastException | SQLException e) {
            throw new RuntimeException("erro ao conectar")
        }
    }
}
