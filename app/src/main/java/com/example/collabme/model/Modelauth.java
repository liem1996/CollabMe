package com.example.collabme.model;

import android.content.Context;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Modelauth {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:4000";
    public static final Modelauth instance2 = new Modelauth();
    public String username1="liem";


    /**
     * interfaces authentication
     */

    public interface signupListener{
        void onComplete(int code);
    }
    public interface loginListener{
        void onComplete(int code);
    }
    public interface logout{
        void onComplete(int code);
    }
    public interface islogin{
        void onComplete(boolean boo);

    }

    public void isSignIn (islogin isloginlisenter) {
        // Check if user is signed in (non-null) and update UI accordingly.


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<Boolean> call = retrofitInterface.getuserislogin("Bearer " + tockenacsses);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (response.code() == 200) {

                    isloginlisenter.onComplete(response.body());

                } else {
                    isloginlisenter.onComplete(false);

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                isloginlisenter.onComplete(false);
            }
        });

    }

    public void sighup(User profile,signupListener sighup) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        HashMap<String, Object> map = new HashMap<>();
        map = profile.tojson();
        Call<Void> call = retrofitInterface.executeSignup(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    sighup.onComplete(200);

                } else {
                    sighup.onComplete(400);

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                sighup.onComplete(400);
            }
        });
    }


    public void Login(String username,String password,loginListener Login){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("Username", username);
        map.put("Password", password);
        username1=username;

        Call<tokenrespone> call = retrofitInterface.executeLogin(map);
        call.enqueue(new Callback<tokenrespone>() {
            @Override
            public void onResponse(Call<tokenrespone> call, Response<tokenrespone> response) {
                if (response.code() == 200) {
                    String tokenResponse = response.body().getaccessToken();
                    String tokenrefresh = response.body().getrefreshToken();
                    MyApplication.getContext()
                            .getSharedPreferences("TAG",Context.MODE_PRIVATE)
                            .edit()
                            .putString("tokenAcsses",tokenResponse)
                            .commit();
                    MyApplication.getContext()
                            .getSharedPreferences("TAG1",Context.MODE_PRIVATE)
                            .edit()
                            .putString("tokenrefresh",tokenrefresh)
                            .commit();
                    MyApplication.getContext()
                            .getSharedPreferences("TAG",Context.MODE_PRIVATE)
                            .edit()
                            .putString("username",username1)
                            .commit();
                    Login.onComplete(200);

                } else  {
                    Login.onComplete(400);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<tokenrespone> call, Throwable t) {
                Login.onComplete(400);
            }

        });



    }

    public void logout(logout logout){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String tokenrefresh = MyApplication.getContext()
                .getSharedPreferences("TAG1", Context.MODE_PRIVATE)
                .getString("tokenrefresh","");

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<Void> call = retrofitInterface.excutelogout("Bearer "+tokenrefresh);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    logout.onComplete(200);
                    MyApplication.getContext()
                            .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                            .edit()
                            .putString("tokenAcsses", "")
                            .commit();
                    MyApplication.getContext()
                            .getSharedPreferences("TAG1", Context.MODE_PRIVATE)
                            .edit()
                            .putString("tokenrefresh", "")
                            .commit();
                }else{
                    logout.onComplete(400);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                logout.onComplete(400);
            }
        });


    }


}
