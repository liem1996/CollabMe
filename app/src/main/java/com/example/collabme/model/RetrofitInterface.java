
package com.example.collabme.model;

import java.util.HashMap;
import java.util.List;
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

    @GET("/users/getUserById/{id}")
    Call<User> getUserById(@Path("id") String id,@Header("authorization") String token);

    @POST("/auth/login")
    Call<tokenrespone> executeLogin(@Body HashMap<String, String> map);

    @POST("/auth/logout")
    Call<Void> excutelogout(@Header("authorization") String token);

    @POST("/auth/register")
    Call<Void> executeSignup(@Body HashMap<String, Object> map);

    @POST("/users/editUser/{username}")
    Call<User> editUser(@Path("username") String username,@Header("authorization") String token, @Body Map<String, Object> newUser);


    @POST("/offer/addNewOffer")
    Call<Offer> executenewOffer(@Body HashMap<String, Object> map,@Header("authorization") String token);


    @GET("/offer/getOfferById/{id}")
    Call<Offer> getOfferById(@Path("id") String offerId,@Header("authorization") String token);

    @GET("/offer/getoffers")
    Call<List<Offer>> getoffers(@Header("authorization") String token);


    @POST("/offer/editOffer/{id}")
    Call<Offer> editOffer(@Path("id") String offerId,@Header("authorization") String token, @Body Map<String, Object> newOffer);



}