package hr.fer.fbcoach.service;

import hr.fer.fbcoach.model.Team;

import java.util.List;

public interface TeamService {
    List<Team> listAllTeamsForSignedInCoach();

    Team addTeam(Team team);

    Team editTeam(Long id, Team team);

    void deleteTeam(Long id);

    Team getTeamById(Long id);
}
