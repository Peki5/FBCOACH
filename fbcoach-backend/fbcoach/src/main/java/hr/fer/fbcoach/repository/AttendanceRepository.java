package hr.fer.fbcoach.repository;

import hr.fer.fbcoach.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByPlayerIdIdPlayer(Long playerId);
}
