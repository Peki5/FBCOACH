package hr.fer.fbcoach.rest;

import hr.fer.fbcoach.model.HealthStatus;
import hr.fer.fbcoach.model.dto.HealthStatusDTO;
import hr.fer.fbcoach.service.HealthStatusService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/healthStatuses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class HealthStatusController {

    private final HealthStatusService healthStatusService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public List<HealthStatusDTO> getAllHealthStatuses() {
        List<HealthStatus> healthStatuses = healthStatusService.getAllHealthStatuses();
        return healthStatuses.stream()
                .sorted(Comparator.comparing(HealthStatus::getIdHealthStatus))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthStatusDTO> getHealthStatusById(@PathVariable Long id) {
        return healthStatusService.getHealthStatusById(id)
                .map(healthStatus -> ResponseEntity.ok(convertToDto(healthStatus)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<HealthStatusDTO> addHealthStatus(@RequestBody HealthStatusDTO healthStatusDTO) {
        HealthStatus healthStatus = convertToEntity(healthStatusDTO);
        HealthStatus createdHealthStatus = healthStatusService.addHealthStatus(healthStatus);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdHealthStatus));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<HealthStatusDTO> updateHealthStatus(@PathVariable Long id, @RequestBody HealthStatusDTO healthStatusDTO) {
        HealthStatus healthStatus = convertToEntity(healthStatusDTO);
        return ResponseEntity.ok(convertToDto(healthStatusService.updateHealthStatus(id, healthStatus)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHealthStatus(@PathVariable Long id) {
        healthStatusService.deleteHealthStatus(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/player/{playerId}")
    public List<HealthStatusDTO> getHealthStatusesByPlayerId(@PathVariable Long playerId) {
        List<HealthStatus> healthStatuses = healthStatusService.getHealthStatusesByPlayerId(playerId);
        return healthStatuses.stream()
                .sorted(Comparator.comparing(HealthStatus::getIdHealthStatus))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private HealthStatusDTO convertToDto(HealthStatus healthStatus) {
        return modelMapper.map(healthStatus, HealthStatusDTO.class);
    }

    private HealthStatus convertToEntity(HealthStatusDTO healthStatusDTO) {
        return modelMapper.map(healthStatusDTO, HealthStatus.class);
    }
}
