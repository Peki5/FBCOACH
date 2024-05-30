package hr.fer.fbcoach.rest;

import hr.fer.fbcoach.model.Match;
import hr.fer.fbcoach.model.Tactics;
import hr.fer.fbcoach.model.TacticsApplication;
import hr.fer.fbcoach.model.dto.TacticsDTO;
import hr.fer.fbcoach.service.TacticsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tactics")
@RequiredArgsConstructor
public class TacticsController {

    private final TacticsService tacticsService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public List<TacticsDTO> listAll() {
        List<Tactics> tacticsList = tacticsService.getAllTactics();
        return tacticsList.stream()
                .sorted(Comparator.comparing(Tactics::getIdTactics))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TacticsDTO> getTacticsById(@PathVariable Long id) {
        return tacticsService.getTacticsById(id)
                .map(tactics -> ResponseEntity.ok(convertToDto(tactics)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<TacticsDTO> addTactics(@RequestBody TacticsDTO tacticsDTO) {
        Tactics tactics = convertToEntity(tacticsDTO);
        Tactics createdTactics = tacticsService.addTactics(tactics);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdTactics));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<TacticsDTO> editTactics(@PathVariable Long id, @RequestBody TacticsDTO tacticsDTO) {
        Tactics tactics = convertToEntity(tacticsDTO);
        Tactics updatedTactics = tacticsService.updateTactics(id, tactics);
        return ResponseEntity.ok(convertToDto(updatedTactics));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTactics(@PathVariable Long id) {
        tacticsService.deleteTactics(id);
        return ResponseEntity.noContent().build();
    }

    private TacticsDTO convertToDto(Tactics tactics) {
        TacticsDTO tacticsDTO = new TacticsDTO();
        tacticsDTO.setIdTactics(tactics.getIdTactics());
        tacticsDTO.setName(tactics.getName());
        tacticsDTO.setDescription(tactics.getDescription());
        tacticsDTO.setTacticsApplicationsIds(tactics.getTacticsApplications() != null ?
                tactics.getTacticsApplications().stream()
                        .map(TacticsApplication::getIdTacticsApplication)
                        .collect(Collectors.toList()) : new ArrayList<>());

        return tacticsDTO;
    }

    private Tactics convertToEntity(TacticsDTO tacticsDTO) {
        return modelMapper.map(tacticsDTO, Tactics.class);
    }
}
