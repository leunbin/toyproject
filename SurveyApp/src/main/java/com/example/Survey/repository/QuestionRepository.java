package com.example.Survey.repository;

import com.example.Survey.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findBySurveyId(Long surveyId);
}
