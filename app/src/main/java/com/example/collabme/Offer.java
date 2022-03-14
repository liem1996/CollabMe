package com.example.collabme;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
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
    private int price;
    @SerializedName("Coupon")
    @Expose
    private String  coupon;
    @SerializedName("IdOffer")
    @Expose
    private int idOffer;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Profession")
    @Expose
    private String[] profession;
    @SerializedName("User")
    @Expose
    private Object user;
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
    public int getIdOffer() {
        return idOffer;
    }
    /**
     *
     * @return
     * The price
     */
    public int getPrice() {
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
    public Object getUser() {
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


    public Offer(String description,String coupon, String headline, int price, int idOffer, String status, String[] profession, Object user, boolean intrestedVerify) {
        this.coupon = coupon;
        this.description = description;
        this.idOffer = idOffer;
        this.headline = headline;
        this.price = price;
        this.status = status;
        this.profession = profession;
        this.user = user;
        this.intrestedVerify = intrestedVerify;

    }


    public static Offer create(Map<String, Object> json) {
        String coupon = (String) json.get("coupon");
        String description = (String) json.get("description");
        int idOffer = (int) json.get("idOffer");
        String headline = (String) json.get("headline");
        int price = (int) json.get("price");
        String status = (String) json.get("status");
        String[] profession =(String[]) json.get("profession");
        Object user = (Object) json.get("user");
        boolean intrestedVerify = (boolean) json.get("intrestedVerify");

        Offer offer = new Offer(description,coupon,headline,price,idOffer,status,profession,user,intrestedVerify);

        return offer;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("Description",description);
        json.put("Coupon",coupon);
        json.put("Headline",headline);
        json.put("Price",price);
        json.put("IdOffer", idOffer);
        json.put("Status", status);
        json.put("Profession", profession);
        json.put("User",user);
        json.put("IntrestedVerify", intrestedVerify);
        return json;
    }
}
