import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientRepository {

    // Ελέγχει αν υπάρχει το username στη βάση δεδομένων
    public boolean isUsernameTaken(String username) {
        String sql = "SELECT 1 FROM Patients WHERE Username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // Επιστρέφει true αν βρεθεί το username
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Προσθήκη νέου ασθενούς στη βάση δεδομένων
    public boolean registerUser(String name, String phone, String address, String email, String username, String password) {
        String sql = "INSERT INTO Patients (Name, Phone, Address, Email, Username, Password) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, address);
            stmt.setString(4, email);
            stmt.setString(5, username);
            stmt.setString(6, password);

            stmt.executeUpdate();
            return true; // Εγγραφή επιτυχής
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Επικύρωση χρήστη για login
    public boolean validateUser(String username, String password) {
        String sql = "SELECT 1 FROM Patients WHERE Username = ? AND Password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // Επιστρέφει true αν βρεθεί το username και ο κωδικός
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}