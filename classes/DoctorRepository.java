import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepository {

    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM Doctors WHERE Specialty = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, specialty);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                doctors.add(new Doctor(
                        rs.getInt("DoctorID"),
                        rs.getString("Name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }
}
