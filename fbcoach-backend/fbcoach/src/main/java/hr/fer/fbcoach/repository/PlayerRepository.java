package hr.fer.fbcoach.repository;

import hr.fer.fbcoach.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
