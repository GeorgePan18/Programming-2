import java.util.Scanner;

public class MainMenu {
    private final UserRepository userRepo;

    public MainMenu(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the Clinic Scheduling System");
            System.out.println("1. Login");
            System.out.println("2. Sign-Up");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    signUp(scanner);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void login(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (userRepo.validateUser(username, password)) {
            System.out.println("Login successful! Redirecting to scheduling...");
            // Call scheduling functions
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private void signUp(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (userRepo.registerUser(name, phone, address, email, username, password)) {
            System.out.println("Registration successful! You can now log in.");
        } else {
            System.out.println("Registration failed. Try again.");
        }
    }
}
