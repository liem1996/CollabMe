package com.example.collabme.objects;
/**
 *
 * the tocken response object  -object for the create  of the tockens you get from the server in every connection
 * for creating tocken response there is a constructor with the fields
 * there is get and set for every field
 * getting the acsses tocken and the refresh tocken and setting them
 *
 */
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
