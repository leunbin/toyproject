package com.example.demo.domain;

import java.util.List;

//DB 없이 사용할 순수 DTO 레코드
public record Question(
        long id,
        String text,
        List<String> choices,
        int answerIndex
) { }
