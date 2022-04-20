package com.example.collabme.objects;

public class Messege {

    private String from;
    private String message;
    private String to;
    private String createdAt;

    public  Messege(){

    }
    public Messege(String from,String to, String message, String createdAt) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}