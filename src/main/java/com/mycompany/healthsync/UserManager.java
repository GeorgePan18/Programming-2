package com.mycompany.healthsync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
// Δήλωση της κλάσης `UserManager`, η οποία περιέχει μεθόδους για τη διαχείριση χρηστών στη βάση δεδομένων.

    public static boolean registerUser(String username, String password) {
    // Μέθοδος εγγραφής νέου χρήστη στη βάση δεδομένων.

        String query = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection();

             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    public static boolean authenticateUser(String username, String password) {
    // Μέθοδος που ελέγχει αν ένας χρήστης υπάρχει στη βάση δεδομένων με τα δεδομένα που δόθηκαν.

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseManager.getConnection();

            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {

                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    public static int getUserId(String username) throws SQLException {
    // Μέθοδος που επιστρέφει το μοναδικό ID ενός χρήστη βάσει του ονόματός του.

        String query = "SELECT id FROM users WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection();

             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt("id");
                }

                throw new SQLException("User not found.");
            }
        }
    }
}
