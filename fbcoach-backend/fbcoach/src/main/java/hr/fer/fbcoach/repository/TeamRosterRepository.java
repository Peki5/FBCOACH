package hr.fer.fbcoach.repository;

import hr.fer.fbcoach.model.TeamRoster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRosterRepository extends JpaRepository<TeamRoster, Long> {
    List<TeamRoster> findByTeamId_IdTeam(Long teamId);

    List<TeamRoster> findByPlayerId_IdPlayer(Long playerId);
}
