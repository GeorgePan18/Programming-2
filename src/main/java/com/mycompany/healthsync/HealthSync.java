package com.mycompany.healthsync;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class HealthSync extends JFrame {
// Δηλώνει την κλάση `HealthSync`, η οποία επεκτείνει την κλάση `JFrame` για τη δημιουργία παραθύρων εφαρμογής.

    public HealthSync() {
    // Κατασκευαστής της κλάσης `HealthSync`.

        DatabaseManager.setupDatabase();
        // Ρυθμίζει τη βάση δεδομένων δημιουργώντας τους πίνακες.

        loginScreen();
        // Εμφάνιση αρχικής οθόνης σύνδεσης του χρήστη.
    }

    private void loginScreen() {
    // Δημιουργία της οθόνης σύνδεσης χρήστη.

        JFrame loginFrame = new JFrame("Σύνδεση Χρήστη");
        // Δημιουργία νέου παραθύρου με τίτλο "Σύνδεση Χρήστη".

        loginFrame.setSize(400, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Όνομα Χρήστη:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Κωδικός:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Σύνδεση");
        JButton registerButton = new JButton("Εγγραφή");

        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
        loginFrame.add(registerButton);
        loginFrame.setVisible(true);

        loginButton.addActionListener(e -> {
        // Ορίζει τη συμπεριφορά του κουμπιού "Σύνδεση".

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (UserManager.authenticateUser(username, password)) {

                loginFrame.dispose();
                // Κλείνει το παράθυρο σύνδεσης.

                mainMenu(username);
                // Εμφανίζει το κεντρικό μενού για τον χρήστη.
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Λάθος στοιχεία σύνδεσης.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> openRegisterWindow());
    }

    private void openRegisterWindow() {
    // Δημιουργία παραθύρου εγγραφής χρήστη.

        JFrame registerFrame = new JFrame("Εγγραφή Χρήστη");
        registerFrame.setSize(400, 300);
        registerFrame.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Όνομα Χρήστη:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Κωδικός:");
        JPasswordField passwordField = new JPasswordField();
        JLabel confirmPasswordLabel = new JLabel("Επιβεβαίωση Κωδικού:");
        JPasswordField confirmPasswordField = new JPasswordField();

        JButton submitButton = new JButton("Εγγραφή");
        JButton cancelButton = new JButton("Ακύρωση");

        registerFrame.add(usernameLabel);
        registerFrame.add(usernameField);
        registerFrame.add(passwordLabel);
        registerFrame.add(passwordField);
        registerFrame.add(confirmPasswordLabel);
        registerFrame.add(confirmPasswordField);
        registerFrame.add(submitButton);
        registerFrame.add(cancelButton);

        registerFrame.setVisible(true);

        submitButton.addActionListener(e -> {
        // Ορίζει τη συμπεριφορά του κουμπιού "Εγγραφή".

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (password.equals(confirmPassword)) {

                if (UserManager.registerUser(username, password)) {

                    JOptionPane.showMessageDialog(registerFrame, "Εγγραφή ολοκληρώθηκε επιτυχώς!", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                    registerFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(registerFrame, "Η εγγραφή απέτυχε. Δοκιμάστε ξανά.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(registerFrame, "Οι κωδικοί δεν ταιριάζουν.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> registerFrame.dispose());
        // Κλείνει το παράθυρο εγγραφής όταν πατηθεί το κουμπί "Ακύρωση".
    }
    
    private void mainMenu(String username) {
    // Δημιουργία του κεντρικού μενού της εφαρμογής.

        setTitle("HealthSync");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Καλώς ήρθατε, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcomeLabel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton scheduleButton = new JButton("Προγραμματισμός Ραντεβού");
        JButton viewButton = new JButton("Προβολή Ραντεβού");
        JButton exitButton = new JButton("Έξοδος");

        buttonPanel.add(scheduleButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.CENTER);

        scheduleButton.addActionListener(e -> openScheduleWindow(username));
        // Συνδέει το κουμπί "Προγραμματισμός Ραντεβού" με τη μέθοδο `openScheduleWindow`.

        viewButton.addActionListener(e -> viewAppointments(username));
        // Συνδέει το κουμπί "Προβολή Ραντεβού" με τη μέθοδο `viewAppointments`.

        exitButton.addActionListener(e -> System.exit(0));
        // Ορίζει τη συμπεριφορά του κουμπιού "Έξοδος", το οποίο τερματίζει την εφαρμογή.

        setVisible(true);
    }

    private void openScheduleWindow(String username) {
    // Δημιουργία παραθύρου προγραμματισμού ραντεβού.

        JFrame scheduleFrame = new JFrame("Προγραμματισμός Ραντεβού");
        scheduleFrame.setSize(400, 300);
        scheduleFrame.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel specialtyLabel = new JLabel("Ειδικότητα:");
        JComboBox<String> specialtyComboBox = new JComboBox<>(new String[]{"Καρδιολόγος", "Νευρολόγος", "Παιδίατρος", "Ορθοπεδικός", "Γυναικολόγος"});
        JLabel dateLabel = new JLabel("Ημερομηνία:");
        JTextField dateField = new JTextField("2025-01-17");
        JLabel timeLabel = new JLabel("Ώρα:");
        JTextField timeField = new JTextField("10:00");
        JButton submitButton = new JButton("Κλείσε Ραντεβού");
        JButton cancelButton = new JButton("Ακύρωση");

        scheduleFrame.add(specialtyLabel);
        scheduleFrame.add(specialtyComboBox);
        scheduleFrame.add(dateLabel);
        scheduleFrame.add(dateField);
        scheduleFrame.add(timeLabel);
        scheduleFrame.add(timeField);
        scheduleFrame.add(submitButton);
        scheduleFrame.add(cancelButton);

        scheduleFrame.setVisible(true);

        submitButton.addActionListener(e -> {

            String specialty = (String) specialtyComboBox.getSelectedItem();
            String date = dateField.getText();
            String time = timeField.getText();

            if (!time.matches("\\d{2}:\\d{2}")) {
            // Έλεγχος ώρας σε σωστή μορφή (HH:MM).

                JOptionPane.showMessageDialog(scheduleFrame, "Η ώρα πρέπει να είναι σε μορφή HH:MM.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int userId = UserManager.getUserId(username);

                AppointmentManager.addAppointment(specialty, date, time, userId);

                scheduleFrame.dispose();

            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(scheduleFrame, "Σφάλμα κατά την προγραμματισμό του ραντεβού.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        cancelButton.addActionListener(e -> scheduleFrame.dispose());
        // Ορίζει τη συμπεριφορά του κουμπιού "Ακύρωση", το οποίο κλείνει το παράθυρο.
    }

    private void viewAppointments(String username) {
    // Δημιουργία παραθύρου προβολής προγραμματισμένων ραντεβού.

        JFrame viewFrame = new JFrame("Προγραμματισμένα Ραντεβού");
        viewFrame.setSize(600, 400);
        viewFrame.setLayout(new BorderLayout());

        JTextArea appointmentsArea = new JTextArea();
        appointmentsArea.setEditable(false);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> appointmentList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(appointmentList);
        JButton deleteButton = new JButton("Διαγραφή Ραντεβού");


        try {
            int userId = UserManager.getUserId(username);

            List<String> appointments = AppointmentManager.getAppointments(userId);

            appointments.forEach(listModel::addElement);

        } catch (SQLException e) {

            listModel.addElement("Αποτυχία κατά την ανάκτηση των ραντεβού.");
            e.printStackTrace();
        }

        deleteButton.addActionListener(e -> {
        // Ορίζει τη συμπεριφορά του κουμπιού "Διαγραφή Ραντεβού".

            int selectedIndex = appointmentList.getSelectedIndex();

            if (selectedIndex != -1) {
                String selectedAppointment = listModel.get(selectedIndex);
                int appointmentId = Integer.parseInt(selectedAppointment.split(",")[0].split(":")[1].trim());

                AppointmentManager.deleteAppointment(appointmentId);

                listModel.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(viewFrame, "Παρακαλώ επιλέξτε ένα ραντεβού για διαγραφή.", "Προσοχή", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);

        viewFrame.add(scrollPane, BorderLayout.CENTER);
        viewFrame.add(buttonPanel, BorderLayout.SOUTH);

        viewFrame.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(HealthSync::new);
        // Εκκίνηση εφαρμογής.
    }
}
