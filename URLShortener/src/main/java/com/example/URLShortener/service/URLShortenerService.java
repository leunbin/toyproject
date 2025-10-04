package com.example.URLShortener.service;

import com.example.URLShortener.domain.Link;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class URLShortenerService {
    private Map<String, Link> store = new HashMap<>();
    private String normalize(String url){
        if(url.startsWith("http://") || url.startsWith("https://")) return url;
        return "http://" + url;
    }

    public String createShortCode(String originalUrl){
        if (originalUrl == null || originalUrl.isBlank()) {
            throw new IllegalArgumentException("original must not be null");
        }
        String normalized = normalize(originalUrl);

        String shortCode = Base64.getUrlEncoder().withoutPadding().encodeToString(originalUrl.getBytes(StandardCharsets.UTF_8));
        store.put(shortCode, new Link(normalized, shortCode));
        return shortCode;
    }

    public Integer getState(String shortCode) {
        if(shortCode == null || shortCode.isBlank()) {
            throw new IllegalArgumentException("short code must be needed.");
        }

        Link link = (Link) store.get(shortCode);
        if (link == null) {
            throw new IllegalArgumentException("unknown short code: " + shortCode);
        }
        return link.getHits();
    }

    public String clickLink(String shortCode){
        if(shortCode == null || shortCode.isBlank()) {
            throw new IllegalArgumentException("short code must be needed.");
        }

        Link link = (Link) store.get(shortCode);
        if(link == null){
            throw new IllegalArgumentException("unknown short code: " + shortCode);
        }
        link.incrementHits();
        return link.getOriginalUrl();
    }
}
