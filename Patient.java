import java.util.List;

public class Patient {
     
    private String Firstname;
    private String Lastname;
    private int ID;
    private String Phonenumber;
    private List<String> Preferences;

    public Patient(String Firstname, String Lastname, int ID, String Phonenumber, List<String> Preferences){
        this.Firstname = Firstname;
        this.Lastname = Lastname;
        this.ID = ID;
        this.Phonenumber = Phonenumber;
        this.Preferences = Preferences;
    }

    public void setFirstname(String Firstname){
        this.Firstname = Firstname;
    }

    public String getFirstname(){
        return Firstname;
    }
    
    public void setLastname(String Lastname){
        this.Lastname = Lastname;
    }

    public String getLastname(){
        return Lastname;
    }
    
    public void setID(int ID){
        this.ID = ID;
    }

    public int getID(){
        return ID;
    }
     
    public void setPhonenumber(String Phonenumber){
        this.Phonenumber = Phonenumber;
    }

    public String getPhonenumber(){
        return Phonenumber;
    }

    public void setPreferences(List<String> Preferences){
        this.Preferences = Preferences;
    }

    public List<String> getPreferences(){
        return Preferences;
    }

    @Override
    public String toString(){
        return "Patient" +
               "firstName='" + Firstname +
               ", lastName='" + Lastname + 
               ", idNumber='" + ID + 
               ", phoneNumber='" + Phonenumber +
               ", preferences='" + Preferences +
               " ";
    }
}