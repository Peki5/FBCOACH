package hr.fer.fbcoach.service;

import hr.fer.fbcoach.model.HealthStatus;

import java.util.List;
import java.util.Optional;

public interface HealthStatusService {
    List<HealthStatus> getAllHealthStatuses();
    Optional<HealthStatus> getHealthStatusById(Long id);
    HealthStatus addHealthStatus(HealthStatus healthStatus);
    HealthStatus updateHealthStatus(Long id, HealthStatus healthStatusDetails);
    void deleteHealthStatus(Long id);
    List<HealthStatus> getHealthStatusesByPlayerId(Long playerId);
}
