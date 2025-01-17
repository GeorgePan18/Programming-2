import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthentication {

    public boolean authenticate(String username, String password) {
        // Σύνδεση στη βάση δεδομένων
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Ερώτημα για να βρεις τον χρήστη με το συγκεκριμένο username
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Αν δεν βρεθεί ο χρήστης ή τα στοιχεία δεν ταιριάζουν
    }
}
