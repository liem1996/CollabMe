package com.example.collabme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class tokenrespone {


    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("refreshToken")
    @Expose
    private String refreshToken;



    /**
     *
     * @return
     * The accessToken
     */
    public String getaccessToken() {
        return accessToken;
    }

    /**
     *
     * @param accessToken
     * The accessToken
     */
    public void setaccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     *
     * @return
     * The accessToken
     */
    public String getrefreshToken() {
        return refreshToken;
    }

    /**
     *
     * @param refreshToken
     * The Level
     */
    public void setrefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


}
