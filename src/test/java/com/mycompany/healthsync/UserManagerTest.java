package com.mycompany.healthsync;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

public class UserManagerTest {

    @Test
    public void testRegisterUser_Success() {
        // Δοκιμάζουμε να εγγράψουμε έναν νέο χρήστη
        String username = "testUser";
        String password = "testPassword";

        boolean result = UserManager.registerUser(username, password);

        // Ελέγχουμε ότι η εγγραφή ολοκληρώθηκε επιτυχώς
        assertTrue(result);
    }

    @Test
    public void testRegisterUser_Failure() {
        // Δοκιμάζουμε να εγγράψουμε έναν χρήστη με υπάρχον όνομα
        String username = "existingUser";
        String password = "password";

        // Υποθέτουμε ότι αυτός ο χρήστης ήδη υπάρχει, οπότε η εγγραφή θα αποτύχει
        boolean result = UserManager.registerUser(username, password);

        assertFalse(result);
    }

    @Test
    public void testAuthenticateUser_Success() {
        // Δοκιμάζουμε να συνδεθούμε με έναν έγκυρο χρήστη
        String username = "validUser";
        String password = "validPassword";

        boolean result = UserManager.authenticateUser(username, password);

        assertTrue(result);
    }

    @Test
    public void testAuthenticateUser_Failure() {
        // Δοκιμάζουμε να συνδεθούμε με έναν χρήστη με λανθασμένα δεδομένα
        String username = "invalidUser";
        String password = "invalidPassword";

        boolean result = UserManager.authenticateUser(username, password);

        assertFalse(result);
    }

    @Test
    public void testGetUserId_Success() throws SQLException {
        // Δοκιμάζουμε να πάρουμε το ID ενός χρήστη
        String username = "existingUser";

        int userId = UserManager.getUserId(username);

        assertNotNull(userId); // Ελέγχουμε ότι το ID δεν είναι null
    }
    

    @Test
    public void testGetUserId_Failure() {
        // Δοκιμάζουμε να πάρουμε το ID ενός ανύπαρκτου χρήστη
        String username = "nonExistentUser";

        assertThrows(SQLException.class, () -> {
            UserManager.getUserId(username);
        });
    }
}
