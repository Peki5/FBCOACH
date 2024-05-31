package hr.fer.fbcoach.rest;

import hr.fer.fbcoach.model.*;
import hr.fer.fbcoach.model.dto.PlayerDTO;
import hr.fer.fbcoach.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PlayerController {

    private final PlayerService playerService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    public List<PlayerDTO> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        return players.stream()
                .filter(player -> player.getTeams() == null || player.getTeams().isEmpty()) // Exclude players in any team
                .sorted(Comparator.comparing(Player::getIdPlayer))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("")
    public List<PlayerDTO> getAllPlayersByTeamId(@RequestParam Long teamId) {
        List<Player> players = playerService.getAllPlayersByTeamId(teamId);
        return players.stream()
                .sorted(Comparator.comparing(Player::getIdPlayer))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long id) {
        return playerService.getPlayerById(id)
                .map(player -> ResponseEntity.ok(convertToDto(player)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<PlayerDTO> addPlayer(@RequestBody PlayerDTO playerDTO) {
        System.out.println("Received request to add player: " + playerDTO); // Debugging line
        Player player = convertToEntity(playerDTO);
        Player createdPlayer = playerService.addPlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdPlayer));
    }


    @PostMapping("/addToTeam")
    public ResponseEntity<Void> addPlayerToTeam(@RequestBody Map<String, Long> payload) {
        Long playerId = payload.get("idPlayer");
        Long teamId = payload.get("teamId");
        playerService.addPlayerToTeam(playerId, teamId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO playerDTO) {
        Player player = convertToEntity(playerDTO);
        return ResponseEntity.ok(convertToDto(playerService.updatePlayer(id, player)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removePlayerFromTeam(@RequestParam Long playerId, @RequestParam Long teamId) {
        playerService.removePlayerFromTeam(playerId, teamId);
        return ResponseEntity.noContent().build();
    }

    private PlayerDTO convertToDto(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setIdPlayer(player.getIdPlayer());
        playerDTO.setFirstname(player.getFirstname());
        playerDTO.setLastName(player.getLastName());
        playerDTO.setDateOfBirth(player.getDateOfBirth());
        playerDTO.setPosition(player.getPosition());
        playerDTO.setHeight(player.getHeight());
        playerDTO.setWeight(player.getWeight());

        playerDTO.setTeamIds(player.getTeams() != null ?
                player.getTeams().stream()
                        .map(Team::getIdTeam)
                        .collect(Collectors.toList()) : new ArrayList<>());

        // Manually set teamRosterIds, healthStatusIds, and attendanceIds
        playerDTO.setPlayerMatchStatsIds(player.getPlayerMatchStats() != null ?
                player.getPlayerMatchStats().stream()
                        .map(PlayerMatchStats::getIdPlayerMatchStats)
                        .collect(Collectors.toList()) : new ArrayList<>());

        playerDTO.setHealthStatusIds(player.getHealthStatuses() != null ?
                player.getHealthStatuses().stream()
                        .map(HealthStatus::getIdHealthStatus)
                        .collect(Collectors.toList()) : new ArrayList<>());

        playerDTO.setAttendanceIds(player.getAttendances() != null ?
                player.getAttendances().stream()
                        .map(Attendance::getIdAttendance)
                        .collect(Collectors.toList()) : new ArrayList<>());

        return playerDTO;
    }

    private Player convertToEntity(PlayerDTO playerDTO) {
        return modelMapper.map(playerDTO, Player.class);
    }
}
