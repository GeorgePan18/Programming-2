import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class DatabaseConnection {
    // Database URL, username, and password
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/clinicscheduling?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Programmatismos2!";
 
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Establishing the connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection to the database was successful!");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database:");
            e.printStackTrace();
        } finally {
            // Closing the connection
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    System.out.println("Error closing the connection:");
                    e.printStackTrace();
                }
            }
        }
    }
}
