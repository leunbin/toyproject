package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AnswerForm {
    @Min(0)
    private int choiceIndex; //라디오버튼 선택값
}
