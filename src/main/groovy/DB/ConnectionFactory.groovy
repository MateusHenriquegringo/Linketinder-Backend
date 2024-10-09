package DB

import java.sql.Connection

abstract class ConnectionFactory {

    private ConnectionFactory(){}

    static Connection getConnection(DBTypes type) {
        switch (type){
            case DBTypes.POSTGRES :
                return PostgresDatabaseConnection.getINSTANCE().getConnection();
            case DBTypes.H2DATABASE :
                return H2DatabaseConnection.getINSTANCE().getConnection();
            default:
                throw new IllegalArgumentException("tipo de banco de dados nao suportado")
        }
    }
}
