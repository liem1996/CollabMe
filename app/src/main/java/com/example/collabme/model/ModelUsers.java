package com.example.collabme.model;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModelUsers {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:4000";
    public static final ModelUsers instance3 = new ModelUsers();

    public interface getuserconnect{
        void onComplete(User profile);

    }
    public interface GetUserByIdListener{
        void onComplete(User profile);

    }

    public interface EditUserListener{
        void onComplete(int code);

    }

    public void getUserById(String id, GetUserByIdListener getUserByIdListener) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<User> call = retrofitInterface.getUserById(id,"Bearer "+tockenacsses);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                getUserByIdListener.onComplete(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                getUserByIdListener.onComplete(null);
            }
        });
    }


    public void getuserbyusername(String username, GetUserByIdListener getUserByIdListener) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<User> call = retrofitInterface.getUser(username,"Bearer "+tockenacsses);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                getUserByIdListener.onComplete(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                getUserByIdListener.onComplete(null);
            }
        });
    }


    public void getUserConnect(getuserconnect getuserconnect) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");

        String username2= MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("username","");

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<User> call = retrofitInterface.getUser(username2,"Bearer "+tockenacsses);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                getuserconnect.onComplete(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                getuserconnect.onComplete(null);
            }
        });
    }


    public void EditUser(User profile,EditUserListener editUserListener){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");

        HashMap<String, Object> map = new HashMap<>();
        map = profile.tojson();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<User> call = retrofitInterface.editUser(profile.getUsername(),"Bearer "+ tockenacsses,map);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("TAG",""+response);

                editUserListener.onComplete(200);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("TAG","not "+t);

                editUserListener.onComplete(400);
            }
        });

    }


}
