package hr.fer.fbcoach.service.impl;

import hr.fer.fbcoach.model.HealthStatus;
import hr.fer.fbcoach.model.exception.ResourceNotFoundException;
import hr.fer.fbcoach.repository.HealthStatusRepository;
import hr.fer.fbcoach.service.HealthStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HealthStatusServiceJpa implements HealthStatusService {

    private final HealthStatusRepository healthStatusRepository;

    public List<HealthStatus> getAllHealthStatuses() {
        return healthStatusRepository.findAll();
    }

    public Optional<HealthStatus> getHealthStatusById(Long id) {
        return healthStatusRepository.findById(id);
    }

    public HealthStatus addHealthStatus(HealthStatus healthStatus) {
        return healthStatusRepository.save(healthStatus);
    }

    public HealthStatus updateHealthStatus(Long id, HealthStatus healthStatusDetails) {
        return healthStatusRepository.findById(id).map(healthStatus -> {
            healthStatus.setDate(healthStatusDetails.getDate());
            healthStatus.setStatusNotes(healthStatusDetails.getStatusNotes());
            return healthStatusRepository.save(healthStatus);
        }).orElseThrow(() -> new ResourceNotFoundException("Health status not found with id " + id));
    }

    public void deleteHealthStatus(Long id) {
        healthStatusRepository.deleteById(id);
    }

    public List<HealthStatus> getHealthStatusesByPlayerId(Long playerId) {
        return healthStatusRepository.findByPlayerIdIdPlayer(playerId);
    }
}
