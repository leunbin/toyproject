package com.example.URLShortener.service;

import com.example.URLShortener.domain.Link;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class URLShortenerService {
    private Map<String, Link> store = new HashMap<>();
    private String normalize(String url){
        if(url.startsWith("http://") || url.startsWith("https://")) return url;
        return "http://" + url;
    }
    private static final char[] BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final SecureRandom RNG = new SecureRandom();
    private static final int DEFAULT_LEN = 5;

    public String toBase62(int len) {
        char[] buf = new char[len];
        for(int i = 0; i<len; i++){
            buf[i] = BASE62_CHARS[RNG.nextInt(62)];
        }
        return new String(buf);
    }

    public String generateShortCode(int len){
        for (int i = 0; i<10_000;i++){
            String code = toBase62(len);
            if(!store.containsKey(code)) return code;
        }

        return toBase62(len + 1);
    }

    public String createShortCode(String originalUrl){
        if (originalUrl == null || originalUrl.isBlank()) {
            throw new IllegalArgumentException("original must not be null");
        }
        String normalized = normalize(originalUrl);

        String shortCode = generateShortCode(DEFAULT_LEN);
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
