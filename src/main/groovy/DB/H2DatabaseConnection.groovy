package DB

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class H2DatabaseConnection  implements DatabaseConnection{

    private static final String URL = "jdbc:h2:mem:test;MODE=PostgreSQL"

    H2DatabaseConnection(){
    }

    Connection getConnection() {
        try {
            return DriverManager.getConnection(URL)
        } catch (SQLException e) {
            throw new RuntimeException("erro ao conectar " + e)
        }
    }
}
