import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    public static Connection connect() {
        Connection conn = null;
        try {
            // SQLite connection
            conn = DriverManager.getConnection("jdbc:sqlite:registration.db");

            // Create table if not exists
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "name TEXT," +
                         "mobile TEXT," +
                         "gender TEXT," +
                         "dob TEXT," +
                         "address TEXT);";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Connection Error: " + e.getMessage());
        }
        return conn;
    }
}
