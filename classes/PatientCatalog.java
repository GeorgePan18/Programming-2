import java.util.ArrayList;
import java.util.List;
public class PatientCatalog {
    private List<Patient> patients;
    public PatientCatalog() {
        this.patients = new ArrayList<>();
    }
    public void addPatient(String FirstName, String LastName, int ID, String PhoneNumber, List<String> Preferences) {
        Patient patient = new Patient(FirstName, LastName, ID, PhoneNumber, Preferences);
        patients.add(patient);
    }
    public void addPatient(Patient patient) {
        patients.add(patient);
    }
    public List<Patient> getPatients() {
        return new ArrayList<>(patients);
    }
    public void printCatalog() {
        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }    
}
