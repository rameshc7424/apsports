package com.ap.sports.controller;

import com.ap.sports.dto.SportDto;
import com.ap.sports.exceptions.SportNotFoundException;
import com.ap.sports.service.SportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SportsController {

    @Autowired
    private SportsService sportsService;

    @GetMapping("/sports")
    public ResponseEntity<List<SportDto>> getSportsWithAssociatedPlayers(@RequestParam List<String> sportNames) {
        try {
            List<SportDto> sportsWithPlayers = sportsService.getSportsWithAssociatedPlayers(sportNames);
            return new ResponseEntity<>(sportsWithPlayers, HttpStatus.OK);
        } catch(SportNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSport(@PathVariable Long id) {
        try {
            sportsService.deleteSport(id);
            return new ResponseEntity<>("Sport and associated data deleted successfully", HttpStatus.OK);
        } catch (SportNotFoundException e) {
            return new ResponseEntity<>("Sport not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete sport and associated data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
