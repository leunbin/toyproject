package com.example.URLShortener.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Link {
    private String originalUrl;
    private String shortCode;
    private LocalDateTime createdAt;
    private int hits;

    public Link(String originalUrl, String shortCode){
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.createdAt = LocalDateTime.now();
        this.hits = 0;
    }

    public void incrementHits() { this.hits++; }
}
