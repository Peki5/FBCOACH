package hr.fer.fbcoach.service.impl;

import hr.fer.fbcoach.model.TeamRoster;
import hr.fer.fbcoach.repository.TeamRosterRepository;
import hr.fer.fbcoach.service.TeamRosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamRosterServiceJpa implements TeamRosterService {

    private final TeamRosterRepository teamRosterRepository;

    public List<TeamRoster> getTeamRostersByTeamId(Long teamId) {
        return teamRosterRepository.findByTeamId_IdTeam(teamId);
    }

    public List<TeamRoster> getTeamRostersByPlayerId(Long playerId) {
        return teamRosterRepository.findByPlayerId_IdPlayer(playerId);
    }

    @Override
    public TeamRoster createTeamRoster(TeamRoster teamRoster) {
        return teamRosterRepository.save(teamRoster);
    }

    @Override
    public TeamRoster updateTeamRoster(Long id, TeamRoster teamRoster) {
        teamRoster.setIdTeamRoster(id);
        return teamRosterRepository.save(teamRoster);
    }

    @Override
    public void deleteTeamRoster(Long id) {
        teamRosterRepository.deleteById(id);
    }
}
