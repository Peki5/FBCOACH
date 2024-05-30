package hr.fer.fbcoach.repository;

import hr.fer.fbcoach.model.HealthStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthStatusRepository extends JpaRepository<HealthStatus, Long> {
    List<HealthStatus> findByPlayerIdIdPlayer(Long playerId);
}
