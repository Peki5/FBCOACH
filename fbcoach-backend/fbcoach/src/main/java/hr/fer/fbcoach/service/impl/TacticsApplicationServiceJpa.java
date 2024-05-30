package hr.fer.fbcoach.service.impl;

import hr.fer.fbcoach.model.TacticsApplication;
import hr.fer.fbcoach.model.exception.ResourceNotFoundException;
import hr.fer.fbcoach.repository.TacticsApplicationRepository;
import hr.fer.fbcoach.service.TacticsApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TacticsApplicationServiceJpa implements TacticsApplicationService {

    private final TacticsApplicationRepository tacticsApplicationRepository;

    public List<TacticsApplication> getAllTacticsApplications() {
        return tacticsApplicationRepository.findAll();
    }

    public TacticsApplication getTacticsApplicationById(Long id) {
        return tacticsApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TacticsApplication not found with id " + id));
    }

    public TacticsApplication addTacticsApplication(TacticsApplication tacticsApplication) {
        return tacticsApplicationRepository.save(tacticsApplication);
    }

    public TacticsApplication updateTacticsApplication(Long id, TacticsApplication tacticsApplicationDetails) {
        TacticsApplication tacticsApplication = getTacticsApplicationById(id);
        tacticsApplication.setTacticsId(tacticsApplicationDetails.getTacticsId());
        tacticsApplication.setMatchId(tacticsApplicationDetails.getMatchId());
        return tacticsApplicationRepository.save(tacticsApplication);
    }

    public void deleteTacticsApplication(Long id) {
        TacticsApplication tacticsApplication = getTacticsApplicationById(id);
        tacticsApplicationRepository.delete(tacticsApplication);
    }

    public List<TacticsApplication> getTacticsApplicationsByMatchId(Long matchId) {
        return tacticsApplicationRepository.findByMatchId_IdMatch(matchId);
    }

    public List<TacticsApplication> getTacticsApplicationsByTacticsId(Long tacticsId) {
        return tacticsApplicationRepository.findByTacticsId_IdTactics(tacticsId);
    }
}
