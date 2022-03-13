package com.example.collabme;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class User {
    @SerializedName("Sex")
    @Expose
    private String sex;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Followers")
    @Expose
    private String followers;
    @SerializedName("NumberOfPosts")
    @Expose
    private String  numOfPosts;
    @SerializedName("Age")
    @Expose
    private String age;
    @SerializedName("Influencer")
    @Expose
    private boolean influencer=false;
    @SerializedName("Company")
    @Expose
    private boolean company=false;
    @SerializedName("Profession")
    @Expose
    private String[] professions;
    @SerializedName("Platform")
    @Expose
    private String[]  platforms;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("Email")
    @Expose
    String Email;


    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }
    /**
     *
     * @return
     * The Email
     */
    public String getEmail() {
        return Email;
    }
    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }
    /**
     *
     * @return
     * The age
     */
    public String getAge() {
        return age;
    }
    /**
     *
     * @return
     * The followers
     */
    public String getFollowers() {
        return followers;
    }
    /**
     *
     * @return
     * The numOfPosts
     */
    public String getNumOfPosts() {
        return numOfPosts;
    }
    /**
     *
     * @return
     * The platforms
     */
    public String[] getPlatforms() {
        return platforms;
    }
    /**
     *
     * @return
     * The professions
     */
    public String[] getProfessions() {
        return professions;
    }
    /**
     *
     * @return
     * The sex
     */
    public String getSex() {
        return sex;
    }
    /**
     *
     * @return
     * The company
     */
    public boolean getCompany(){return company;}
    /**
     *
     * @return
     * The influencer
     */
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

    public static User create(Map<String, Object> json) {
        String sex = (String) json.get("sex");
        String username = (String) json.get("email");
        String age = (String) json.get("age");
        String followers = (String) json.get("followers");
        String numOfPosts = (String) json.get("numOfPosts");
        boolean company = (boolean) json.get("company");
        boolean influencer = (boolean) json.get("influencer");
        String professions []= (String[]) json.get("professions");
        String platforms []= (String[]) json.get("platforms");
        String password = (String) json.get("password");
        String Email = (String) json.get("Email");
        User user = new User(sex,password,Email,username,age,followers,numOfPosts,company,influencer,professions,platforms);

        return user;
    }
}
