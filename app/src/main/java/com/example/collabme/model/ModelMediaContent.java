package com.example.collabme.model;

import android.content.Context;

import com.example.collabme.objects.MyApplication;
import com.example.collabme.objects.tokensrefresh;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * this model is for media content the function in this model are :
 * 1.add a media content to an specific offer according to offer id
 *
 * */

public class ModelMediaContent {

    public static final ModelMediaContent instance = new ModelMediaContent();

    public com.example.collabme.objects.tokensrefresh tokensrefresh = new tokensrefresh();


    public interface addMediaContentListener {
        void onComplete(int code);
    }

    public void addMediaContent(String idOffer, String[] MediaContent, ModelMediaContent.addMediaContentListener addMediaContentListener) {
        tokensrefresh.retroServer();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("IdOffer",idOffer);
        map.put("MediaContent",MediaContent);

        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");

        Call<Void> call = tokensrefresh.retrofitInterface.addMediaContent(map, "Bearer " + tockenacsses);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    addMediaContentListener.onComplete(200);
                } else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    String tockennew = tokensrefresh.gettockenAcsses();
                    Call<Void> call1 = tokensrefresh.retrofitInterface.addMediaContent(map, "Bearer " + tockennew);
                    call1.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response1) {
                            if (response1.code() == 200) {
                                addMediaContentListener.onComplete(200);
                            } else {
                                addMediaContentListener.onComplete(400);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            addMediaContentListener.onComplete(400);
                        }
                    });
                } else {
                    addMediaContentListener.onComplete(400);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                addMediaContentListener.onComplete(400);
            }
        });
    }


}
