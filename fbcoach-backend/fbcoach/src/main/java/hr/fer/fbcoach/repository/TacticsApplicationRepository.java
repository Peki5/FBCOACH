package hr.fer.fbcoach.repository;

import hr.fer.fbcoach.model.TacticsApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TacticsApplicationRepository extends JpaRepository<TacticsApplication, Long> {
    List<TacticsApplication> findByMatchId_IdMatch(Long matchId);
    List<TacticsApplication> findByTacticsId_IdTactics(Long tacticsId);
    List<TacticsApplication> findByTeamId_IdTeam(Long teamId);
}
