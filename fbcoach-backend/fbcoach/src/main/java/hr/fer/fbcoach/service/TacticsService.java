package hr.fer.fbcoach.service;

import hr.fer.fbcoach.model.Tactics;

import java.util.List;
import java.util.Optional;

public interface TacticsService {
    List<Tactics> getAllTactics();
    Optional<Tactics> getTacticsById(Long id);
    Tactics addTactics(Tactics tactics);
    Tactics updateTactics(Long id, Tactics tacticsDetails);
    void deleteTactics(Long id);
    List<Tactics> getAllTacticsByTeamId(Long teamId);
}
