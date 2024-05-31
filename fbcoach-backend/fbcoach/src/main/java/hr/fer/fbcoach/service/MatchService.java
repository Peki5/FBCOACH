package hr.fer.fbcoach.service;

import hr.fer.fbcoach.model.Match;

import java.util.List;
import java.util.Optional;

public interface MatchService {
    List<Match> getAllMatches();
    Optional<Match> getMatchById(Long id);
    Match addMatch(Match match);
    Match updateMatch(Long id, Match matchDetails);
    void deleteMatch(Long id);
    List<Match> getAllMatchesByTeamId(Long teamId);
}
