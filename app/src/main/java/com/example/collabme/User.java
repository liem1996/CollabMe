package com.example.collabme;

public class User {
    private String sex,username;
    private String age, followers, numOfPosts;
    private boolean company, influencer;
    private String[] professions, platforms;
    private String password;
    String Email;

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return Email;
    }

    public String getUsername() {
        return username;
    }

    public String getAge() {
        return age;
    }

    public String getFollowers() {
        return followers;
    }

    public String getNumOfPosts() {
        return numOfPosts;
    }

    public String[] getPlatforms() {
        return platforms;
    }

    public String[] getProfessions() {
        return professions;
    }

    public String getSex() {
        return sex;
    }

    public boolean getCompany(){return company;}

    public boolean getInfluencer(){return influencer;}

    public User(String sex,String password, String Email, String username, String age, String followers, String numOfPosts, boolean company, boolean influencer, String[] professions, String[] platforms) {
        this.sex = sex;
        this.username = username;
        this.age = age;
        this.followers = followers;
        this.numOfPosts = numOfPosts;
        this.company = company;
        this.influencer = influencer;
        this.professions = professions;
        this.platforms = platforms;
        this.password=password;
        this.Email = Email;
    }
}
