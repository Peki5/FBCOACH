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

    public List<PlayerMatchStats> getTeamRostersByTeamId(Long teamId) {
        return playerMatchStatsRepository.findByTeamId_IdTeam(teamId);
    }

    public List<PlayerMatchStats> getTeamRostersByPlayerId(Long playerId) {
        return playerMatchStatsRepository.findByPlayerId_IdPlayer(playerId);
    }

    @Override
    public PlayerMatchStats createTeamRoster(PlayerMatchStats playerMatchStats) {
        return playerMatchStatsRepository.save(playerMatchStats);
    }

    @Override
    public PlayerMatchStats updateTeamRoster(Long id, PlayerMatchStats playerMatchStats) {
        playerMatchStats.setIdTeamRoster(id);
        return playerMatchStatsRepository.save(playerMatchStats);
    }

    @Override
    public void deleteTeamRoster(Long id) {
        playerMatchStatsRepository.deleteById(id);
    }
}
