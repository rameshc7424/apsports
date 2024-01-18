package com.ap.sports;

import com.ap.sports.controller.PlayerController;
import com.ap.sports.dto.PlayerDto;
import com.ap.sports.exceptions.PlayerNotFoundException;
import com.ap.sports.exceptions.SportNotFoundException;
import com.ap.sports.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @Test
    void testGetPlayersWithoutSports() {
        // Arrange
        List<PlayerDto> playersWithoutSports = Collections.singletonList(new PlayerDto("john@example.com", "John"));
        when(playerService.getPlayersWithoutSports()).thenReturn(playersWithoutSports);

        // Act
        ResponseEntity<List<PlayerDto>> response = playerController.getPlayersWithoutSports();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(playersWithoutSports, response.getBody());
    }

    @Test
    void testGetPlayersWithoutSports_Exception() {
        // Arrange
        when(playerService.getPlayersWithoutSports()).thenThrow(new RuntimeException("Something went wrong"));

        // Act
        ResponseEntity<List<PlayerDto>> response = playerController.getPlayersWithoutSports();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testUpdatePlayerSports() {
        // Arrange
        String email = "john@example.com";
        List<String> sportNames = Collections.singletonList("Tennis");

        // Act
        ResponseEntity<String> response = playerController.updatePlayerSports(email, sportNames);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Player's sports updated successfully", response.getBody());
    }

    @Test
    void testUpdatePlayerSports_PlayerNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        List<String> sportNames = Collections.singletonList("Tennis");
        when(playerService.updatePlayerSports(eq(email), anyList())).thenThrow(new PlayerNotFoundException());

        // Act
        ResponseEntity<String> response = playerController.updatePlayerSports(email, sportNames);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Player not found", response.getBody());
    }

    @Test
    void testUpdatePlayerSports_SportNotFound() {
        // Arrange
        String email = "john@example.com";
        List<String> sportNames = Collections.singletonList("NonexistentSport");
        when(playerService.updatePlayerSports(eq(email), anyList())).thenThrow(new SportNotFoundException());

        // Act
        ResponseEntity<String> response = playerController.updatePlayerSports(email, sportNames);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("One or more sports not found", response.getBody());
    }

    @Test
    void testUpdatePlayerSports_Exception() {
        // Arrange
        String email = "john@example.com";
        List<String> sportNames = Collections.singletonList("Tennis");
        when(playerService.updatePlayerSports(eq(email), anyList())).thenThrow(new RuntimeException("Something went wrong"));

        // Act
        ResponseEntity<String> response = playerController.updatePlayerSports(email, sportNames);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to update player's sports", response.getBody());
    }
}
