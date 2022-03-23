package com.example.collabme.model;

import android.content.Context;
import android.util.Log;

import com.example.collabme.objects.MyApplication;
import com.example.collabme.objects.RetrofitInterface;
import com.example.collabme.objects.User;

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
 //   public Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    public interface getuserconnect{
        void onComplete(User profile);

    }
    public interface GetUserByIdListener{
        void onComplete(User profile);

    }

    public interface EditUserListener{
        void onComplete(int code);

    }



    public void getuserbyusername(String username1, GetUserByIdListener getUserByIdListener) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<User> call = retrofitInterface.getUser(username1,"Bearer "+tockenacsses);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code()==200) {
                    getUserByIdListener.onComplete(response.body());
                }else if (response.code()==403){
                    ModelOffers.instance.changeAcssesToken();
                    ModelUsers.instance3.getuserbyusername(username1,getUserByIdListener);

                }else{
                    getUserByIdListener.onComplete(null);
                }
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
                if(response.code()==200) {
                    getuserconnect.onComplete(response.body());
                }else if (response.code()==403){
                    ModelOffers.instance.changeAcssesToken();
                    ModelUsers.instance3.getUserConnect(getuserconnect);

                }else{
                    getuserconnect.onComplete(null);
                }
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
                if(response.code()==200) {
                    editUserListener.onComplete(200);
                }else if(response.code()==403){
                    ModelOffers.instance.changeAcssesToken();
                    ModelUsers.instance3.EditUser(profile,editUserListener);
                }else{
                    editUserListener.onComplete(400);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("TAG","not "+t);

                editUserListener.onComplete(400);
            }
        });

    }


}
