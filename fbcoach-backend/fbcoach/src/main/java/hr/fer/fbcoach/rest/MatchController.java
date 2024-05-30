package hr.fer.fbcoach.rest;

import hr.fer.fbcoach.model.Match;
import hr.fer.fbcoach.model.Report;
import hr.fer.fbcoach.model.TacticsApplication;
import hr.fer.fbcoach.model.dto.MatchDTO;
import hr.fer.fbcoach.service.MatchService;
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
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public List<MatchDTO> getAllMatches() {
        List<Match> matches = matchService.getAllMatches();
        return matches.stream()
                .sorted(Comparator.comparing(Match::getIdMatch))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getMatchById(@PathVariable Long id) {
        return matchService.getMatchById(id)
                .map(match -> ResponseEntity.ok(convertToDto(match)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<MatchDTO> addMatch(@RequestBody MatchDTO matchDTO) {
        Match match = convertToEntity(matchDTO);
        Match createdMatch = matchService.addMatch(match);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdMatch));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<MatchDTO> updateMatch(@PathVariable Long id, @RequestBody MatchDTO matchDTO) {
        Match match = convertToEntity(matchDTO);
        return ResponseEntity.ok(convertToDto(matchService.updateMatch(id, match)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/teamRoster/{teamRosterId}")
    public List<MatchDTO> getMatchesByTeamRosterId(@PathVariable Long teamRosterId) {
        List<Match> matches = matchService.getMatchesByTeamRosterId(teamRosterId);
        return matches.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private MatchDTO convertToDto(Match match) {
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setIdMatch(match.getIdMatch());
        matchDTO.setDate(match.getDate());
        matchDTO.setOpponent(match.getOpponent());
        matchDTO.setLocation(match.getLocation());
        matchDTO.setResult(match.getResult());

        // Manually set teamRosterId and tacticsApplicationIds
        if (match.getTeamRosterId() != null) {
            matchDTO.setTeamRosterId(match.getTeamRosterId().getIdTeamRoster());
        }

        matchDTO.setTacticsApplicationIds(match.getTacticsApplications() != null ?
                match.getTacticsApplications().stream()
                        .map(TacticsApplication::getIdTacticsApplication)
                        .collect(Collectors.toList()) : new ArrayList<>());

        return matchDTO;
    }

    private Match convertToEntity(MatchDTO matchDTO) {
        return modelMapper.map(matchDTO, Match.class);
    }
}
