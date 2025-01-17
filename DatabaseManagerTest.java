package com.mycompany.healthsync;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManagerTest {

    // Τεστ για τη μέθοδο getConnection της κλάσης DatabaseManager
    @Test
    public void testGetConnection_Success() {
        try {
            // Προσπάθεια απόκτησης σύνδεσης
            Connection conn = DatabaseManager.getConnection();
            assertNotNull(conn, "Connection should not be null.");
            
            // Εκτέλεση απλής εντολής για να χρησιμοποιηθεί η σύνδεση
            assertTrue(conn.isValid(2), "Connection should be valid.");
            
            // Χρησιμοποιούμε τη σύνδεση για να κάνουμε μια απλή ερώτηση
            conn.createStatement().executeQuery("SELECT 1");
            
            conn.close();  // Κλείσιμο σύνδεσης μετά την επιτυχή απόκτηση
        } catch (SQLException e) {
            fail("Connection should be established successfully.");
        }
    }

    // Τεστ για τη μέθοδο getConnection όταν η βάση δεδομένων δεν είναι προσβάσιμη
    @Test
    public void testGetConnection_Failure() {
        // Εδώ θα μπορούσαμε να δημιουργήσουμε ένα σενάριο αποτυχίας, π.χ., να παραποιήσουμε τη διεύθυνση της βάσης δεδομένων
        // για να προκαλέσουμε αποτυχία στη σύνδεση. Εδώ το αφήνουμε ως placeholder για το μέλλον.
        
        try {
            // Αντικαταστήστε με εσφαλμένα δεδομένα για αποτυχία σύνδεσης
            Connection conn = DatabaseManager.getConnection();  
            fail("Connection should have failed.");
        } catch (SQLException e) {
            assertTrue(e.getMessage().contains("Communications link failure"), "Expected connection failure message.");
        }
    }
}
