package com.example.finnkino.model;

public class Schedule {

    //Show show;
    String color;
    String text;

    public Schedule(String color, String text) {
        this.color = color;
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
