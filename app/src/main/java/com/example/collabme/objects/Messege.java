package com.example.collabme.objects;

public class Messege {

    private String nickname;
    private String message ;
    long createdAt;

    public  Messege(){

    }
    public Messege(String nickname, String message, long createdAt) {
        this.nickname = nickname;
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}