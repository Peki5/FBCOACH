package hr.fer.fbcoach.service.impl;

import hr.fer.fbcoach.model.Korisnik;
import hr.fer.fbcoach.model.Team;
import hr.fer.fbcoach.repository.TeamRepository;
import hr.fer.fbcoach.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TeamServiceJpa implements TeamService {

    private TeamRepository teamRepository;
    @Override
    public List<Team> listAllTeamsForSignedInCoach() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Korisnik loggedKorisnik) {
            return teamRepository.findByCoach(loggedKorisnik);
        }
        return null;
    }

    @Override
    public Team addTeam(Team team) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Korisnik loggedKorisnik) {
            team.setCoach(loggedKorisnik);
            return teamRepository.save(team);
        }
        return null;
    }

    @Override
    public Team editTeam(Long id, Team team) {
        Team existingTeam = teamRepository.findById(id).orElse(null);
        if (existingTeam != null) {
            existingTeam.setTeamName(team.getTeamName());
            existingTeam.setSeason(team.getSeason());
            return teamRepository.save(existingTeam);
        }
        return null;
    }

    @Override
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }
}
