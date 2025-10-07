package com.example.Survey.service;

import com.example.Survey.entity.Question;
import com.example.Survey.entity.Response;
import com.example.Survey.entity.Survey;
import com.example.Survey.repository.QuestionRepository;
import com.example.Survey.repository.ResponseRepository;
import com.example.Survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SurveyService {
    private final QuestionRepository questionRepository;
    private final ResponseRepository responseRepository;
    private final SurveyRepository surveyRepository;

    //설문 생성
    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    @Transactional(readOnly = true)
    public List<Survey> getSurveys() {
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
}
