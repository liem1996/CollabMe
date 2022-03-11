
package com.example.collabme;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @GET("/users/getUser/{username}")
    Call<User> getUser(@Path("username") String username);


    @POST("/auth/login")
    Call<User> executeLogin(@Body HashMap<String, String> map);

    @POST("/auth/register")
    Call<Void> executeSignup(@Body HashMap<String, Object> map);



    @POST("/api/auth/token")
    @FormUrlEncoded
    Call refreshToken(
            @Field("username") String username,
            @Field("refreshToken") String refreshToken
    );









}