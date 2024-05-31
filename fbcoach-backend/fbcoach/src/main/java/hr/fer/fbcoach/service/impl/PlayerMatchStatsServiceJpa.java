package hr.fer.fbcoach.service.impl;

import hr.fer.fbcoach.model.PlayerMatchStats;
import hr.fer.fbcoach.repository.PlayerMatchStatsRepository;
import hr.fer.fbcoach.service.PlayerMatchStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerMatchStatsServiceJpa implements PlayerMatchStatsService {

    private final PlayerMatchStatsRepository playerMatchStatsRepository;

    public List<PlayerMatchStats> getPlayerMatchStatssByPlayerId(Long playerId) {
        return playerMatchStatsRepository.findByPlayerId_IdPlayer(playerId);
    }

    public List<PlayerMatchStats> getPlayerMatchStatssByMatchId(Long matchId) {
        return playerMatchStatsRepository.findByMatchId_IdMatch(matchId);
    }

    public PlayerMatchStats createPlayerMatchStats(PlayerMatchStats playerMatchStats) {
        return playerMatchStatsRepository.save(playerMatchStats);
    }

    public PlayerMatchStats updatePlayerMatchStats(Long id, PlayerMatchStats playerMatchStats) {
        playerMatchStats.setIdPlayerMatchStats(id);
        return playerMatchStatsRepository.save(playerMatchStats);
    }

    @Override
    public void deletePlayerMatchStats(Long id) {
        playerMatchStatsRepository.deleteById(id);
    }
}
