package com.example.Survey.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long surveyId;

    @Column
    private String text;

    @Enumerated(EnumType.STRING)
    @Column
    private QuestionType type;
}
