package com.example.collabme.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Offer implements Parcelable{
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
    @SerializedName("Isdelete")
    @Expose
    private boolean delete;

    @SerializedName("Image")
    @Expose
    String  image;

    public String[] getMediaContent() {
        return MediaContent;
    }

    public void setMediaContent(String[] mediaContent) {
        MediaContent = mediaContent;
    }

    @SerializedName("MediaContent")
    @Expose
    String[]  MediaContent;

    protected Offer(Parcel in) {
        description = in.readString();
        headline = in.readString();
        finishDate = in.readString();
        price = in.readString();
        idOffer = in.readString();
        status = in.readString();
        profession = in.createStringArray();
        user = in.readString();
        users = in.createStringArray();
        delete = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(headline);
        dest.writeString(finishDate);
        dest.writeString(price);
        dest.writeString(idOffer);
        dest.writeString(status);
        dest.writeStringArray(profession);
        dest.writeString(user);
        dest.writeStringArray(users);
        dest.writeByte((byte) (delete ? 1 : 0));
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

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


    public void setStatus(String status) {
        this.status = status;
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

    public Offer(String description, String headline, String finishDate, String price, String idOffer, String status, String[] profession, String user) {
        this.description = description;
        this.idOffer = idOffer;
        this.headline = headline;
        this.finishDate = finishDate;
        this.price = price;
        this.status = status;
        this.profession = profession;
        this.user = user;



    }




    public static Offer create(Map<String, Object> json) {
        String description = (String) json.get("Description");
        String idOffer = (String) json.get("IdOffer");
        String headline = (String) json.get("Headline");
        String finishDate = (String) json.get("FinishDate");
        String price = (String) json.get("Price");
        String status = (String) json.get("Status");
        String[] profession =(String[]) json.get("Profession");
        String user = (String) json.get("User");
        String [] users = (String[]) json.get("Users");
        String bitmap = (String) json.get("Image");
        boolean delete = (boolean) json.get("Isdelete");
        String[] MediaContent =(String[]) json.get("MediaContent");
        Offer offer = new Offer(description,headline,finishDate,price,idOffer,status,profession,user);
        offer.setdelete(delete);
        offer.setUsers(users);
        offer.setImage(bitmap);
        offer.setMediaContent(MediaContent);

        return offer;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("Description",description);
        json.put("HeadLine",headline);
        json.put("FinishDate",finishDate);
        json.put("Price",price);
        json.put("IdOffer", idOffer);
        json.put("Status", status);
        json.put("Profession", profession);
        json.put("User",user);
        json.put("Isdelete", delete);
        json.put("Users", users);
        json.put("Image", image);
        json.put("MediaContent", MediaContent);
        return json;
    }


}