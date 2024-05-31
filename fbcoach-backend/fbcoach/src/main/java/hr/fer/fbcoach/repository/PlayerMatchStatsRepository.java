package hr.fer.fbcoach.repository;

import hr.fer.fbcoach.model.PlayerMatchStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerMatchStatsRepository extends JpaRepository<PlayerMatchStats, Long> {

    List<PlayerMatchStats> findByPlayerId_IdPlayer(Long playerId);

    List<PlayerMatchStats> findByMatchId_IdMatch(Long matchId);
}
