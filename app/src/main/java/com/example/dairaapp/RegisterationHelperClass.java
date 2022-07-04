package com.example.dairaapp;

public class RegisterationHelperClass {
    String eventName, participantName;

    public RegisterationHelperClass() {
    }

    public RegisterationHelperClass(String eventName, String participantName) {
        this.eventName = eventName;
        this.participantName = participantName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }
}
