package com.ap.sports.service;

import com.ap.sports.repository.SportRepository;
import com.ap.sports.domain.PlayerSport;
import com.ap.sports.dto.SportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SportsService {

    @Autowired
    private SportRepository sportsRepository;

    public List<SportDto> getSportsWithAssociatedPlayers(List<String> sportNames) {
        // Implement the logic to retrieve sports with associated players from the repository
        // Use the SportDto class to represent the data to be returned
        // Handle any exceptions gracefully
        // ...

        // For example:
        List<PlayerSport> sports = sportsRepository.getSportsWithAssociatedPlayers(sportNames);
        return mapToSportDtoList(sports);
    }

    private List<SportDto> mapToSportDtoList(List<PlayerSport> sports) {
        // Implement the mapping logic from entities to DTOs
        // ...
        return null;
    }

    @Transactional
    public Long deleteSport(Long id) {
        sportsRepository.delete(id);
        return id;
    }
}
