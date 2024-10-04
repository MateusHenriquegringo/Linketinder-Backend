package dao

import DB.ConnectionFactory
import DB.DBTypes
import repository.EmpresaDAO
import DB.H2DatabaseConnection
import model.Empresa
import spock.lang.Shared
import spock.lang.Specification

import java.sql.Connection

class EmpresaDAOTest extends Specification {

    private Connection connection = ConnectionFactory.getConnection(DBTypes.H2DATABASE)

    @Shared
    Empresa empresa

    @Shared
    EmpresaDAO dao

    def setup() {
        def statement = connection.createStatement()
        statement.execute(
                """CREATE TABLE empresa (
                        id SERIAL PRIMARY KEY,
                                empresa_name VARCHAR(100) NOT NULL,
                                description TEXT,
                        email VARCHAR(100) NOT NULL,
                                cnpj VARCHAR(18) NOT NULL,
                                cep VARCHAR(9) NOT NULL,
                                country VARCHAR(50) NOT NULL,
                                password VARCHAR(100) NOT NULL )"""
        );

        dao = new EmpresaDAO(connection)
    }

    def cleanup() {
        def statement = connection.createStatement()
        statement.execute("DROP TABLE empresa")
        statement.close()
        connection.close()
    }

}
