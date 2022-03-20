package com.example.collabme.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Offer {
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("HeadLine")
    @Expose
    private String headline;
    @SerializedName("FinishDate")
    @Expose
    private String finishDate;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("Coupon")
    @Expose
    private String  coupon;
    @SerializedName("IdOffer")
    @Expose
    private String idOffer;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Profession")
    @Expose
    private String[] profession;
    @SerializedName("User")
    @Expose
    private String user;
    @SerializedName("Users")
    @Expose
    private String [] users;
    @SerializedName("IntrestedVerify")
    @Expose
    private boolean intrestedVerify;
    @SerializedName("Isdelete")
    @Expose
    private boolean delete;

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }
    /**
     *
     * @return
     * The headline
     */
    public String getHeadline() {
        return headline;
    }
    /**
     *
     * @return
     * The finishDate
     */
    public String getFinishDate() {
        return finishDate;
    }
    /**
     *
     * @return
     * The coupon
     */
    public String getCoupon() {
        return coupon;
    }
    /**
     *
     * @return
     * The idOffer
     */
    public String getIdOffer() {
        return idOffer;
    }
    /**
     *
     * @return
     * The price
     */
    public String getPrice() {
        return price;
    }
    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }
    /**
     *
     * @return
     * The profession
     */
    public String[] getProfession() {
        return profession;
    }
    /**
     *
     * @return
     * The user
     */
    public String getUser() {
        return user;
    }
     /**
     *
     * @return
     * The intrestedVerify
     */
    public boolean getIntrestedVerify() {
        return intrestedVerify;
    }

    /**
     *
     * @return
     * The delete
     */
    public boolean getdelete() {
        return delete;
    }

    /**
     *
     * @param delete
     * The isdelete
     */
    public void setdelete(boolean delete) {
        this.delete = delete;
    }

    /**
     *
     * @return
     * The users
     */
    public String[] getUsers() {
        return users;
    }


    public List<String> setusersandadd(String[] users, String newuser) {
        int openArray = 0;

        List<String> useres2 = new LinkedList<String>();
        useres2 = Arrays.asList(users);
        List<String> arraylist = new ArrayList<>(useres2);
        if(!arraylist.contains(newuser)){
            arraylist.add(newuser);
        }
        return arraylist;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }

    public Offer(String description, String coupon, String headline, String finishDate, String price, String idOffer, String status, String[] profession, String user, boolean intrestedVerify) {
        this.coupon = coupon;
        this.description = description;
        this.idOffer = idOffer;
        this.headline = headline;
        this.finishDate = finishDate;
        this.price = price;
        this.status = status;
        this.profession = profession;
        this.user = user;
        this.intrestedVerify = intrestedVerify;


    }




    public static Offer create(Map<String, Object> json) {
        String coupon = (String) json.get("Coupon");
        String description = (String) json.get("Description");
        String idOffer = (String) json.get("IdOffer");
        String headline = (String) json.get("Headline");
        String finishDate = (String) json.get("FinishDate");
        String price = (String) json.get("Price");
        String status = (String) json.get("Status");
        String[] profession =(String[]) json.get("Profession");
        String user = (String) json.get("User");
        String [] users = (String[]) json.get("Users");
        boolean intrestedVerify = (boolean) json.get("IntrestedVerify");
        boolean delete = (boolean) json.get("Isdelete");
        Offer offer = new Offer(description,coupon,headline,finishDate,price,idOffer,status,profession,user,intrestedVerify);
        offer.setdelete(delete);
        offer.setUsers(users);

        return offer;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("Description",description);
        json.put("Coupon",coupon);
        json.put("HeadLine",headline);
        json.put("FinishDate",finishDate);
        json.put("Price",price);
        json.put("IdOffer", idOffer);
        json.put("Status", status);
        json.put("Profession", profession);
        json.put("User",user);
        json.put("IntrestedVerify", intrestedVerify);
        json.put("Isdelete", delete);
        json.put("Users", users);
        return json;
    }
}