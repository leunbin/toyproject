package com.example.demo.controller;

import com.example.demo.dto.GenerateRequest;
import com.example.demo.dto.GenerateResponse;
import com.example.demo.service.PasswordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PasswordController {
    private final PasswordService passwordService;
    public PasswordController (PasswordService passwordService){
        this.passwordService = passwordService;
    }

    @GetMapping({"/", "/password"})
    public String page(Model model) {
        model.addAttribute("title", "Password Generator");
        model.addAttribute("req", new GenerateRequest());
        return "password";
    }

    @PostMapping("/password")
    public String generate(@ModelAttribute("req") GenerateRequest req, Model model){
        try{
            String pwd = passwordService.generate(
                    clamp(req.getLength(), 4, 64),
                    safe(req.getLower()), safe(req.getUpper()),
                    safe(req.getNumber()), safe(req.getSymbol())
            );
            model.addAttribute("title", "Password Generator");
            model.addAttribute("result", pwd);
            return "password";
        } catch (IllegalArgumentException e) {
            model.addAttribute("title", "Password Generator");
            model.addAttribute("error", e.getMessage());
            return "password";
        }
    }

    //REST API
    @PostMapping("/api/password")
    @ResponseBody
    public GenerateResponse api(@RequestBody GenerateRequest req) {
        String pwd = passwordService.generate(
                clamp(req.getLength(), 4, 64),
                safe(req.getLower()), safe(req.getUpper()),
                safe(req.getNumber()), safe(req.getSymbol())
            );
        return new GenerateResponse((pwd));
    }

    private static int clamp(Integer v, int min, int max){
        if(v == null) return 12;
        return Math.max(min, Math.min(max, v));
    }

    private static boolean safe(Boolean b) {return b != null && b;}
}
