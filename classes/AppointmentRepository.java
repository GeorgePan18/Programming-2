import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {

    public boolean isDoctorAvailable(int doctorId, LocalDateTime slot) {
        String sql = "SELECT IsAvailable FROM DoctorTimetable WHERE DoctorID = ? AND Slot = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            stmt.setTimestamp(2, Timestamp.valueOf(slot));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("IsAvailable");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public LocalDateTime findNextAvailableSlot(int doctorId, LocalDateTime requestedSlot) {
        String sql = "SELECT Slot FROM DoctorTimetable WHERE DoctorID = ? AND Slot > ? AND IsAvailable = TRUE ORDER BY Slot LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            stmt.setTimestamp(2, Timestamp.valueOf(requestedSlot));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp("Slot").toLocalDateTime();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Appointment createAppointment(Doctor doctor, Patient patient, LocalDateTime slot) {
        String sql = "INSERT INTO Appointments (DoctorID, PatientID, Slot) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctor.getId());
            stmt.setInt(2, patient.getId());
            stmt.setTimestamp(3, Timestamp.valueOf(slot));
            stmt.executeUpdate();

            markSlotAsBooked(doctor.getId(), slot);
            return new Appointment(doctor, patient, slot);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

   
