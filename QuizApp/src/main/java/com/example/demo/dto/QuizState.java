package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QuizState {
    private int currentIndex = 0;
    private int score = 0;

    public void next() {currentIndex++;}
    public void addScore() {score++;}

}
