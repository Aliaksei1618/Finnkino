package com.example.finnkino.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class User {

    public String userId;
    public String name;
    public String email;
    public String avatar;
    public String theme;
    public String language;
    public List<String> userEventsList = new ArrayList<>();

    public User() {
    }

    public User(String email, String userId) {
        this.userId = userId;
        this.email = email;
        this.name = email.split("@")[0];
        this.avatar = "";
        this.theme = "light";
        this.language = Locale.getDefault().getLanguage();
        this.userEventsList.add("");
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getUserEventsList() {
        return userEventsList;
    }

    public void setUserEventsList(List<String> userEventsList) {
        this.userEventsList = userEventsList;
    }
}
