package hr.fer.fbcoach.rest;

import hr.fer.fbcoach.model.PlayerMatchStats;
import hr.fer.fbcoach.model.dto.PlayerMatchStatsDTO;
import hr.fer.fbcoach.service.PlayerMatchStatsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teamRoster")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PlayerMatchStatsController {

    private final PlayerMatchStatsService playerMatchStatsService;
    private final ModelMapper modelMapper;

    @GetMapping("team/{teamId}")
    public ResponseEntity<List<PlayerMatchStatsDTO>> getTeamRostersByTeamId(@PathVariable Long teamId) {
        List<PlayerMatchStats> playerMatchStats = playerMatchStatsService.getTeamRostersByTeamId(teamId);
        List<PlayerMatchStatsDTO> playerMatchStatsDTOS = playerMatchStats.stream()
                .sorted(Comparator.comparing(PlayerMatchStats::getIdTeamRoster))
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(playerMatchStatsDTOS);
    }

    @GetMapping("player/{playerId}")
    public ResponseEntity<List<PlayerMatchStatsDTO>> getTeamRostersByPlayerId(@PathVariable Long playerId) {
        List<PlayerMatchStats> playerMatchStats = playerMatchStatsService.getTeamRostersByPlayerId(playerId);
        List<PlayerMatchStatsDTO> playerMatchStatsDTOS = playerMatchStats.stream()
                .sorted(Comparator.comparing(PlayerMatchStats::getIdTeamRoster))
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(playerMatchStatsDTOS);
    }

    @PostMapping("/add")
    public ResponseEntity<PlayerMatchStatsDTO> createTeamRoster(@RequestBody PlayerMatchStatsDTO playerMatchStatsDTO) {
        PlayerMatchStats playerMatchStats = convertToEntity(playerMatchStatsDTO);
        PlayerMatchStats createdPlayerMatchStats = playerMatchStatsService.createTeamRoster(playerMatchStats);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdPlayerMatchStats));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<PlayerMatchStatsDTO> updateTeamRoster(@PathVariable Long id, @RequestBody PlayerMatchStatsDTO playerMatchStatsDTO) {
        PlayerMatchStats playerMatchStats = convertToEntity(playerMatchStatsDTO);
        PlayerMatchStats updatedPlayerMatchStats = playerMatchStatsService.updateTeamRoster(id, playerMatchStats);
        return ResponseEntity.ok(convertToDto(updatedPlayerMatchStats));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTeamRoster(@PathVariable Long id) {
        playerMatchStatsService.deleteTeamRoster(id);
        return ResponseEntity.noContent().build();
    }

    private PlayerMatchStatsDTO convertToDto(PlayerMatchStats playerMatchStats) {
        PlayerMatchStatsDTO playerMatchStatsDTO = new PlayerMatchStatsDTO();
        playerMatchStatsDTO.setIdTeamRoster(playerMatchStats.getIdTeamRoster());
        playerMatchStatsDTO.setGoals(playerMatchStats.getGoals());
        playerMatchStatsDTO.setAssists(playerMatchStats.getAssists());

        // Manually set playerId and matchId
        if (playerMatchStats.getPlayerId() != null) {
            playerMatchStatsDTO.setPlayerId(playerMatchStats.getPlayerId().getIdPlayer());
        }
        if (playerMatchStats.getMatchId() != null) {
            playerMatchStatsDTO.setMatchId(playerMatchStats.getMatchId().getIdMatch());
        }

        return playerMatchStatsDTO;
    }


    private PlayerMatchStats convertToEntity(PlayerMatchStatsDTO playerMatchStatsDTO) {
        return modelMapper.map(playerMatchStatsDTO, PlayerMatchStats.class);
    }
}
