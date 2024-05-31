package hr.fer.fbcoach.repository;

import hr.fer.fbcoach.model.Tactics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TacticsRepository extends JpaRepository<Tactics, Long> {
    List<Tactics> findByTeamId_IdTeam(Long teamId);
}
