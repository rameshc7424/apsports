package com.ap.sports;

import com.ap.sports.controller.SportsController;
import com.ap.sports.dto.SportDto;
import com.ap.sports.exceptions.SportNotFoundException;
import com.ap.sports.service.SportsService;
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
class SportsControllerTest {

    @Mock
    private SportsService sportsService;

    @InjectMocks
    private SportsController sportsController;

    @Test
    void testGetSportsWithAssociatedPlayers() {
        // Arrange
        List<String> sportNames = Collections.singletonList("Tennis");
        List<SportDto> sportsWithPlayers = Collections.singletonList(new SportDto("Tennis", Collections.emptyList()));
        when(sportsService.getSportsWithAssociatedPlayers(eq(sportNames))).thenReturn(sportsWithPlayers);

        // Act
        ResponseEntity<List<SportDto>> response = sportsController.getSportsWithAssociatedPlayers(sportNames);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sportsWithPlayers, response.getBody());
    }

    @Test
    void testGetSportsWithAssociatedPlayers_Exception() {
        // Arrange
        List<String> sportNames = Collections.singletonList("NonexistentSport");
        when(sportsService.getSportsWithAssociatedPlayers(eq(sportNames)))
                .thenThrow(new SportNotFoundException());

        // Act
        ResponseEntity<List<SportDto>> response = sportsController.getSportsWithAssociatedPlayers(sportNames);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetSportsWithAssociatedPlayers_InternalServerError() {
        // Arrange
        List<String> sportNames = Collections.singletonList("Tennis");
        when(sportsService.getSportsWithAssociatedPlayers(eq(sportNames)))
                .thenThrow(new RuntimeException("Something went wrong"));

        // Act
        ResponseEntity<List<SportDto>> response = sportsController.getSportsWithAssociatedPlayers(sportNames);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testDeleteSport() {
        // Arrange
        Long sportId = 1L;

        // Act
        ResponseEntity<String> response = sportsController.deleteSport(sportId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Sport and associated data deleted successfully", response.getBody());
    }

    @Test
    void testDeleteSport_SportNotFound() {
        // Arrange
        Long sportId = 1L;
        when(sportsService.deleteSport(eq(sportId))).thenThrow(new SportNotFoundException());

        // Act
        ResponseEntity<String> response = sportsController.deleteSport(sportId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteSport_InternalServerError() {
        // Arrange
        Long sportId = 1L;
        when(sportsService.deleteSport(eq(sportId))).thenThrow(new RuntimeException("Something went wrong"));

        // Act
        ResponseEntity<String> response = sportsController.deleteSport(sportId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
