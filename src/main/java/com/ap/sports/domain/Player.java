package com.ap.sports.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private int level;

    private int age;

    private String gender;

    public Player(String john) {
    }

    public void setSports(List<Sport> sports) {
    }
}
