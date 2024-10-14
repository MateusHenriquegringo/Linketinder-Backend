package DB

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class H2IDatabaseConnection implements IDatabaseConnection{

    private static H2IDatabaseConnection INSTANCE;

    private static final String URL = "jdbc:h2:mem:test;MODE=PostgreSQL"

    private H2IDatabaseConnection(){
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
            INSTANCE = new H2IDatabaseConnection()
        }
        return INSTANCE
    }
}
