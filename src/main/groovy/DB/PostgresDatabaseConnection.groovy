package DB

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class PostgresDatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres"
    private static final String DRIVER = 'org.postgresql.Driver'

    static Connection getConnection(){
        Properties props = new Properties()

        props.setProperty("user", "mateus")
        props.setProperty("password", "gringo")

        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, props)
        } catch (ClassCastException | SQLException e) {
            throw new RuntimeException("erro ao conectar "+ e)
        }
    }

}
