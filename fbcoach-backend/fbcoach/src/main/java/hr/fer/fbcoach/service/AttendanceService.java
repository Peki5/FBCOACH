package hr.fer.fbcoach.service;

import hr.fer.fbcoach.model.Attendance;

import java.util.List;
import java.util.Optional;

public interface AttendanceService {
    List<Attendance> getAllAttendances();
    Optional<Attendance> getAttendanceById(Long id);
    Attendance addAttendance(Attendance attendance);
    Attendance updateAttendance(Long id, Attendance attendanceDetails);
    void deleteAttendance(Long id);
    List<Attendance> getAttendanceByPlayerId(Long playerId);

}
