package hr.fer.fbcoach.service;

import hr.fer.fbcoach.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    List<Player> getAllPlayers();
    List<Player> getAllPlayersByTeamId(Long teamId);
    Optional<Player> getPlayerById(Long id);
    Player addPlayer(Player player);
    Player updatePlayer(Long id, Player playerDetails);
    void deletePlayer(Long id);
    void removePlayerFromTeam(Long id, Long teamId);
    void addPlayerToTeam(Long playerId, Long teamId);
}
