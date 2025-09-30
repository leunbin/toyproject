package com.example.demo.dto;

public class GenerateRequest {
    private int length;
    private boolean lower;
    private boolean upper;
    private boolean number;
    private boolean symbol;

    //기본 생성자
    public GenerateRequest() {
        this.length = 12;
        this.lower = true;
        this.upper = true;
        this.number = true;
        this.symbol = false;
    }

    //전체 파라미터 받는 생성자
    public GenerateRequest(int length, boolean lower, boolean upper, boolean number, boolean symbol) {
        this.length = length;
        this.lower = lower;
        this.upper = upper;
        this.number = number;
        this.symbol = symbol;
    }

    //Getter
    public int getLength() {return length;}

    public boolean getLower() {
        return lower;
    }

    public boolean getUpper() {
        return upper;
    }

    public boolean getNumber() {
        return number;
    }

    public boolean getSymbol() {
        return symbol;
    }

    //Setter

    public void setLength(int length) {
        this.length = length;
    }

    public void setLower(boolean lower) {
        this.lower = lower;
    }

    public void setUpper(boolean upper) {
        this.upper = upper;
    }

    public void setNumber(boolean number) {
        this.number = number;
    }

    public void setSymbol(boolean symbol) {
        this.symbol = symbol;
    }
}
