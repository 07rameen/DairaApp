package com.example.dairaapp;

public class SubEventHelperClass {
    String name, parentEvent, venue, date, time, fileName;

    public SubEventHelperClass(String name, String parentEvent, String venue, String date, String day, String fileName) {
        this.name = name;
        this.parentEvent = parentEvent;
        this.venue = venue;
        this.date = date;
        this.time = day;
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentEvent() {
        return parentEvent;
    }

    public void setParentEvent(String parentEvent) {
        this.parentEvent = parentEvent;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
