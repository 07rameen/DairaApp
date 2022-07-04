package com.example.dairaapp;

public class EventHelperClass {

    String name, mentor;

    public EventHelperClass() {
    }


    public EventHelperClass(String name, String mentor) {
        this.name = name;
        this.mentor = mentor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }
}
