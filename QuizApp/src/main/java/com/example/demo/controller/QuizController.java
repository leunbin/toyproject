package com.example.demo.controller;

import com.example.demo.dto.AnswerForm;
import com.example.demo.dto.ChoiceVM;
import com.example.demo.dto.QuizState;
import com.example.demo.service.QuizService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;
    private static final String STATE_KEY = "QUIZ_STATE";

    private QuizState getOrInit(HttpSession session){
        QuizState state = (QuizState) session.getAttribute(STATE_KEY);
        if (state == null){
            state = new QuizState();
            session.setAttribute(STATE_KEY, state);
        }
        return state;
    }

    @GetMapping
    public String renderCurrent(Model model, HttpSession session) {
        QuizState state = getOrInit(session);
        if (state.getCurrentIndex() >= quizService.size()){
            return "redirect:/quiz/result";
        }
        var question = quizService.get(state.getCurrentIndex());
        List<ChoiceVM> opts = new ArrayList<>();
        for(int i = 0; i< question.choices().size();i++){
            opts.add(new ChoiceVM(i, question.choices().get(i)));
        }
        model.addAttribute("q", question);
        model.addAttribute("opts", opts);
        model.addAttribute("index", state.getCurrentIndex()+1);
        model.addAttribute("total", quizService.size());
        model.addAttribute("form", new AnswerForm());
        return "quiz";
    }

    @PostMapping("/answer")
    public String submitAnswer(@Valid @ModelAttribute("form") AnswerForm form, BindingResult bindingResult, HttpSession session) {
        QuizState state = getOrInit(session);

        if(state.getCurrentIndex() >= quizService.size()){
            return "redirect:/quiz/result";
        }

        if (bindingResult.hasErrors()){
            return "redirect:/quiz";
        }

        var q = quizService.get(state.getCurrentIndex());
        if(form.getChoiceIndex() == q.answerIndex()){
            state.addScore();
        }
        state.next();
        session.setAttribute(STATE_KEY, state);

        return(state.getCurrentIndex()>= quizService.size()) ? "redirect:/quiz/result" : "redirect:/quiz";
    }
    @GetMapping("/result")
    public String result(Model model, HttpSession session){
        QuizState state = getOrInit(session);
        model.addAttribute("score",state.getScore());
        model.addAttribute("total",quizService.size());
        return "result";
    }

    @PostMapping("/reset")
    public String reset(HttpSession session){
        session.removeAttribute(STATE_KEY);
        return "redirect:/quiz";
    }
}
