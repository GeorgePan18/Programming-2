public class SignUp {

    private UserDAO userDAO;

    public SignUp(UserDAO userDAO) {
        this.userDAO = userDAO;
}

    public void signUp(String name, String phone, String address, String email, String username, String password) {
        Patient patient = new Patient(0, name, phone, address, email, username, password);
        boolean success = userDAO.registerUser(patient);
        if (success) {
            System.out.println("Η εγγραφή ολοκληρώθηκε με επιτυχία!");
        } else {
            System.out.println("Η εγγραφή απέτυχε. Το username είναι ήδη κατειλημμένο.");
        }
    }
}
