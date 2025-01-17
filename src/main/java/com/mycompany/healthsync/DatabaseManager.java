package com.mycompany.healthsync;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
// Δημιουργεί την κλάση `DatabaseManager` που περιέχει μεθόδους για τη διαχείριση της βάσης δεδομένων.

    private static final String URL = "jdbc:mysql://localhost:3306/HealthSyncDB";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Nick2004";

    public static Connection getConnection() throws SQLException {
  
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void setupDatabase() {

        try (Connection conn = getConnection()) {

            conn.createStatement().execute("""
            CREATE TABLE IF NOT EXISTS users (
                id INT AUTO_INCREMENT PRIMARY KEY,
                username VARCHAR(50) UNIQUE NOT NULL,
                password VARCHAR(50) NOT NULL
            )
        """);
        // Δημιουργία πίνακα `users`.

            conn.createStatement().execute("""
            CREATE TABLE IF NOT EXISTS doctors (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(50) NOT NULL,
                specialty VARCHAR(50) NOT NULL
            )
        """);
        // Δημιουργία πίνακα `doctors`.

            conn.createStatement().execute("""
            CREATE TABLE IF NOT EXISTS appointments (
                id INT AUTO_INCREMENT PRIMARY KEY,
                user_id INT NOT NULL,
                doctor_id INT NOT NULL,
                date DATE NOT NULL,
                time TIME NOT NULL,
                FOREIGN KEY (user_id) REFERENCES users(id),
                FOREIGN KEY (doctor_id) REFERENCES doctors(id)
            )
        """);
        // Δημιουργία πίνακα `appointments`. 

            DoctorManager.initializeDoctors();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
