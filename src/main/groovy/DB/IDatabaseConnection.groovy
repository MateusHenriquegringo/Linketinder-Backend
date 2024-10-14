package DB

import java.sql.Connection

interface IDatabaseConnection {

    Connection getConnection();

}