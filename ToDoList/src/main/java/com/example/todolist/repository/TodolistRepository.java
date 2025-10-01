package com.example.todolist.repository;

import com.example.todolist.entity.Todolist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodolistRepository extends JpaRepository<Todolist, Long> {

    //완료 여부로 조회
    List<Todolist> findByDone(Boolean done);

    //제목 키워드로 조회
    List<Todolist> findByTitleContainingIgnoreCase(String keyword);

    //가장 최근에 생성된 항목 1개
    Optional<Todolist> findTopByOrderByCreatedAtDesc();



}
