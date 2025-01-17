package com.mycompany.healthsync;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AppointmentManager {
// Δημιουργία της κλάσης `AppointmentManager`, η οποία περιέχει μεθόδους για τη διαχείριση ραντεβού.

    public static void addAppointment(String specialty, String date, String time, int userId) throws SQLException {
    // Δημιουργεί ραντεβού για τον χρήστη, ελέγχοντας τη διαθεσιμότητα και τους κανόνες.

        try (Connection conn = DatabaseManager.getConnection()) {

            if (isWeekend(date)) {
            // Αποφυγή Σαββατοκύριακου.

                JOptionPane.showMessageDialog(null, "Η ημερομηνία που επιλέξατε είναι Σαββατοκύριακο. Παρακαλώ επιλέξτε καθημερινή.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                suggestAlternativeTimesFromDB(conn, specialty, date, time, userId);
                return;
                // Εμφανίζει μήνυμα και προτείνει εναλλακτικές ώρες.
            }

            if (!isValidTimeFormat(time) || !isValidTimeRange(time)) {
            // Ελέγχει αν η ώρα είναι σε σωστή μορφή και εντός λειτουργικού ωραρίου.

                JOptionPane.showMessageDialog(null, "Η κλινική δεν λειτουργεί την ώρα που επιλέξατε. Η ώρες λειτουργίας είναι 09:00 με 17:00.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                suggestAlternativeTimesFromDB(conn, specialty, date, time, userId);
                return;
            }

            if (isTimeInPast(date, time)) {
            // Εγκυρότητα ώρας και ημερομηνίας.

                JOptionPane.showMessageDialog(null, "Η ώρα που δώσατε είναι στο παρελθόν.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                suggestAlternativeTimesFromDB(conn, specialty, date, time, userId);
                return;
            }

            if (!isAppointmentSlotAvailable(conn, date, time, specialty)) {
            // Ελέγχει αν το slot είναι ήδη κλεισμένο.

                JOptionPane.showMessageDialog(null, "Η ώρα " + time + " της ημέρας " + date + " είναι ήδη κλεισμένη για την ειδικότητα " + specialty + ".", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                suggestAlternativeTimesFromDB(conn, specialty, date, time, userId);
                return;
            }

            int leastBusyDoctorId = getLeastBusyDoctorFromDB(conn, specialty, date, time);
            // Εντοπίζει τον λιγότερο απασχολημένο γιατρό.

            if (leastBusyDoctorId != -1 && isDoctorAvailable(conn, leastBusyDoctorId, date, time)) {
            // Ελέγχει τη διαθεσιμότητα του γιατρού.

                String appointmentQuery = """
                        INSERT INTO appointments (user_id, doctor_id, date, time)
                        VALUES (?, ?, ?, ?)
                        """;
                // Ερώτημα για την προσθήκη ραντεβού.

                try (PreparedStatement stmt = conn.prepareStatement(appointmentQuery)) {

                    stmt.setInt(1, userId);
                    stmt.setInt(2, leastBusyDoctorId);
                    stmt.setString(3, date);
                    stmt.setString(4, time);
                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Το ραντεβού προγραμματίστηκε με τον γιατρό με ID: " + leastBusyDoctorId + "!", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                suggestAlternativeTimesFromDB(conn, specialty, date, time, userId);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Σφάλμα κατά τον προγραμματισμό του ραντεβού: " + e.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private static boolean isWeekend(String date) {
    // Αποφυγή Σαββατοκύριακου.

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private static boolean isValidTimeFormat(String time) {
    // Έλεγχος ώρας σε σωστή μορφή (HH:MM).

        return time.matches("\\d{2}:\\d{2}");
    }

    private static boolean isValidTimeRange(String time) {
    // Ελέγχει αν η ώρα είναι εντός ωραρίου (09:00–17:00).

        try {
            int hour = Integer.parseInt(time.split(":")[0]);
            return hour >= 9 && hour < 17;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isTimeInPast(String date, String time) {
    // Ελέγχει αν η ημερομηνία και η ώρα είναι στο παρελθόν.

        LocalDate appointmentDate = LocalDate.parse(date);
        LocalTime appointmentTime = LocalTime.parse(time);
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        return appointmentDate.isBefore(today) || (appointmentDate.isEqual(today) && appointmentTime.isBefore(now));
    }

    private static int getLeastBusyDoctorFromDB(Connection conn, String specialty, String date, String time) throws SQLException {
    // Βρίσκει τον λιγότερο απασχολημένο γιατρό για μια ειδικότητα.

        String query = """
                SELECT d.id AS doctor_id, COUNT(a.id) AS appointment_count
                FROM doctors d
                LEFT JOIN appointments a ON d.id = a.doctor_id AND a.date = ? AND a.time = ?
                WHERE d.specialty = ?
                GROUP BY d.id
                ORDER BY appointment_count ASC
                LIMIT 1
                """;
        // Το ερώτημα κάνει τα εξής:
        // 1. Επιλέγει το ID του γιατρού (`d.id`) και τον αριθμό των ραντεβού του (`COUNT(a.id)`).
        // 2. Κάνει LEFT JOIN μεταξύ του πίνακα `doctors` και `appointments`:
        //    - Συνδέει τον πίνακα `appointments` με τον `doctors` μέσω του `doctor_id`.
        //    - Φιλτράρει τα ραντεβού μόνο για τη συγκεκριμένη ημερομηνία (`?`) και ώρα (`?`).
        // 3. Φιλτράρει τους γιατρούς για τη συγκεκριμένη ειδικότητα (`d.specialty = ?`).
        // 4. Ομαδοποιεί τα αποτελέσματα ανά γιατρό (`GROUP BY d.id`).
        // 5. Ταξινομεί τους γιατρούς με βάση τον αριθμό των ραντεβού που έχουν (`ORDER BY appointment_count ASC`).
        //    - Οι γιατροί με τα λιγότερα ραντεβού εμφανίζονται πρώτοι.
        // 6. Περιορίζει το αποτέλεσμα στον πρώτο γιατρό (`LIMIT 1`).

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, date);
            stmt.setString(2, time);
            stmt.setString(3, specialty);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("doctor_id");
                }
            }
        }
        return -1;
        // Επιστρέφει άκυρη τιμή αν δεν υπάρχει διαθέσιμος γιατρός.
    }

    private static boolean isDoctorAvailable(Connection conn, int doctorId, String date, String time) throws SQLException {
    // Ελέγχει αν ένας γιατρός είναι διαθέσιμος για συγκεκριμένη ημερομηνία και ώρα.

        String query = """
                SELECT COUNT(*) AS appointment_count
                FROM appointments
                WHERE doctor_id = ? AND date = ? AND time = ?
                """;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.setString(2, date);
            stmt.setString(3, time);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("appointment_count") == 0;
                }
            }
        }
        return false;
    }
    
    private static void suggestAlternativeTimesFromDB(Connection conn, String specialty, String date, String time, int userId) throws SQLException {
    // Προτείνει εναλλακτικές ώρες για ραντεβού, αν η επιλεγμένη ώρα δεν είναι διαθέσιμη.

        LocalDate currentDate = LocalDate.parse(date);
        
        while (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                currentDate = currentDate.plusDays(1);
        }
        // Αν ξεκινάει από Σαββατοκυριακό, να προσθέτει μέρα.

        while (true) {
        // Loop που συνεχίζει να προτείνει ώρες μέχρι να βρεθεί διαθέσιμη ημέρα και ώρα.

            String query = """
            WITH time_slots AS (
                SELECT TIME '09:00' + INTERVAL (n) HOUR AS available_time
                FROM (
                    SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL 
                    SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7
                ) AS numbers
            ),
            appointments_count AS (
                SELECT
                    a.date,
                    a.time,
                    d.specialty,
                    COUNT(a.doctor_id) AS doctors_booked
                FROM appointments a
                JOIN doctors d ON a.doctor_id = d.id
                WHERE a.date = ? AND d.specialty = ?
                GROUP BY a.date, a.time, d.specialty
            )
            SELECT
                ts.available_time
            FROM time_slots ts
            LEFT JOIN appointments_count ac
                ON ts.available_time = ac.time
            WHERE (ac.doctors_booked IS NULL OR ac.doctors_booked < 3)
            ORDER BY ts.available_time;
            """;
            // Ερώτημα που βρίσκει διαθέσιμες ώρες για μια συγκεκριμένη ημέρα και ειδικότητα.
            // Το ερώτημα:
            // - Δημιουργεί όλες τις δυνατές ώρες από τις 09:00 έως τις 17:00.
            // - Ελέγχει πόσοι γιατροί είναι κλεισμένοι για την επιλεγμένη ειδικότητα σε κάθε ώρα.
            // - Επιστρέφει ώρες που έχουν λιγότερες από 3 κρατήσεις.

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, currentDate.toString());
                stmt.setString(2, specialty);

                try (ResultSet rs = stmt.executeQuery()) {
                    List<String> availableTimes = new ArrayList<>();

                    LocalTime now = LocalTime.now(); 

                    boolean isToday = currentDate.equals(LocalDate.now());

                    while (rs.next()) {
                        LocalTime availableTime = rs.getTime("available_time").toLocalTime();

                        if (!isToday || availableTime.isAfter(now)) {
                        // Αν η ημερομηνία είναι σήμερα, φιλτράρει ώρες που έχουν περάσει.

                            String formattedTime = availableTime.toString();
                            availableTimes.add(formattedTime);
                        }
                    }

                    if (!availableTimes.isEmpty()) {
                    // Αν υπάρχουν διαθέσιμες ώρες, τις εμφανίζει σε μήνυμα.

                        Object[] options = {"Κλείσιμο Παραθύρου", "Επόμενη Ημέρα", "Επιλογή Ώρας"};

                        StringBuilder message = new StringBuilder("Διαθέσιμες ώρες για την " + currentDate + ":\n");
                        for (int i = 0; i < availableTimes.size(); i++) {
                            message.append((i + 1)).append(". ").append(availableTimes.get(i)).append("\n");
                        }

                        int selection = JOptionPane.showOptionDialog(
                                null,
                                message.toString(),
                                "Διαθέσιμες Ώρες",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                options,
                                options[2]
                        );

                        if (selection == 0) { 
                        // Επιλογή: Κλείσιμο Παραθύρου.
                            return;
                        } else if (selection == 1) { 
                        // Επιλογή: Επόμενη Ημέρα.
                            do {
                                currentDate = currentDate.plusDays(1);
                            } while (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY);
                            // Προχωρά στην επόμενη εργάσιμη ημέρα.
                            continue;
                        } else if (selection == 2) { 
                        // Επιλογή: Επιλογή Ώρας.

                            String selectedTime = (String) JOptionPane.showInputDialog(
                                    null,
                                    "Επιλέξτε μία ώρα:",
                                    "Επιλογή Ώρας",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    availableTimes.toArray(),
                                    availableTimes.get(0)
                            );

                            if (selectedTime != null) {
                            // Δημιοργία ραντεβού.

                                addAppointment(specialty, currentDate.toString(), selectedTime, userId);
                                return;
                            }
                        }
                    }
                }
            }

            do {
                currentDate = currentDate.plusDays(1);
            } while (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY);
            // Αν δεν υπάρχουν διαθέσιμες ώρες, προχωρά στην επόμενη εργάσιμη ημέρα.
        }
    }

    public static List<String> getAppointments(int userId) throws SQLException {
    // Παίρνει τη λίστα των ραντεβού ενός χρήστη.

        List<String> appointments = new ArrayList<>();
        String query = """
                SELECT a.id, a.date, a.time, d.name AS doctor, d.specialty
                FROM appointments a
                JOIN doctors d ON a.doctor_id = d.id
                WHERE a.user_id = ?
                ORDER BY a.date, a.time
                """;
        // Ερώτημα που επιστρέφει τα ραντεβού ενός χρήστη, ταξινομημένα κατά ημερομηνία και ώρα.
        // Ερώτημα που:
        // 1. Επιλέγει:
        //    - Το ID του ραντεβού (`a.id`).
        //    - Την ημερομηνία (`a.date`) και την ώρα (`a.time`) του ραντεβού.
        //    - Το όνομα του γιατρού (`d.name`) και την ειδικότητά του (`d.specialty`).
        // 2. Συνδέει τον πίνακα `appointments` με τον πίνακα `doctors` μέσω του `doctor_id`.
        // 3. Φιλτράρει τα ραντεβού για τον συγκεκριμένο χρήστη (`a.user_id = ?`).
        // 4. Ταξινομεί τα ραντεβού κατά ημερομηνία και ώρα (`ORDER BY a.date, a.time`).

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    appointments.add(String.format("ID: %d, Ημερομηνία: %s, Ώρα: %s, Γιατρός: %s, Ειδικότητα: %s",
                            rs.getInt("id"), rs.getString("date"), rs.getString("time"),
                            rs.getString("doctor"), rs.getString("specialty")));
                    // Δημιουργεί μια συμβολοσειρά για κάθε ραντεβού και την προσθέτει στη λίστα.
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Σφάλμα κατά την ανάκτηση των ραντεβού: " + e.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return appointments;
    }

    private static boolean isAppointmentSlotAvailable(Connection conn, String date, String time, String specialty) throws SQLException {
    // Ελέγχει αν υπάρχει διαθέσιμο ραντεβού για συγκεκριμένη ημερομηνία, ώρα και ειδικότητα.

        String query = """
                SELECT COUNT(*) AS appointment_count
                FROM appointments a
                JOIN doctors d ON a.doctor_id = d.id
                WHERE a.date = ? AND a.time = ? AND d.specialty = ?
                """;
        // Ερώτημα που υπολογίζει τον αριθμό των ραντεβού για την επιλεγμένη ημερομηνία, ώρα και ειδικότητα.

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, date);
            stmt.setString(2, time);
            stmt.setString(3, specialty);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int appointmentCount = rs.getInt("appointment_count");
                    // Παίρνει τον αριθμό των υπάρχοντων ραντεβού για την ειδικότητα.

                    return appointmentCount < 3;
                }
            }
        }
        return false;
    }


    public static void deleteAppointment(int appointmentId) {

        String query = "DELETE FROM appointments WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, appointmentId);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Το ραντεβού διαγράφηκε με επιτυχία.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Αποτυχία κατά τη διαγραφή του ραντεβού: " + e.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
