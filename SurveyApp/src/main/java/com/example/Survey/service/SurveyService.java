package com.example.Survey.service;

import com.example.Survey.entity.Question;
import com.example.Survey.entity.Response;
import com.example.Survey.entity.Survey;
import com.example.Survey.repository.QuestionRepository;
import com.example.Survey.repository.ResponseRepository;
import com.example.Survey.repository.SurveyRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SurveyService {
    private final QuestionRepository questionRepository;
    private final ResponseRepository responseRepository;
    private final SurveyRepository surveyRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Map<String, Object> parseAnswerSafely(String answerJson) {
        try{
            if (answerJson == null || answerJson.isBlank()) return Collections.emptyMap();
            return objectMapper.readValue(answerJson, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e){
            return Collections.emptyMap();
        }
    }

    private String normalize(String s) {
        return s == null ? "" : s.trim().toLowerCase().replaceAll("\\s+", " ");
    }

    private void inc(Map<Long, Map<String, Integer>> counts, Long qid, String key){
        counts.computeIfAbsent(qid, k->new HashMap<>());
        Map<String, Integer> inner = counts.get(qid);
        inner.put(key, inner.getOrDefault(key, 0)+1);
    }

    //설문 생성
    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    @Transactional(readOnly = true)
    public List<Survey> getSurveys() {
        System.out.println(surveyRepository.findAll());
        return surveyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Survey getSurvey(Long surveyId){
        return surveyRepository.findById(surveyId).orElseThrow(()->new IllegalArgumentException("Survey not found"));
    }

    public void deleteSurvey(Long surveyId){
        surveyRepository.deleteById(surveyId);
    }

    //질문
    public Question addQuestion(Question q){
        return questionRepository.save(q);
    }

    @Transactional(readOnly = true)
    public List<Question> getQuestions(Long surveyId){
        return questionRepository.findBySurveyId(surveyId);
    }

    public void deleteQuestion(Long questionId){
        questionRepository.deleteById(questionId);
    }

    public Response submitResponse(Response r){
        return responseRepository.save(r);
    }

    public List<Response> getResponses(Long surveyId){
        return responseRepository.findBySurveyId(surveyId);
    }

    public void deleteResponse(Long responseId) {
        responseRepository.deleteById(responseId);
    }

    public Map<Long, LinkedHashMap<String, Integer>> aggregateBySurvey(Long surveyId) {
        List<Question> questions = questionRepository.findBySurveyId(surveyId);
        Set<String> qids = questions.stream().map(q-> String.valueOf(q.getId())).collect(Collectors.toSet());

        List<Response> responses = responseRepository.findBySurveyId(surveyId);
        Map<Long, Map<String, Integer>> counts = new HashMap<>();

        for (Response r : responses) {
            Map<String, Object> ansMap = parseAnswerSafely(r.getAnswers());
            for (String qid : qids) {
                if (!ansMap.containsKey(qid)) continue;
                Object raw = ansMap.get(qid);

                if (raw instanceof String s) {
                    String norm = normalize(s);
                    inc(counts, Long.valueOf(qid), norm);
                } else if (raw != null) {
                    String norm = normalize(String.valueOf(raw));
                    inc(counts, Long.valueOf(qid), norm);
                }
            }
        }
        Map<Long, LinkedHashMap<String, Integer>> sorted = new LinkedHashMap<>();
        for (Map.Entry<Long, Map<String, Integer>> e: counts.entrySet()) {
            LinkedHashMap<String, Integer> ordered =
                    e.getValue().entrySet().stream().sorted((a,b)->Integer.compare(b.getValue(),a.getValue()))
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    Map.Entry::getValue,
                                    (a,b)->a,
                                    LinkedHashMap::new
                            ));
            sorted.put(e.getKey(), ordered);
        }
        return sorted;
    }

    public Map<Long, String> questionTextMap(Long surveyId) {
        return questionRepository.findBySurveyId(surveyId).stream().collect(Collectors.toMap(Question::getId, Question::getText));
    }
}
