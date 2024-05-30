package hr.fer.fbcoach.service;

import hr.fer.fbcoach.model.Training;

import java.util.List;

public interface TrainingService {
    List<Training> listAllTrainingsByTeamId(Long teamId);

    Training addTraining(Training training);

    Training editTraining(Long id, Training training);

    void deleteTraining(Long id);

    Training getTrainingById(Long id);
}
