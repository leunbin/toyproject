package com.example.todolist.controller;

import com.example.todolist.entity.Todolist;
import com.example.todolist.service.TodolistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todos") // 베이스 경로
@RequiredArgsConstructor
public class TodolistController {
    private final TodolistService service;

    //메인목록
    @GetMapping
    public String list(Model model){
        model.addAttribute("todos", service.findAll());
        return "todo-list";
    }

    //완료미완료
    @GetMapping("/done/{done}")
    public String doneList(Model model, @PathVariable Boolean done){
        model.addAttribute("todos", service.findByDone(done));
        model.addAttribute("filter",done);
        return "todo-list";
    }

    //키워드 검색
    @GetMapping("/search")
    public String search(@RequestParam String s, Model model){
        model.addAttribute("todos", service.searchByTitle(s));
        model.addAttribute("search", s);
        return "todo-list";
    }

    //최근 한개
    @GetMapping("/latest")
    public String latest(Model model){
        model.addAttribute("todos", service.findLatest());
        return "todo-list";
    }

    //생성
    @PostMapping
    public String create(@RequestParam String title){
        service.create(title);
        return "redirect:/todos";
    }

    //done 토글
    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id){
        service.toggleDone(id);
        return "redirect:/todos";
    }

    //delete
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        service.delete(id);
        return "redirect:/todos";
    }

}
