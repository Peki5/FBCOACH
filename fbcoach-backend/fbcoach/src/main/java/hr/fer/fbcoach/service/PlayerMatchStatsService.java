package hr.fer.fbcoach.service;

import hr.fer.fbcoach.model.PlayerMatchStats;

import java.util.List;

public interface PlayerMatchStatsService {
    List<PlayerMatchStats> getTeamRostersByTeamId(Long teamId);

    List<PlayerMatchStats> getTeamRostersByPlayerId(Long playerId);

    PlayerMatchStats createTeamRoster(PlayerMatchStats playerMatchStats);

    PlayerMatchStats updateTeamRoster(Long id, PlayerMatchStats playerMatchStats);

    void deleteTeamRoster(Long id);
}
