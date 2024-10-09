package DB

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class H2DatabaseConnection  implements DatabaseConnection{

    private static H2DatabaseConnection INSTANCE;

    private static final String URL = "jdbc:h2:mem:test;MODE=PostgreSQL"

    private H2DatabaseConnection(){
    }

    Connection getConnection() {
        try {
            return DriverManager.getConnection(URL)
        } catch (SQLException e) {
            throw new RuntimeException("erro ao conectar " + e)
        }
    }

    static getINSTANCE() {
        if(INSTANCE == null){
            INSTANCE = new H2DatabaseConnection()
        }
        return INSTANCE
    }
}
