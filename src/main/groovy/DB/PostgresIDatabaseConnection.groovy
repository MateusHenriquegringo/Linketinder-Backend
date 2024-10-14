package DB

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class PostgresIDatabaseConnection implements IDatabaseConnection{

    private static PostgresIDatabaseConnection INSTANCE

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres"
    private static final String DRIVER = 'org.postgresql.Driver'

    static Properties properties = new Properties();

    static {
        properties.setProperty("user", "mateus");
        properties.setProperty("password", "gringo");
        properties.setProperty("currentSchema", "flyway")
    }

    static void setConnectionProperties(String key, String value) {
        properties.setProperty(key, value);
    }

    private PostgresIDatabaseConnection(){

    }

    @Override
    Connection getConnection(){
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, properties)
        } catch (SQLException e) {
            throw new RuntimeException("erro ao conectar "+ e)
        }
    }

    static PostgresIDatabaseConnection getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new PostgresIDatabaseConnection();
        }
        return INSTANCE;
    }

}
