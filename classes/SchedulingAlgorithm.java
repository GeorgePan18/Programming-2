import java.time.LocalDateTime;
import java.util.List;

public class SchedulingAlgorithm {

     ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the task to run every 24 hours
        scheduler.scheduleAtFixedRate(() -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                LocalDate today = LocalDate.now();
                LocalDate newDate = today.plusWeeks(4);

                // Step 1: Delete expired slots
                try (PreparedStatement deleteStmt = conn.prepareStatement(
                        "DELETE FROM DoctorSchedule WHERE Date = ?")) {
                    deleteStmt.setDate(1, Date.valueOf(today));
                    deleteStmt.executeUpdate();
                }

                // Step 2: Insert new slots for the new day
                try (PreparedStatement insertStmt = conn.prepareStatement(
                        "INSERT INTO DoctorSchedule (DoctorID, Date, Hour, IsAvailable) VALUES (?, ?, ?, ?)")) {
                    for (int doctorId = 1; doctorId <= 15; doctorId++) { // 15 doctors
                        for (int hour = 9; hour < 17; hour++) { // 8 working hours
                            insertStmt.setInt(1, doctorId);
                            insertStmt.setDate(2, Date.valueOf(newDate));
                            insertStmt.setInt(3, hour);
                            insertStmt.setBoolean(4, true); // Slots start as available
                            insertStmt.addBatch();
                        }
                    }
                    insertStmt.executeBatch();
                }

                System.out.println("Doctor schedule updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.DAYS);
    }


    private Appointment createAppointment(Doctor doctor, Patient patient, LocalDateTime slot) {
        return appointmentRepo.createAppointment(doctor, patient, slot);
    }
}
