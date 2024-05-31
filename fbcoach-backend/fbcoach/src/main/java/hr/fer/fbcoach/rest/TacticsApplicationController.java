package hr.fer.fbcoach.rest;

import hr.fer.fbcoach.model.TacticsApplication;
import hr.fer.fbcoach.model.dto.TacticsApplicationDTO;
import hr.fer.fbcoach.service.TacticsApplicationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tacticsapplication")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TacticsApplicationController {

    private final TacticsApplicationService tacticsApplicationService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public List<TacticsApplicationDTO> listAll() {
        List<TacticsApplication> tacticsApplications = tacticsApplicationService.getAllTacticsApplications();
        return tacticsApplications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TacticsApplicationDTO> getTacticsApplicationById(@PathVariable Long id) {
        TacticsApplication tacticsApplication = tacticsApplicationService.getTacticsApplicationById(id);
        return ResponseEntity.ok(convertToDto(tacticsApplication));
    }

    @GetMapping("/match/{matchId}")
    public List<TacticsApplicationDTO> getTacticsApplicationsByMatchId(@PathVariable Long matchId) {
        List<TacticsApplication> tacticsApplications = tacticsApplicationService.getTacticsApplicationsByMatchId(matchId);
        return tacticsApplications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/tactics/{tacticsId}")
    public List<TacticsApplicationDTO> getTacticsApplicationsByTacticsId(@PathVariable Long tacticsId) {
        List<TacticsApplication> tacticsApplications = tacticsApplicationService.getTacticsApplicationsByTacticsId(tacticsId);
        return tacticsApplications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public ResponseEntity<TacticsApplicationDTO> addTacticsApplication(@RequestBody TacticsApplicationDTO tacticsApplicationDTO) {
        TacticsApplication tacticsApplication = convertToEntity(tacticsApplicationDTO);
        TacticsApplication createdTacticsApplication = tacticsApplicationService.addTacticsApplication(tacticsApplication);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdTacticsApplication));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<TacticsApplicationDTO> editTacticsApplication(@PathVariable Long id, @RequestBody TacticsApplicationDTO tacticsApplicationDTO) {
        TacticsApplication tacticsApplication = convertToEntity(tacticsApplicationDTO);
        TacticsApplication updatedTacticsApplication = tacticsApplicationService.updateTacticsApplication(id, tacticsApplication);
        return ResponseEntity.ok(convertToDto(updatedTacticsApplication));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTacticsApplication(@PathVariable Long id) {
        tacticsApplicationService.deleteTacticsApplication(id);
        return ResponseEntity.noContent().build();
    }

    private TacticsApplicationDTO convertToDto(TacticsApplication tacticsApplication) {
        return modelMapper.map(tacticsApplication, TacticsApplicationDTO.class);
    }

    private TacticsApplication convertToEntity(TacticsApplicationDTO tacticsApplicationDTO) {
        return modelMapper.map(tacticsApplicationDTO, TacticsApplication.class);
    }
}
