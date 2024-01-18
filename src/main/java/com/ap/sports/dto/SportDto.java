package com.ap.sports.dto;

import com.ap.sports.dto.PlayerDto;

import java.util.List;

public class SportDto {

    private String name;
    private List<PlayerDto> players;

    public <T> SportDto(String tennis, List<T> list) {
    }

    // Constructors, getters, and setters
}
