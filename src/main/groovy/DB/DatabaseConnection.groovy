package DB

import java.sql.Connection

interface DatabaseConnection {

    Connection getConnection();

}