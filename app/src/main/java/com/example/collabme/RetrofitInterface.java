
package com.example.collabme;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @GET("/users/getUser/{username}")
    Call<User> getUser(@Path("username") String username,@Header("authorization") String token);

    @POST("/auth/login")
    Call<tokenrespone> executeLogin(@Body HashMap<String, String> map);

    @POST("/auth/logout")
    Call<Void> excutelogout(@Header("authorization") String token);

    @POST("/auth/register")
    Call<Void> executeSignup(@Body HashMap<String, Object> map);

    @POST("/offer/addNewOffer")
    Call<Offer> executenewOffer(@Body HashMap<String, Object> map, @Header("authorization") String token);


    @GET("/offer/getOfferById/{id}")
    Call<Offer> getOfferById(@Path("id") String offerId,@Header("authorization") String token);

    @POST("/offer/editOffer/{id}")
    Call<Void> editOffer(@Path("id") String offerId,@Header("authorization") String token, @Body Map<String, Object> newOffer);


}