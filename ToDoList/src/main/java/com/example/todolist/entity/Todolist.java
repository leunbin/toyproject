package com.example.todolist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Todolist {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String title;
    @Column
    private Boolean done;
    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Todolist() {};

    public  Todolist(String title, Boolean done) {
        this.title = title;
        this.done = done;
    }

    public void setDone(boolean b) {
        this.done = b;
    }

    public boolean getDone() {
        return this.done;
    }
}
