import groovy.sql.Sql
import groovy.transform.CompileStatic

@CompileStatic
class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres"
    private static final String USER = "mateus"
    private static final String PASSWORD = "gringo"
    private static final String DRIVER = 'org.postgresql.Driver'

    private static Sql getConnection(){
        return Sql.newInstance(URL,USER, PASSWORD, DRIVER)
    }
}
