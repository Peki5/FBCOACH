package hr.fer.fbcoach.repository;

import hr.fer.fbcoach.model.Korisnik;
import hr.fer.fbcoach.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByCoach(Korisnik coach);
}
