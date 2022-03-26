package com.example.collabme.model;

import android.content.Context;
import android.util.Log;

import com.example.collabme.objects.MyApplication;
import com.example.collabme.objects.User;
import com.example.collabme.objects.tokensrefresh;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelUsers {


    public static final ModelUsers instance3 = new ModelUsers();
    public com.example.collabme.objects.tokensrefresh tokensrefresh = new tokensrefresh();
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
        tokensrefresh.retroServer();

        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");


        Call<User> call = tokensrefresh.retrofitInterface.getUser(username1,"Bearer "+tockenacsses);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code()==200) {
                    getUserByIdListener.onComplete(response.body());
                }else if (response.code()==403){
                    tokensrefresh.changeAcssesToken();
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
        tokensrefresh.retroServer();
        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");

        String username2= MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("username","");


        Call<User> call = tokensrefresh.retrofitInterface.getUser(username2,"Bearer "+tockenacsses);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code()==200) {
                    getuserconnect.onComplete(response.body());
                }else if (response.code()==403){
                    tokensrefresh.changeAcssesToken();
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
        tokensrefresh.retroServer();
        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");

        HashMap<String, Object> map = profile.tojson();


        Call<User> call = tokensrefresh.retrofitInterface.editUser(profile.getUsername(),"Bearer "+ tockenacsses,map);
        HashMap<String, Object> finalMap = map;
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("TAG",""+response);
                if(response.code()==200) {
                    editUserListener.onComplete(200);
                }else if(response.code()==403){
                    tokensrefresh.changeAcssesToken();
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
