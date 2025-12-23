package com.example.FileSharing.controller;

import com.example.FileSharing.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
public class FileController {
    private final FileService service;

    @GetMapping("/files")
    public String list(Model model){
        model.addAttribute("files", service.findAllFiles());
        return "files";
    }

    @PostMapping("/files")
    public String postFiles(@RequestParam("file")MultipartFile file){
        service.upload(file);
        return "redirect:/files";
    }

    @GetMapping("files/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id){
        return service.download(id);
    }
}
