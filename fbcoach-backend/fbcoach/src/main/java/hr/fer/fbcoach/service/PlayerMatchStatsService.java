package hr.fer.fbcoach.service;

import hr.fer.fbcoach.model.PlayerMatchStats;

import java.util.List;

public interface PlayerMatchStatsService {

    List<PlayerMatchStats> getPlayerMatchStatssByPlayerId(Long playerId);

    List<PlayerMatchStats> getPlayerMatchStatssByMatchId(Long matchId);

    PlayerMatchStats createPlayerMatchStats(PlayerMatchStats playerMatchStats);

    PlayerMatchStats updatePlayerMatchStats(Long id, PlayerMatchStats playerMatchStats);

    void deletePlayerMatchStats(Long id);
}
