package com.example.URLShortener.controller;

import com.example.URLShortener.dto.InputForm;
import com.example.URLShortener.service.URLShortenerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@AllArgsConstructor
public class URLShortenerController {
    private final URLShortenerService urlShortenerService;

    @GetMapping("/")
    public String renderForm(Model model){
        model.addAttribute("form", new InputForm());
        return "input";
    }

    @PostMapping("/shorten")
    public String create(@ModelAttribute("form") InputForm form, Model model){
        String shortCode = urlShortenerService.createShortCode(form.getOriginalUrl());
        String shortUrl = "http://localhost:8080/r/"+shortCode;
        model.addAttribute("shortUrl", shortUrl);
        model.addAttribute("shortCode", shortCode);
        model.addAttribute("original", form.getOriginalUrl());
        return "result";
    }

    @GetMapping("/r/{code:[0-9A-Za-z_-]+}")
    public ResponseEntity<Void> redirect(@PathVariable String code){
        String originalUrl = urlShortenerService.clickLink(code);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
    }

    @GetMapping("/state/{code}")
    public String statepage(@PathVariable String code, Model model){
        int hits = urlShortenerService.getState(code);
        model.addAttribute("code", code);
        model.addAttribute("hits", hits);
        return "state";
    }

    @GetMapping("/health")
    @ResponseBody
    public String health() { return "ok"; }


}
