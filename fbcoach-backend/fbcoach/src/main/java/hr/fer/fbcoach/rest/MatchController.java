package hr.fer.fbcoach.rest;

import hr.fer.fbcoach.model.Match;
import hr.fer.fbcoach.model.TacticsApplication;
import hr.fer.fbcoach.model.PlayerMatchStats;
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
@CrossOrigin(origins = "http://localhost:5173")
public class MatchController {

    private final MatchService matchService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    public List<MatchDTO> getAllMatches() {
        List<Match> matches = matchService.getAllMatches();
        return matches.stream()
                .sorted(Comparator.comparing(Match::getIdMatch))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("")
    public List<MatchDTO> getAllMatchesByTeamId(@RequestParam Long teamId) {
        List<Match> matches = matchService.getAllMatchesByTeamId(teamId);
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

    private MatchDTO convertToDto(Match match) {
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setIdMatch(match.getIdMatch());
        matchDTO.setDate(match.getDate());
        matchDTO.setOpponent(match.getOpponent());
        matchDTO.setLocation(match.getLocation());
        matchDTO.setResult(match.getResult());
        if (match.getTeamId() != null) {
            matchDTO.setTeamId(match.getTeamId().getIdTeam());
        }

        matchDTO.setPlayerMatchStatsIds(match.getPlayerMatchStats() != null ?
                match.getPlayerMatchStats().stream()
                        .map(PlayerMatchStats::getIdPlayerMatchStats)
                        .collect(Collectors.toList()) : new ArrayList<>());

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
