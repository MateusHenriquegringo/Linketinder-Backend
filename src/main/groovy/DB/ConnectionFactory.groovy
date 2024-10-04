package DB

import java.sql.Connection

abstract class ConnectionFactory {

    private ConnectionFactory(){}

    static Connection getConnection(DBTypes type) {
        switch (type){
            case DBTypes.POSTGRES :
                return new PostgresDatabaseConnection().getConnection();
            case DBTypes.H2DATABASE :
                return new H2DatabaseConnection().getConnection();
            default:
                throw new IllegalArgumentException("tipo de banco de dados nao suportado")
        }
    }
}
