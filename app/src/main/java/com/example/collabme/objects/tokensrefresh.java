package com.example.collabme.objects;

import android.content.Context;

import com.example.collabme.model.ModelUsers;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tokensrefresh {
    public Retrofit retrofit;
    public RetrofitInterface retrofitInterface;
    public String BASE_URL = "http://10.0.2.2:4000"; //local server
    //public String BASE_URL = "http://193.106.55.126:4000"; //remote server
    public static final ModelUsers instance3 = new ModelUsers();


    public void retroServer(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }


    public void changeAcssesToken(){

        retroServer();
        String tokenrefresh = MyApplication.getContext()
                .getSharedPreferences("TAG1", Context.MODE_PRIVATE)
                .getString("tokenrefresh","");
        Call<tokenrespone> call = retrofitInterface.getnewtoken("Bearer " + tokenrefresh);
        call.enqueue(new Callback<tokenrespone>() {
            @Override
            public void onResponse(Call<tokenrespone> call, Response<tokenrespone> response) {
                if(response.code()==200) {
                    String tokenResponse = response.body().getaccessToken();
                    String tokenrefresh = response.body().getrefreshToken();
                    MyApplication.getContext()
                            .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                            .edit()
                            .putString("tokenAcsses", tokenResponse)
                            .commit();
                    MyApplication.getContext()
                            .getSharedPreferences("TAG1", Context.MODE_PRIVATE)
                            .edit()
                            .putString("tokenrefresh", tokenrefresh)
                            .commit();
                }else{
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
                }
            }

            @Override
            public void onFailure(Call<tokenrespone> call, Throwable t) {
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
            }
        });

    }

    public String gettockenAcsses(){
        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");
        return tockenacsses;
    }

}
