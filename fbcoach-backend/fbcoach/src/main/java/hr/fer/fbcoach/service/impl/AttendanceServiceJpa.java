package hr.fer.fbcoach.service.impl;

import hr.fer.fbcoach.model.Attendance;
import hr.fer.fbcoach.model.HealthStatus;
import hr.fer.fbcoach.model.exception.ResourceNotFoundException;
import hr.fer.fbcoach.repository.AttendanceRepository;
import hr.fer.fbcoach.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceServiceJpa implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    public Optional<Attendance> getAttendanceById(Long id) {
        return attendanceRepository.findById(id);
    }

    public Attendance addAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public Attendance updateAttendance(Long id, Attendance attendanceDetails) {
        return attendanceRepository.findById(id).map(attendance -> {
            attendance.setDate(attendanceDetails.getDate());
            attendance.setPresent(attendanceDetails.isPresent());
            attendance.setType(attendanceDetails.getType());
            attendance.setPlayerId(attendanceDetails.getPlayerId());
            return attendanceRepository.save(attendance);
        }).orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id " + id));
    }

    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

    public List<Attendance> getAttendanceByPlayerId(Long playerId) {
        return attendanceRepository.findByPlayerIdIdPlayer(playerId);
    }
}
