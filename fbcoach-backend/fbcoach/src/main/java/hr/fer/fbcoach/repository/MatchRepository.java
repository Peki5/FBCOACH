package hr.fer.fbcoach.repository;

import hr.fer.fbcoach.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
