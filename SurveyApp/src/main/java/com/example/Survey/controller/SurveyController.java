package com.example.Survey.controller;

import com.example.Survey.entity.Response;
import com.example.Survey.service.SurveyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/surveys")
public class SurveyController {
    private final SurveyService service;

    @GetMapping
    public String listSurveys(Model model){
        model.addAttribute("surveys", service.getSurveys());
        return "survey-list";
    }

    //설문 상세
    @GetMapping("/{id}")
    public String getSurvey(Model model, @PathVariable Long id){
        model.addAttribute("surey", service.getSurvey(id));
        model.addAttribute("questions", service.getQuestions(id));
        return "servey-form";
    }

    //응답
    @PostMapping("/{id}/submit")
    public String submitResponse(@PathVariable Long id, @RequestParam MultiValueMap<String, String> formData, ObjectMapper objectMapper) {
        Map<String, Object> normalized = new LinkedHashMap<>();
        formData.forEach((k,v) -> {
            if (v==null || v.isEmpty()) return;
            normalized.put(k, v.size() == 1 ? v.get(0) : v);
        });

        final String json;
        try{
            json = objectMapper.writeValueAsString(normalized);
        } catch (JsonProcessingException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "응답 데이터를 JSON으로 변환할 수 없습니다.",
                    e
            );
        }

        Response r = new Response();
        r.setSurveyId(id);
        r.setAnswers(json);
        service.submitResponse(r);

        return "redirect:/surveys/"+id+"/responses";
    }

    @GetMapping("/{id}/responses")
    public String getResponses(@PathVariable Long id, Model model){
        model.addAttribute("responses", service.getResponses(id));
        return "response-list";
    }
}
