package com.ap.sports.controller;

import com.ap.sports.dto.PlayerDto;
import com.ap.sports.exceptions.PlayerNotFoundException;
import com.ap.sports.service.PlayerService;
import com.ap.sports.exceptions.SportNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/without-sports")
    public ResponseEntity<List<PlayerDto>> getPlayersWithoutSports() {
        try {
            List<PlayerDto> playersWithoutSports = playerService.getPlayersWithoutSports();
            return new ResponseEntity<>(playersWithoutSports, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{email}/sports")
    public ResponseEntity<String> updatePlayerSports(@PathVariable String email, @RequestBody List<String> sportNames) {
        try {
            playerService.updatePlayerSports(email, sportNames);
            return new ResponseEntity<>("Player's sports updated successfully", HttpStatus.OK);
        } catch (PlayerNotFoundException e) {
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
        } catch (SportNotFoundException e) {
            return new ResponseEntity<>("One or more sports not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update player's sports", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/players")
    public List<PlayerDto> getPaginatedPlayersWithSportsFilter(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String sportName) {

        return playerService.getPaginatedPlayersWithSportsFilter(page, sportName);
    }
}
