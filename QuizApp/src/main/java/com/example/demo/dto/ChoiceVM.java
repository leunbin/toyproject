package com.example.demo.dto;

public record ChoiceVM(int index, String text) {
    public int index() {return index+1;}
}
