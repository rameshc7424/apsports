package com.ap.sports.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Sport {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
