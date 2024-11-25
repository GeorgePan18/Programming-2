//import jakarta.xml.bind.annotation.XmlElement;
//import jakarta.xml.bind.annotation.XmlRootElement;
//import jakarta.xml.bind.annotation.XmlType;
import java.time.LocalDateTime;

//@XmlRootElement(name = "appointment")
//@XmlType(propOrder = {"appointmentId", "patientName", "doctorName", "startTime", "durationInMinutes", "status", "notes"})


public class Appointment {
    private String appointmentId; 
    private String patientName; 
    private String doctorName; 
    private LocalDateTime startTime; //Ώρα Έναρξης
    private int durationInMinutes; 
    private String status; // (π.χ active, canceled, completed)
    private String notes; 

    public Appointment(String appointmentId, String patientName, String doctorName,
            LocalDateTime startTime, int durationInMinutes, String status, String notes) {
        this.appointmentId = appointmentId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.startTime = startTime;
        this.durationInMinutes = durationInMinutes;
        this.status = status;
        this.notes = notes;
    }
    //@XmlElement
    public String getAppointmentId() {
        return appointmentId;
    }
    
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }
    //@XmlElement
    public String getPatientName() {
        return patientName;
    }
    
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    //@XmlElement
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    //@XmlElement
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    //@XmlElement
    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
   // @XmlElement
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
   // @XmlElement
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

   // @XmlElement
    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(durationInMinutes);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId='" + appointmentId + 
                ", patientName='" + patientName + 
                ", doctorName='" + doctorName + 
                ", startTime=" + startTime +
                ", durationInMinutes=" + durationInMinutes +
                ", status='" + status +
                ", notes='" + notes + 
                '}';
    }
}
