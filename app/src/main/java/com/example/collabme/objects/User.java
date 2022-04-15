package com.example.collabme.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.lights.LightsManager;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    private String numOfPosts;
    @SerializedName("Age")
    @Expose
    private String age;
    @SerializedName("Influencer")
    @Expose
    private boolean influencer = false;
    @SerializedName("Company")
    @Expose
    private boolean company = false;
    @SerializedName("Profession")
    @Expose
    private String[] professions;
    @SerializedName("Platform")
    @Expose
    private String[] platforms;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("Email")
    @Expose
    String Email;
    @SerializedName("Image")
    @Expose
    String image;
    @SerializedName("RejectedOffers")
    @Expose
    ArrayList<String> rejectedOffers;


    /**
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return The Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return The age
     */
    public String getAge() {
        return age;
    }

    /**
     * @return The followers
     */
    public String getFollowers() {
        return followers;
    }

    /**
     * @return The numOfPosts
     */
    public String getNumOfPosts() {
        return numOfPosts;
    }

    /**
     * @return The platforms
     */
    public String[] getPlatforms() {
        return platforms;
    }

    /**
     * @return The professions
     */
    public String[] getProfessions() {
        return professions;
    }

    /**
     * @return The sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @return The company
     */
    public boolean getCompany() {
        return company;
    }

    /**
     * @return The influencer
     */
    public ArrayList<String> getRejectedOffers() {
        return rejectedOffers;
    }

    /**
     * @return The influencer
     */
    public boolean getInfluencer() {
        return influencer;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String sex, String password, String Email, String username, String age, String followers, String numOfPosts, boolean company, boolean influencer, String[] professions, String[] platforms, ArrayList<String> rejectedOffers) {
        this.sex = sex;
        this.username = username;
        this.age = age;
        this.followers = followers;
        this.numOfPosts = numOfPosts;
        this.company = company;
        this.influencer = influencer;
        this.professions = professions;
        this.platforms = platforms;
        this.password = password;
        this.Email = Email;
        this.rejectedOffers = rejectedOffers;
    }

    public static User create(Map<String, Object> json) {
        String sex = (String) json.get("sex");
        String username = (String) json.get("email");
        String age = (String) json.get("age");
        String followers = (String) json.get("followers");
        String numOfPosts = (String) json.get("numOfPosts");
        boolean company = (boolean) json.get("company");
        boolean influencer = (boolean) json.get("influencer");
        String professions[] = (String[]) json.get("professions");
        String platforms[] = (String[]) json.get("platforms");
        String password = (String) json.get("password");
        String Email = (String) json.get("Email");
        ArrayList<String> rejectedOffers = (ArrayList<String>)(json.get("rejectedOffers"));

        User user = new User(sex, password, Email, username, age, followers, numOfPosts, company, influencer, professions, platforms, rejectedOffers);

        return user;
    }

    public HashMap<String, Object> tojson() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Username", username);
        map.put("Password", password);
        map.put("Email", Email);
        map.put("Sex", sex);
        map.put("Age", age);
        map.put("Followers", followers);
        map.put("NumberOfPosts", numOfPosts);
        map.put("Company", company);
        map.put("Influencer", influencer);
        map.put("Profession", professions);
        map.put("Platform", platforms);
        map.put("Image", image);
        map.put("RejectedOffers", rejectedOffers.toArray());

        return map;
    }

    /**
     * @return The Bitmap
     */
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Uri getImageUri(Bitmap inImage, Context context) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
