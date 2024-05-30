package hr.fer.fbcoach.service;

import hr.fer.fbcoach.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    List<Player> getAllPlayers();
    Optional<Player> getPlayerById(Long id);
    Player addPlayer(Player player);
    Player updatePlayer(Long id, Player playerDetails);
    void deletePlayer(Long id);

}
