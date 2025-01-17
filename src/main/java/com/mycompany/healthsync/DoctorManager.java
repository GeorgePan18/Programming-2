package com.mycompany.healthsync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoctorManager {
// Δημιουργεί την κλάση `DoctorManager` που περιέχει μεθόδους για τη διαχείριση των γιατρών.

    public static void initializeDoctors() {
    // Μέθοδος που αρχικοποιεί τα δεδομένα των γιατρών στη βάση δεδομένων.

        String[][] doctors = {
            {"Γιατρός 1", "Καρδιολόγος"},
            {"Γιατρός 2", "Καρδιολόγος"},
            {"Γιατρός 3", "Καρδιολόγος"},
            {"Γιατρός 4", "Νευρολόγος"},
            {"Γιατρός 5", "Νευρολόγος"},
            {"Γιατρός 6", "Νευρολόγος"},
            {"Γιατρός 7", "Παιδίατρος"},
            {"Γιατρός 8", "Παιδίατρος"},
            {"Γιατρός 9", "Παιδίατρος"},
            {"Γιατρός 10", "Ορθοπεδικός"},
            {"Γιατρός 11", "Ορθοπεδικός"},
            {"Γιατρός 12", "Ορθοπεδικός"},
            {"Γιατρός 13", "Γυναικολόγος"},
            {"Γιατρός 14", "Γυναικολόγος"},
            {"Γιατρός 15", "Γυναικολόγος"}
        };

        String query = "INSERT IGNORE INTO doctors (name, specialty) VALUES (?, ?)";
        // Αποφυγή διπλών εγγραφών (αν υπάρχει ήδη, την αγνοεί).

        try (Connection conn = DatabaseManager.getConnection();

             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (String[] doctor : doctors) {
            // Επαναλαμβάνει για κάθε ζεύγος δεδομένων [όνομα, ειδικότητα] στον πίνακα `doctors`.

                stmt.setString(1, doctor[0]);
                stmt.setString(2, doctor[1]);
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
