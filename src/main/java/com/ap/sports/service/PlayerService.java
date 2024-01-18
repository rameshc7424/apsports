package com.ap.sports.service;

import com.ap.sports.exceptions.PlayerNotFoundException;
import com.ap.sports.repository.PlayerRepository;
import com.ap.sports.exceptions.SportNotFoundException;
import com.ap.sports.repository.SportRepository;
import com.ap.sports.domain.Player;
import com.ap.sports.domain.Sport;
import com.ap.sports.dto.PlayerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SportRepository sportRepository;

    public List<PlayerDto> getPlayersWithoutSports() {
        // Implement the logic to retrieve players without associated sports from the repository
        // Use the PlayerDto class to represent the data to be returned
        // Handle any exceptions gracefully
        // ...

        // For example:
        List<Player> playersWithoutSports = playerRepository.findPlayersWithoutSports();
        return mapToPlayerDtoList(playersWithoutSports);
    }

    private List<PlayerDto> mapToPlayerDtoList(List<Player> players) {
        // Implement the mapping logic from entities to DTOs
        // ...
        return null;
    }

    @Transactional
    public Player updatePlayerSports(String email, List<String> sportNames) {
        Player player = playerRepository.findByEmail(email);

        if (player == null) {
            throw new PlayerNotFoundException();
        }

        List<Sport> sports = sportRepository.findByNameIn(sportNames);

        if (sports.size() != sportNames.size()) {
            throw new SportNotFoundException();
        }

        player.setSports(sports);
        playerRepository.save(player);

        return player;
    }

    public List<PlayerDto> getPaginatedPlayersWithSportsFilter(int page, String sportName) {
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return playerRepository.findPaginatedPlayersWithSportsFilter(page, pageSize, sportName);
    }
}


