package com.example.collabme;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Offer {
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("HeadLine")
    @Expose
    private String headline;
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
    private String  userid;
    @SerializedName("IntrestedVerify")
    @Expose
    private boolean intrestedVerify;

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
     * The userid
     */
    public String getUser() {
        return userid;
    }
    /**
     *
     * @return
     * The intrestedVerify
     */
    public boolean getIntrestedVerify() {
        return intrestedVerify;
    }


    public Offer(String description,String coupon, String headline, String price, String idOffer, String status, String[] profession, String user, boolean intrestedVerify) {
        this.coupon = coupon;
        this.description = description;
        this.idOffer = idOffer;
        this.headline = headline;
        this.price = price;
        this.status = status;
        this.profession = profession;
        this.userid = user;
        this.intrestedVerify = intrestedVerify;

    }


    public static Offer create(Map<String, Object> json) {
        String coupon = (String) json.get("Coupon");
        String description = (String) json.get("Description");
        String idOffer = (String) json.get("IdOffer");
        String headline = (String) json.get("Headline");
        String price = (String) json.get("Price");
        String status = (String) json.get("Status");
        String[] profession =(String[]) json.get("Profession");
        String userid = (String) json.get("User");
        boolean intrestedVerify = (boolean) json.get("IntrestedVerify");

        Offer offer = new Offer(description,coupon,headline,price,idOffer,status,profession,userid,intrestedVerify);

        return offer;
    }
}