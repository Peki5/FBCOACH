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
        teamDTO.setCoachId(team.getCoach().getIdKorisnika());

        teamDTO.setTrainingIds(team.getTrainings() != null ?
                team.getTrainings().stream()
                        .filter(training -> training.getTeamId().getIdTeam().equals(team.getIdTeam()))
                        .map(Training::getIdTraining)
                        .collect(Collectors.toList()) : new ArrayList<>());

        teamDTO.setPlayerIds(team.getPlayers() != null ?
                team.getPlayers().stream()
                        .filter(player -> player.getTeams().stream()
                                .anyMatch(t -> t.getIdTeam().equals(team.getIdTeam())))
                        .map(Player::getIdPlayer)
                        .collect(Collectors.toList()) : new ArrayList<>());

        teamDTO.setMatchIds(team.getMatches() != null ?
                team.getMatches().stream()
                        .filter(match -> match.getTeamId().getIdTeam().equals(team.getIdTeam()))
                        .map(Match::getIdMatch)
                        .collect(Collectors.toList()) : new ArrayList<>());

        teamDTO.setTacticIds(team.getTactics() != null ?
                team.getTactics().stream()
                        .filter(tactics -> tactics.getTeamId().getIdTeam().equals(team.getIdTeam()))
                        .map(Tactics::getIdTactics)
                        .collect(Collectors.toList()) : new ArrayList<>());

        return teamDTO;
    }



    private Team convertToEntity(TeamDTO teamDTO) {
        // Coach and other relationships will be set in the service layer
        return modelMapper.map(teamDTO, Team.class);
    }
}
