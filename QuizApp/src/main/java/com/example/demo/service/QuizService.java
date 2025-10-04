package com.example.demo.service;

import com.example.demo.domain.Question;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuizService {

    private final List<Question> questionList = new ArrayList<>();

    @PostConstruct
    void init(){
        questionList.add(new Question(1,"자바에서 List 구현체가 아닌 것은?", List.of("ArrayList","LinkedList", "HashMap","Vector"),2));
        questionList.add(new Question(2,"HTTP 404는 무엇을 의미?",List.of("성공","리다이렉트","클라이언트 에러","서버 에러"),2));
        questionList.add(new Question(3, "Spring의 DI 뜻은?",List.of("Data Injection","Dependency Injection","Dynamic Init","Domain Index"),1));

        shuffleChoices();
    }

    public int size(){return questionList.size();}
    public Question get(int index){ return questionList.get(index);}

    public List<Question> all() {return Collections.unmodifiableList(questionList);}

    public void shuffleChoices(){
        for(int i=0; i<questionList.size();i++){
            var q = questionList.get(i);
            var choices = new ArrayList<>(q.choices());
            int correct = q.answerIndex();
            String correctText = choices.get(correct);
            Collections.shuffle(choices);
            int newIdx = choices.indexOf(correctText);
            questionList.set(i, new Question(q.id(),q.text(),q.choices(),q.answerIndex()));
        }
    }
}
