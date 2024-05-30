package hr.fer.fbcoach.repository;

import hr.fer.fbcoach.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByTeams_IdTeam(Long teamId);
}
