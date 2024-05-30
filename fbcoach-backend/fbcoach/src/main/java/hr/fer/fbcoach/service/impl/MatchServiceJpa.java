package hr.fer.fbcoach.service.impl;

import hr.fer.fbcoach.model.Match;
import hr.fer.fbcoach.model.exception.ResourceNotFoundException;
import hr.fer.fbcoach.repository.MatchRepository;
import hr.fer.fbcoach.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchServiceJpa implements MatchService {

    private final MatchRepository matchRepository;

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Optional<Match> getMatchById(Long id) {
        return matchRepository.findById(id);
    }

    public Match addMatch(Match match) {
        return matchRepository.save(match);
    }

    public Match updateMatch(Long id, Match matchDetails) {
        return matchRepository.findById(id).map(match -> {
            match.setDate(matchDetails.getDate());
            match.setOpponent(matchDetails.getOpponent());
            match.setLocation(matchDetails.getLocation());
            match.setResult(matchDetails.getResult());
            return matchRepository.save(match);
        }).orElseThrow(() -> new ResourceNotFoundException("Match not found with id " + id));
    }

    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }

    public List<Match> getMatchesByTeamRosterId(Long teamRosterId) {
        return matchRepository.findByTeamRosterId_IdTeamRoster(teamRosterId);
    }
}
