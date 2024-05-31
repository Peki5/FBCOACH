package hr.fer.fbcoach.service.impl;

import hr.fer.fbcoach.model.Tactics;
import hr.fer.fbcoach.model.exception.ResourceNotFoundException;
import hr.fer.fbcoach.repository.TacticsRepository;
import hr.fer.fbcoach.service.TacticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TacticsServiceJpa implements TacticsService {

    private final TacticsRepository tacticsRepository;

    public List<Tactics> getAllTactics() {
        return tacticsRepository.findAll();
    }

    public List<Tactics> getAllTacticsByTeamId(Long teamId) {
        return tacticsRepository.findByTeamId_IdTeam(teamId);
    }

    public Optional<Tactics> getTacticsById(Long id) {
        return tacticsRepository.findById(id);
    }

    public Tactics addTactics(Tactics tactics) {
        return tacticsRepository.save(tactics);
    }

    public Tactics updateTactics(Long id, Tactics tacticsDetails) {
        return tacticsRepository.findById(id).map(tactics -> {
            tactics.setName(tacticsDetails.getName());
            tactics.setDescription(tacticsDetails.getDescription());
            return tacticsRepository.save(tactics);
        }).orElseThrow(() -> new ResourceNotFoundException("Tactics not found with id " + id));
    }

    public void deleteTactics(Long id) {
        tacticsRepository.deleteById(id);
    }
}
