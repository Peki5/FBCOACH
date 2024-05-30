package hr.fer.fbcoach.rest;

import hr.fer.fbcoach.model.Match;
import hr.fer.fbcoach.model.Team;
import hr.fer.fbcoach.model.TeamRoster;
import hr.fer.fbcoach.model.dto.TeamRosterDTO;
import hr.fer.fbcoach.service.TeamRosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teamRoster")
@RequiredArgsConstructor
public class TeamRosterController {

    private final TeamRosterService teamRosterService;

    @GetMapping("team/{teamId}")
    public ResponseEntity<List<TeamRosterDTO>> getTeamRostersByTeamId(@PathVariable Long teamId) {
        List<TeamRoster> teamRosters = teamRosterService.getTeamRostersByTeamId(teamId);
        List<TeamRosterDTO> teamRosterDTOs = teamRosters.stream()
                .sorted(Comparator.comparing(TeamRoster::getIdTeamRoster))
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teamRosterDTOs);
    }

    @GetMapping("player/{playerId}")
    public ResponseEntity<List<TeamRosterDTO>> getTeamRostersByPlayerId(@PathVariable Long playerId) {
        List<TeamRoster> teamRosters = teamRosterService.getTeamRostersByPlayerId(playerId);
        List<TeamRosterDTO> teamRosterDTOs = teamRosters.stream()
                .sorted(Comparator.comparing(TeamRoster::getIdTeamRoster))
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teamRosterDTOs);
    }

    @PostMapping("/add")
    public ResponseEntity<TeamRosterDTO> createTeamRoster(@RequestBody TeamRosterDTO teamRosterDTO) {
        TeamRoster teamRoster = convertToEntity(teamRosterDTO);
        TeamRoster createdTeamRoster = teamRosterService.createTeamRoster(teamRoster);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdTeamRoster));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<TeamRosterDTO> updateTeamRoster(@PathVariable Long id, @RequestBody TeamRosterDTO teamRosterDTO) {
        TeamRoster teamRoster = convertToEntity(teamRosterDTO);
        TeamRoster updatedTeamRoster = teamRosterService.updateTeamRoster(id, teamRoster);
        return ResponseEntity.ok(convertToDto(updatedTeamRoster));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTeamRoster(@PathVariable Long id) {
        teamRosterService.deleteTeamRoster(id);
        return ResponseEntity.noContent().build();
    }

    private TeamRosterDTO convertToDto(TeamRoster teamRoster) {
        TeamRosterDTO teamRosterDTO = new TeamRosterDTO();
        teamRosterDTO.setIdTeamRoster(teamRoster.getIdTeamRoster());
        teamRosterDTO.setGoals(teamRoster.getGoals());
        teamRosterDTO.setAssists(teamRoster.getAssists());

        // Manually set teamId and playerId
        if (teamRoster.getTeamId() != null) {
            teamRosterDTO.setTeamId(teamRoster.getTeamId().getIdTeam());
        }
        if (teamRoster.getPlayerId() != null) {
            teamRosterDTO.setPlayerId(teamRoster.getPlayerId().getIdPlayer()); // Assuming getIdPlayer() is the method to get player's ID
        }

        // Set matchIds
        teamRosterDTO.setMatchesIds(teamRoster.getMatches() != null ?
                teamRoster.getMatches().stream()
                        .filter(match -> match.getTeamRosterId().getIdTeamRoster().equals(teamRoster.getIdTeamRoster()))
                        .map(Match::getIdMatch) // Assuming getIdMatch() is the method to get match ID
                        .collect(Collectors.toList()) : new ArrayList<>());

        return teamRosterDTO;
    }


    private TeamRoster convertToEntity(TeamRosterDTO teamRosterDTO) {
        TeamRoster teamRoster = new TeamRoster();
        teamRoster.setIdTeamRoster(teamRosterDTO.getIdTeamRoster());
        teamRoster.setGoals(teamRosterDTO.getGoals());
        teamRoster.setAssists(teamRosterDTO.getAssists());
        // Set teamId and playerId as required
        // You may need to fetch Team and Player entities from the database based on their IDs
        // and then set them to the TeamRoster entity.
        return teamRoster;
    }
}
