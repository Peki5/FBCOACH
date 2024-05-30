package hr.fer.fbcoach.service.impl;

import hr.fer.fbcoach.model.Training;
import hr.fer.fbcoach.repository.TrainingRepository;
import hr.fer.fbcoach.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingServiceJpa implements TrainingService {

    private final TrainingRepository trainingRepository;

    public List<Training> listAllTrainingsByTeamId(Long teamId) {
        return trainingRepository.findByTeamId_IdTeam(teamId);
    }

    public Training getTrainingById(Long id) {
        return trainingRepository.findById(id).orElse(null);
    }

    public Training addTraining(Training training) {
        return trainingRepository.save(training);
    }

    public Training editTraining(Long id, Training training) {
        training.setIdTraining(id);
        return trainingRepository.save(training);
    }

    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }
}
