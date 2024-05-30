package hr.fer.fbcoach.repository;

import hr.fer.fbcoach.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training,Long> {
    List<Training> findByTeamId_IdTeam(Long teamId); // Assuming `teamId` is the property in `Training` referring to `Team`
}
