package DB

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class H2DatabaseConnection {

    private static final String URL = "jdbc:h2:mem:test"

    static Connection getConnection(){

        try {
            return DriverManager.getConnection(URL)
        } catch (ClassCastException | SQLException e) {
            throw new RuntimeException("erro ao conectar "+ e)
        }
    }
}
