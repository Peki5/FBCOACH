package hr.fer.fbcoach.rest;

import hr.fer.fbcoach.model.*;
import hr.fer.fbcoach.model.dto.TeamDTO;
import hr.fer.fbcoach.service.TeamService;
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
@RequestMapping("/team")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TeamController {

    private final TeamService teamService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public List<TeamDTO> listAll() {
        List<Team> teams = teamService.listAllTeamsForSignedInCoach();
        return teams.stream()
                .sorted(Comparator.comparing(Team::getIdTeam))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) {
        Team team = teamService.getTeamById(id);
        if (team != null) {
            return ResponseEntity.ok(convertToDto(team));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/add")
    public ResponseEntity<TeamDTO> addTeam(@RequestBody TeamDTO teamDTO) {
        Team team = convertToEntity(teamDTO);
        Team createdTeam = teamService.addTeam(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdTeam));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<TeamDTO> editTeam(@PathVariable Long id, @RequestBody TeamDTO teamDTO) {
        Team team = convertToEntity(teamDTO);
        Team updatedTeam = teamService.editTeam(id, team);
        return ResponseEntity.ok(convertToDto(updatedTeam));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    private TeamDTO convertToDto(Team team) {
        TeamDTO teamDTO = modelMapper.map(team, TeamDTO.class);
        teamDTO.setCoachId(team.getCoach().getIdKorisnika()); // Assuming getId() is the method to get coach's ID

        teamDTO.setTrainingIds(team.getTrainings() != null ?
                team.getTrainings().stream()
                        .filter(training -> training.getTeamId().equals(team.getIdTeam()))
                        .map(Training::getIdTraining)
                        .collect(Collectors.toList()) : new ArrayList<>()); // Assuming getIdTraining() is the method to get training ID

        teamDTO.setTeamRosterIds(team.getTeamRosters() != null ?
                team.getTeamRosters().stream()
                        .filter(teamRoster -> teamRoster.getTeamId().equals(team.getIdTeam()))
                        .map(TeamRoster::getIdTeamRoster)
                        .collect(Collectors.toList()) : new ArrayList<>()); // Assuming getIdTeamRoster() is the method to get teamRoster ID

        teamDTO.setTacticsApplicationIds(team.getTacticsApplications() != null ?
                team.getTacticsApplications().stream()
                        .filter(tacticsApplication -> tacticsApplication.getTeamId().equals(team.getIdTeam()))
                        .map(TacticsApplication::getIdTacticsApplication)
                        .collect(Collectors.toList()) : new ArrayList<>()); // Assuming getIdTacticsApplication() is the method to get tacticsApplication ID

        return teamDTO;
    }



    private Team convertToEntity(TeamDTO teamDTO) {
        // Coach and other relationships will be set in the service layer
        return modelMapper.map(teamDTO, Team.class);
    }
}
