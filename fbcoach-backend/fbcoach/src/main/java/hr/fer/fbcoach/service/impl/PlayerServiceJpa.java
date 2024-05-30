package hr.fer.fbcoach.service.impl;

import hr.fer.fbcoach.model.Player;
import hr.fer.fbcoach.model.exception.ResourceNotFoundException;
import hr.fer.fbcoach.repository.PlayerRepository;
import hr.fer.fbcoach.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerServiceJpa implements PlayerService {

    private final PlayerRepository playerRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
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
        playerRepository.deleteById(id);
    }
}
