import java.util.List;

public class Doctor {

    private String ID;

    private String name;
    private String specialty;
    private List<String> availability;

    public Doctor(String ID, String name, String specialty, List<String> availability) {
        this.ID = ID;
        this.name = name;
        this.specialty = specialty;
        this.availability = availability;
    }

    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }
    public void  setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    
    public List<String> getAvailability() {
        return availability;
    }
    public void setAvailability(List<String> availability) {
        this.availability = availability;
    }


    public void addAvailability(String slot) {
        this.availability.add(slot);
    }

    public void removeAvailability(String slot) {
        this.availability.remove(slot);
    }


    @Override

    public String toString() {
        return "Doctor{" + 
        "ID=" + ID + 
        ",name=" + name + 
        ",specialty=" + specialty + 
        ",availability=" + availability + 
        "}";
    }

}
