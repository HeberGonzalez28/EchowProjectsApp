package com.example.echowprojectsapp.Models;

public class NotificationModel {
    private String title;
    private String body;

    public NotificationModel(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
