package com.example.collabme.model;

import android.content.Context;

import com.example.collabme.objects.ChatUserConvo;
import com.example.collabme.objects.MyApplication;
import com.example.collabme.objects.tokensrefresh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelChatUser {

    public static final ModelChatUser instance3 = new ModelChatUser();
    public com.example.collabme.objects.tokensrefresh tokensrefresh = new tokensrefresh();

    public interface GetUserChatWithAnother {
        void onComplete(List<ChatUserConvo> list);

    }

    public void getChatOtherSide(ChatUserConvo chatUserConvo, GetUserChatWithAnother getUserChatWithAnother) {
        tokensrefresh.retroServer();
        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");
        HashMap<String, Object> map = chatUserConvo.toJson();
        Call<List<ChatUserConvo>> call = tokensrefresh.retrofitInterface.getothersideconvo(map, "Bearer " + tockenacsses);
        call.enqueue(new Callback<List<ChatUserConvo>>() {
            @Override
            public void onResponse(Call<List<ChatUserConvo>> call, Response<List<ChatUserConvo>> response) {
                if (response.code() == 200) {
                    List<ChatUserConvo> list = new ArrayList<>();
                    list.addAll(response.body());
                    getUserChatWithAnother.onComplete(list);
                } else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    ModelChatUser.instance3.getChatOtherSide(chatUserConvo, getUserChatWithAnother);
                } else {
                    getUserChatWithAnother.onComplete(null);
                }
            }

            @Override
            public void onFailure(Call<List<ChatUserConvo>> call, Throwable t) {
                getUserChatWithAnother.onComplete(null);
            }
        });
    }


    public void getusersChatConnectotherside(ChatUserConvo chatUserConvo, GetUserChatWithAnother getUserChatWithAnother) {
        tokensrefresh.retroServer();
        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");
        HashMap<String, Object> map = chatUserConvo.toJson();
        Call<List<ChatUserConvo>> call = tokensrefresh.retrofitInterface.getchatConvo(map, "Bearer " + tockenacsses);
        call.enqueue(new Callback<List<ChatUserConvo>>() {
            @Override
            public void onResponse(Call<List<ChatUserConvo>> call, Response<List<ChatUserConvo>> response) {
                if (response.code() == 200) {
                    List<ChatUserConvo> list = new ArrayList<>();
                    list.addAll(response.body());
                    getUserChatWithAnother.onComplete(list);
                } else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    ModelChatUser.instance3.getusersChatConnectotherside(chatUserConvo, getUserChatWithAnother);
                } else {
                    getUserChatWithAnother.onComplete(null);
                }
            }

            @Override
            public void onFailure(Call<List<ChatUserConvo>> call, Throwable t) {
                getUserChatWithAnother.onComplete(null);
            }
        });
    }





}
