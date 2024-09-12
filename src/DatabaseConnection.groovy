import java.sql.Connection
import java.sql.DriverManager

class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres"
    private static final String USER = "mateus"
    private static final String PASSWORD = "gringo"

    private static Connection getConnection(){
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
