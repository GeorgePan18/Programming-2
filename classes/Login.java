public class Login {

    private UserDAO userDAO;

    public Login(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void login(String username, String password) {
        Patient patient = userDAO.authenticateUser(username, password);
        if (patient != null) {
            System.out.println("Σύνδεση επιτυχής! Καλώς ήρθες, " + patient.getName());
        } else {
            System.out.println("Λάθος όνομα χρήστη ή κωδικός πρόσβασης.");
        }
    }
}

