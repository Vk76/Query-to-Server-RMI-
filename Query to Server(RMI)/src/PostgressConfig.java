import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgressConfig {
    private static final String DB_NAME = "testdb";
    private static final String DB_URL = "jdbc:postgresql://localhost/" + DB_NAME;
    private static final String USER = "vishalkriplani";
    private static final String PASSWORD = "";

    private PostgressConfig() {
    }

    private static Connection createConnection() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public static Statement getStatement() throws SQLException, ClassNotFoundException {
        Connection connection = createConnection();
        return connection.createStatement();
    }
}
