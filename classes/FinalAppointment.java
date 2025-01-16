
//import jakarta.xml.bind.annotation.XmlElement;
//import jakarta.xml.bind.annotation.XmlRootElement;
//import jakarta.xml.bind.annotation.XmlType;
import java.time.LocalDateTime;

//@XmlRootElement(name = "appointment")
//@XmlType(propOrder = {"appointmentId", "patientName", "doctorName", "startTime", "durationInMinutes", "status", "notes"})

public class FinalAppointment {
    private String appointmentId;
    private String patientName;
    private String doctorName;
    private LocalDateTime startTime; // Ώρα Έναρξης
    private int durationInMinutes;
    private String appointmentType;
    private String status; // (π.χ active, canceled, completed)
    private String notes;

    public FinalAppointment(String appointmentId, String patientName, String doctorName,
            LocalDateTime startTime, int durationInMinutes, String status, String notes, String appointmentType) {
        this.appointmentId = appointmentId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.startTime = startTime;
        this.durationInMinutes = durationInMinutes;
        this.status = status;
        this.notes = notes;
        this.appointmentType = appointmentType;
    }

    // @XmlElement
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    // @XmlElement
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    // @XmlElement
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    // @XmlElement
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    // @XmlElement
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

    /*
     * public boolean conflictAppoint(Appointment otherAppointment) {
     * LocalDateTime thisEndTime = this.dateTime.plusMinutes(this.duration);
     * LocalDateTime otherEndTime =
     * other.getDateTime().plusMinutes(other.getDuration());
     * return this.dateTime.isBefore(otherEndTime) &&
     * other.getDateTime().isBefore(thisEndTime);
     * }
     */

    /*
     * public boolean isWithinDocAvailability() {
     * for (TimeSlot slot : doctor.getAvailability()) {
     * LocalDateTime endTime = this.dateTime.plusMinutes(this.duration);
     * if (!this.dateTime.isBefore(slot.getStartTime()) &&
     * !endTime.isAfter(slot.getEndTime())) {
     * return true;
     * }
     * }
     * return false;
     * }
     */

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
    /*
     * public class TimeSlot {
     * private LocalDateTime startTime;
     * private LocalDateTime endTime;
     * public TimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
     * this.startTime = startTime;
     * this.endTime = endTime;
     * }''
     * public void setStartTime(LocalDateTime startTime) {
     * this.startTime = startTime;
     * }
     * public LocalDateTime getStartTime() {
     * return startTime;
     * }
     * public void setEndTime(LocalDateTime endTime) {
     * this.endTime = endTime;
     * }
     * public LocalDateTime getEndTime() {
     * return endTime;
     * }
     * }
     */
}
