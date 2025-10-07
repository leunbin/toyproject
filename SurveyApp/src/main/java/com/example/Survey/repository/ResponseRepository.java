package com.example.Survey.repository;

import com.example.Survey.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response,Long> {
    List<Response> findBySurveyId(Long surveyId);
}
