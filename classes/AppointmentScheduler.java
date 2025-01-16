import java.time.LocalDateTime;
import java.util.List;

public class AppointmentScheduler {

    private final DoctorRepository doctorRepo;
    private final AppointmentRepository appointmentRepo;

    public AppointmentScheduler(DoctorRepository doctorRepo, AppointmentRepository appointmentRepo) {
        this.doctorRepo = doctorRepo;
        this.appointmentRepo = appointmentRepo;
    }

    // Μέθοδος για την προγραμματισμένη διαδικασία ραντεβού
    public Appointment scheduleAppointment(String specialty, LocalDateTime requestedSlot, Patient patient) {
        List<Doctor> doctors = doctorRepo.getDoctorsBySpecialty(specialty);
        
        // Έλεγχος διαθεσιμότητας γιατρών στην επιθυμητή ώρα
        for (Doctor doctor : doctors) {
            if (appointmentRepo.isDoctorAvailable(doctor.getId(), requestedSlot)) {
                // Αν βρεθεί διαθέσιμος γιατρός, δημιουργείται το ραντεβού
                return appointmentRepo.createAppointment(doctor, patient, requestedSlot);
            }
        }

        // Αν δεν υπάρχει διαθέσιμος γιατρός στην αρχική ώρα, αναζητούμε την επόμενη διαθέσιμη ώρα
        for (Doctor doctor : doctors) {
            LocalDateTime nextAvailableSlot = appointmentRepo.findNextAvailableSlot(doctor.getId(), requestedSlot);
            if (nextAvailableSlot != null) {
                // Προτείνουμε στον ασθενή να κλείσει το ραντεβού στην επόμενη διαθέσιμη ώρα
                boolean patientAccepts = promptPatientForConfirmation(doctor, nextAvailableSlot);
                if (patientAccepts) {
                    return appointmentRepo.createAppointment(doctor, patient, nextAvailableSlot);
                }
            }
        }

        System.out.println("Δεν υπάρχει διαθεσιμότητα για την ώρα που ζητήσατε ή αργότερα.");
        return null; // Επιστρέφει null αν δεν βρεθεί διαθέσιμη ώρα
    }

    // Μέθοδος για να ζητήσει από τον ασθενή να επιβεβαιώσει αν δέχεται την επόμενη διαθέσιμη ώρα
    private boolean promptPatientForConfirmation(Doctor doctor, LocalDateTime nextAvailableSlot) {
        System.out.println("Η ζητούμενη ώρα δεν είναι διαθέσιμη.");
        System.out.println("Επόμενη διαθέσιμη ώρα:");
        System.out.println("Γιατρός: " + doctor.getName());
        System.out.println("Ειδικότητα: " + doctor.getSpeciality());
        System.out.println("Ώρα: " + nextAvailableSlot);
        System.out.println("Θέλετε να κλείσετε το ραντεβού για αυτή την ώρα; (ναι/όχι)");

        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            String response = scanner.nextLine();
            return response.equalsIgnoreCase("ναι");
        }
    }
}
