import java.time.LocalDateTime;
import java.util.List;

public class Appointment {
    private Doctor doctor;
    private String specialty;
    private Patient patient;
    private LocalDayTime dateTime;
    private int duration;
    private String appointmentType;
    private String labRequirements;

    public Appointment(Doctor doctor, String specialty, Patient patient, LocalDayTime dateTime, int duration,
            String appointmentType, String labRequirements) {
        this.doctor = doctor;
        this.specialty = specialty;
        this.patient = patient;
        this.dateTime = dateTime;
        this.duration = duration;
        this.appointmentType = appointmentType;
        this.labRequirements = labRequirements;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        this.specialty = doctor.getSpecialty();
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        retutn patient;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setLabRequirements(String labRequirements) {
        this.labRequirements = labRequirements;
    }

    public String getLabRequirements() {
        return labRequirements;
    }

    public boolean conflictAppoint(Appointment otherAppointment) {
        LocalDateTime thisEndTime = this.dateTime.plusMinutes(this.duration);
        LocalDateTime otherEndTime = other.getDateTime().plusMinutes(other.getDuration());
        return this.dateTime.isBefore(otherEndTime) && other.getDateTime().isBefore(thisEndTime);
    }

    public boolean isWithinDocAvailability() {
        for (TimeSlot slot : doctor.getAvailability()) {
            LocalDateTime endTime = this.dateTime.plusMinutes(this.duration);
            if (!this.dateTime.isBefore(slot.getStartTime()) && !endTime.isAfter(slot.getEndTime())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "doctor=" + doctor.getName() +
                ", specialty='" + specialty +
                ", patient=" + patient.getName() +
                ", dateTime=" + dateTime +
                ", duration=" + duration + "minutes" +
                ", appointmentType='" + appointmentType +
                ", LabRequirements='" + labRequirements +
                '}';
    }
}

public class TimeSlot {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}