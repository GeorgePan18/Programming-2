public class Patient {
    private int id; // Χρησιμοποιείται μόνο όταν απαιτείται
    private String name;
    private String phone;
    private String address;
    private String email;
    private String username;
    private String password;

    // Constructor για νέα εγγραφή (χωρίς ID)
    public Patient(String name, String phone, String address, String email, String username, String password) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    // Constructor για αντικείμενα που έχουν ID 
    public Patient(int id, String name, String phone, String address, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Getters και Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}