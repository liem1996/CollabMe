package com.example.collabme;

public class User {
    private String sex,username;
    private int age, followers, numOfPosts;
    private boolean company, influencer;
    private String[] professions, platforms;


    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public int getFollowers() {
        return followers;
    }

    public int getNumOfPosts() {
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

}
