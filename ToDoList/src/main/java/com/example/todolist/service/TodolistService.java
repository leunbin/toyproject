package com.example.todolist.service;

import com.example.todolist.entity.Todolist;
import com.example.todolist.repository.TodolistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodolistService {

    private final TodolistRepository repo;

    //생성
    @Transactional
    public Todolist create(String title){
        Todolist t = new Todolist(title, false);
        return repo.save(t);
    };

    //전체 조회
    public List<Todolist> findAll() {
        return repo.findAll();
    }

    //완료, 미완료 필터
    public List<Todolist> findByDone(Boolean done){
        return repo.findByDone(done);
    }

    //제목 키워드 검색
    public List<Todolist> searchByTitle(String title){
        return repo.findByTitleContainingIgnoreCase(title);
    }

    //최근 1개
    public Todolist findLatest() {
        return repo.findTopByOrderByCreatedAtDesc().orElse(null);
    }

    //완료토글
    @Transactional
    public Todolist toggleDone(Long id){
        Todolist t = repo.findById(id).orElseThrow();
        t.setDone(!t.getDone());
        return t;
    }

    //삭제
    @Transactional
    public void delete(Long id){
        repo.deleteById(id);
    }
}
