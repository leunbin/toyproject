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

import java.util.*;

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
        model.addAttribute("survey", service.getSurvey(id));

        var questions = service.getQuestions(id);
        var qviews = new ArrayList<Map<String, Object>>();

        for (var q : questions){
            var m = new LinkedHashMap<String, Object>();
            m.put("id", q.getId());
            m.put("text",q.getText());
            m.put("isSingle",q.getType().name().equals("SINGLE"));
            m.put("isMulti", q.getType().name().equals("MULTI"));
            qviews.add(m);
        }
        model.addAttribute("questions", qviews);
        return "survey-form";
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
        // 1) 원본 응답
        model.addAttribute("responses", service.getResponses(id));

        // 2) 집계(Map<Long, Map<String,Integer>>) + 질문문구(Map<Long,String>)
        var stats = service.aggregateBySurvey(id);
        var qtext = service.questionTextMap(id);

        // 3) Mustache 친화적 구조로 변환: List<Map> 형태 (blocks)
        List<Map<String, Object>> blocks = new ArrayList<>();
        for (var entry : stats.entrySet()) {
            Long qid = entry.getKey();
            Map<String, Integer> inner = entry.getValue();

            // rows: [{label: "...", count: 3}, ...] 로 변환
            List<Map<String, Object>> rows = new ArrayList<>();
            for (var kv : inner.entrySet()) {
                rows.add(Map.of(
                        "label", kv.getKey(),
                        "count", kv.getValue()
                ));
            }
            // count 내림차순 정렬(선택)
            rows.sort((a, b) -> Integer.compare((int) b.get("count"), (int) a.get("count")));

            // 한 질문 블록
            blocks.add(Map.of(
                    "questionId", qid,
                    "questionText", qtext.getOrDefault(qid, "질문 " + qid),
                    "rows", rows,
                    "hasRows", !rows.isEmpty()
            ));
        }
        blocks.sort(Comparator.comparing(b -> (Long) b.get("questionId")));

        model.addAttribute("blocks", blocks);


        return "response-list";
    }

}
