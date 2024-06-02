package hr.fer.fbcoach.rest;

import hr.fer.fbcoach.model.Training;
import hr.fer.fbcoach.model.dto.TrainingDTO;
import hr.fer.fbcoach.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/training")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TrainingController {

    private final TrainingService trainingService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public List<TrainingDTO> listAll(@RequestParam Long teamId) {
        List<Training> trainings = trainingService.listAllTrainingsByTeamId(teamId);
        return trainings.stream()
                .sorted(Comparator.comparing(Training::getIdTraining))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingDTO> getTrainingById(@PathVariable Long id) {
        Training training = trainingService.getTrainingById(id);
        if (training != null) {
            return ResponseEntity.ok(convertToDto(training));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<TrainingDTO> addTraining(@RequestBody TrainingDTO trainingDTO) {
        Training training = convertToEntity(trainingDTO);
        Training createdTraining = trainingService.addTraining(training);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdTraining));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<TrainingDTO> editTraining(@PathVariable Long id, @RequestBody TrainingDTO trainingDTO) {
        Training training = convertToEntity(trainingDTO);
        Training updatedTraining = trainingService.editTraining(id, training);
        return ResponseEntity.ok(convertToDto(updatedTraining));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return ResponseEntity.noContent().build();
    }

    private TrainingDTO convertToDto(Training training) {
        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setIdTraining(training.getIdTraining());
        trainingDTO.setDate(training.getDate());
        trainingDTO.setDescription(training.getDescription());
        trainingDTO.setType(training.getType());

        if (training.getTeamId() != null) {
            trainingDTO.setTeamId(training.getTeamId().getIdTeam());
        }

        return trainingDTO;
    }

    private Training convertToEntity(TrainingDTO trainingDTO) {
        return modelMapper.map(trainingDTO, Training.class);
    }
}
