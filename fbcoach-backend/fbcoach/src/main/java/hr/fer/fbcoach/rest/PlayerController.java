package hr.fer.fbcoach.rest;

import hr.fer.fbcoach.model.Player;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public List<PlayerDTO> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
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
        Player player = convertToEntity(playerDTO);
        Player createdPlayer = playerService.addPlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdPlayer));
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

    private PlayerDTO convertToDto(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setIdPlayer(player.getIdPlayer());
        playerDTO.setFirstname(player.getFirstname());
        playerDTO.setLastName(player.getLastName());
        playerDTO.setDateOfBirth(player.getDateOfBirth());
        playerDTO.setPosition(player.getPosition());
        playerDTO.setHeight(player.getHeight());
        playerDTO.setWeight(player.getWeight());

        // Manually set teamRosterIds, healthStatusIds, and attendanceIds
        playerDTO.setTeamRosterIds(player.getTeamRosters() != null ?
                player.getTeamRosters().stream()
                        .map(teamRoster -> teamRoster.getIdTeamRoster())
                        .collect(Collectors.toList()) : new ArrayList<>());

        playerDTO.setHealthStatusIds(player.getHealthStatuses() != null ?
                player.getHealthStatuses().stream()
                        .map(healthStatus -> healthStatus.getIdHealthStatus())
                        .collect(Collectors.toList()) : new ArrayList<>());

        playerDTO.setAttendanceIds(player.getAttendances() != null ?
                player.getAttendances().stream()
                        .map(attendance -> attendance.getIdAttendance())
                        .collect(Collectors.toList()) : new ArrayList<>());

        return playerDTO;
    }

    private Player convertToEntity(PlayerDTO playerDTO) {
        return modelMapper.map(playerDTO, Player.class);
    }
}
