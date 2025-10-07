package com.example.Survey.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Survey {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;
}
