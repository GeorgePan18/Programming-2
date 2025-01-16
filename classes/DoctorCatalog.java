import java.util.ArrayList;
import java.util.List;

// class that creates a private catalog with doctors 

public class DoctorCatalog {
    private List<Doctor> doctors;

    public DoctorCatalog() {
        this.doctors = new ArrayList<>();
    }

    // Create add a doctor to the catalog
    public void addDoctor(String ID, String name, String speciality, List<String> availability) {
        Doctor doctor = new Doctor(ID, name, speciality, availability); 
        doctors.add(doctor);
    }

    // overload addDoctor for easy use e.g add a doctor in menu
    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }


    // Retrieve the list of doctors
    public List<Doctor> getDoctors() {
        return new ArrayList<>(doctors); // Returns a copy 
    }

    public void printCatalog() {
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
    }
}
