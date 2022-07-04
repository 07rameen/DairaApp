package com.example.dairaapp;

public class MentorHelperClass {
    String name, cnic, contact, password, email, userLevel;

    public MentorHelperClass() {
    }

    public MentorHelperClass(String name, String cnic, String contact, String password, String email, String userLevel) {
        this.name = name;
        this.cnic = cnic;
        this.contact = contact;
        this.password = password;
        this.email = email;
        this.userLevel = userLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }
}
