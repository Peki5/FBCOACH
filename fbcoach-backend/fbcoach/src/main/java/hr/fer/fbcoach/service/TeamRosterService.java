package hr.fer.fbcoach.service;

import hr.fer.fbcoach.model.TeamRoster;

import java.util.List;

public interface TeamRosterService {
    List<TeamRoster> getTeamRostersByTeamId(Long teamId);

    List<TeamRoster> getTeamRostersByPlayerId(Long playerId);

    TeamRoster createTeamRoster(TeamRoster teamRoster);

    TeamRoster updateTeamRoster(Long id, TeamRoster teamRoster);

    void deleteTeamRoster(Long id);
}
