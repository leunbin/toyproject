package com.example.HitLoggerTracker.controller;

import com.example.HitLoggerTracker.repository.HitRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class HitsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HitRepository hitRepository;

    @GetMapping("/hits")
    public String showRecentHits(Model model) {
        var hits = hitRepository.findTop100ByOrderByAtDesc();
        model.addAttribute("hits", hits);
        logger.info("Admin viewed recent hits: {} rows", hits.size());
        return "admin-hits";
    }
}
