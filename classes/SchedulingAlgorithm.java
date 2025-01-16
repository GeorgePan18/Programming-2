import java.time.LocalDateTime;
import java.util.List;

public class SchedulingAlgorithm {

    private final DoctorRepository doctorRepo;
    private final AppointmentRepository appointmentRepo;

    public SchedulingAlgorithm(DoctorRepository doctorRepo, AppointmentRepository appointmentRepo) {
        this.doctorRepo = doctorRepo;
        this.appointmentRepo = appointmentRepo;
    }

    public Appointment findAndCreateAppointment(String specialty, LocalDateTime requestedSlot, Patient patient) {
        // Retrieve doctors by specialty
        List<Doctor> doctors = doctorRepo.getDoctorsBySpecialty(specialty);

        // Step 1: Check availability for the requested slot
        for (Doctor doctor : doctors) {
            if (appointmentRepo.isDoctorAvailable(doctor.getId(), requestedSlot)) {
                return createAppointment(doctor, patient, requestedSlot);
            }
        }

        // Step 2: Find the next available slot if none are available at the requested time
        Appointment nextAvailableAppointment = findNextAvailableAppointment(doctors, requestedSlot, patient);
        if (nextAvailableAppointment != null) {
            boolean patientAccepts = promptPatientForConfirmation(nextAvailableAppointment);
            if (patientAccepts) {
                return createAppointment(nextAvailableAppointment.getDoctor(), patient, nextAvailableAppointment.getSlot());
            }
        }

        // Step 3: If no slots are available or patient declines, return null
        System.out.println("No appointments available at the requested time or later.");
        return null;
    }

    private Appointment findNextAvailableAppointment(List<Doctor> doctors, LocalDateTime requestedSlot, Patient patient) {
        for (Doctor doctor : doctors) {
            LocalDateTime nextAvailableSlot = appointmentRepo.findNextAvailableSlot(doctor.getId(), requestedSlot);
            if (nextAvailableSlot != null) {
                return new Appointment(doctor, patient, nextAvailableSlot);
            }
        }
        return null;
    }

    private boolean promptPatientForConfirmation(Appointment appointment) {
        System.out.println("The requested time is unavailable.");
        System.out.println("Next available slot:");
        System.out.println("Doctor: " + appointment.getDoctor().getName());
        System.out.println("Specialty: " + appointment.getDoctor().getSpeciality());
        System.out.println("Slot: " + appointment.getSlot());
        System.out.println("Do you accept this appointment? (yes/no)");

        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            String response = scanner.nextLine();
            return response.equalsIgnoreCase("yes");
        }
    }

    private Appointment createAppointment(Doctor doctor, Patient patient, LocalDateTime slot) {
        return appointmentRepo.createAppointment(doctor, patient, slot);
    }
}
