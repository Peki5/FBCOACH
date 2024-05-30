package hr.fer.fbcoach.service.impl;

import hr.fer.fbcoach.model.Player;
import hr.fer.fbcoach.model.Team;
import hr.fer.fbcoach.model.exception.ResourceNotFoundException;
import hr.fer.fbcoach.repository.PlayerRepository;
import hr.fer.fbcoach.repository.TeamRepository;
import hr.fer.fbcoach.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerServiceJpa implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public List<Player> getAllPlayersByTeamId(Long teamId) {
        return playerRepository.findByTeams_IdTeam(teamId);
    }

    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public Player addPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Player updatePlayer(Long id, Player playerDetails) {
        return playerRepository.findById(id).map(player -> {
            player.setFirstname(playerDetails.getFirstname());
            player.setLastName(playerDetails.getLastName());
            player.setDateOfBirth(playerDetails.getDateOfBirth());
            player.setPosition(playerDetails.getPosition());
            player.setHeight(playerDetails.getHeight());
            player.setWeight(playerDetails.getWeight());
            return playerRepository.save(player);
        }).orElseThrow(() -> new ResourceNotFoundException("Player not found with id " + id));
    }

    public void deletePlayer(Long id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            List<Team> teams = player.getTeams();

            // Remove player from all teams
            for (Team team : teams) {
                team.getPlayers().remove(player);
            }

            // Save all teams to update the changes in the database
            teamRepository.saveAll(teams);

            // Delete the player from the repository
            playerRepository.deleteById(id);
        }
    }

    public void removePlayerFromTeam(Long playerId, Long teamId) {
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        Optional<Team> teamOptional = teamRepository.findById(teamId);

        if (playerOptional.isPresent() && teamOptional.isPresent()) {
            Player player = playerOptional.get();
            Team team = teamOptional.get();

            player.getTeams().remove(team);
            team.getPlayers().remove(player);

            playerRepository.save(player);
        }
    }

    public void addPlayerToTeam(Long playerId, Long teamId) {
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        Optional<Team> teamOptional = teamRepository.findById(teamId);

        if (playerOptional.isPresent() && teamOptional.isPresent()) {
            Player player = playerOptional.get();
            Team team = teamOptional.get();

            player.getTeams().add(team);
            team.getPlayers().add(player);

            playerRepository.save(player);
            teamRepository.save(team);
        }
    }


}
