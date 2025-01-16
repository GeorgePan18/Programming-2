import java.sql.*;

public class UserDAO {
    // Μέθοδος για να ελέγξεις αν υπάρχει ήδη το username
    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM patients WHERE Username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Αν υπάρχει αποτέλεσμα, το username υπάρχει
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Μέθοδος για την εγγραφή νέου χρήστη
    public boolean registerUser(Patient patient) {
        if (usernameExists(patient.getUsername())) {
            System.out.println("Το username υπάρχει ήδη. Δοκιμάστε άλλο.");
            return false; // Επιστροφή false αν υπάρχει το username
        }
        String sql = "INSERT INTO patients (Name, Phone, Address, Email, Username, Password) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getPhone());
            stmt.setString(3, patient.getAddress());
            stmt.setString(4, patient.getEmail());
            stmt.setString(5, patient.getUsername());
            stmt.setString(6, patient.getPassword());
            stmt.executeUpdate();
            System.out.println("Ο χρήστης εγγράφηκε με επιτυχία.");
            return true; // Επιστροφή true αν ολοκληρωθεί η εγγραφή
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Μέθοδος για αυθεντικοποίηση χρήστη
    public Patient authenticateUser(String username, String password) {
        String sql = "SELECT * FROM patients WHERE Username = ? AND Password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Patient(
                    rs.getInt("PatientID"),
                    rs.getString("Name"),
                    rs.getString("Phone"),
                    rs.getString("Address"),
                    rs.getString("Email"),
                    rs.getString("Username"),
                    rs.getString("Password")
                ); // Επιστροφή του αντικειμένου Patient αν τα στοιχεία είναι σωστά
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Επιστροφή null αν τα στοιχεία είναι λάθος
    }
}