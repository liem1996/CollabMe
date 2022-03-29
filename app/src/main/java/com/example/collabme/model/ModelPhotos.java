package com.example.collabme.model;

import android.content.Context;
import android.net.Uri;

import com.example.collabme.objects.tokensrefresh;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ModelPhotos {

    public static final ModelPhotos instance3 = new ModelPhotos();
    public com.example.collabme.objects.tokensrefresh tokensrefresh = new tokensrefresh();

    public interface PostProfilePhoto{
        void onComplete(int code);

    }


    public void uploadImage(Uri  imageBytes, Context context,String name1, PostProfilePhoto postProfilePhoto) {
        tokensrefresh.retroServer();
        File file = new File(imageBytes.getPath());
        RequestBody profile = RequestBody.create(MultipartBody.FORM,name1);
        RequestBody filepart = RequestBody.create(MediaType.parse(context.getContentResolver().getType(imageBytes)),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("images",  file.getName(),filepart);
        Call<Void> call = tokensrefresh.retrofitInterface.postImage(profile,body);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {

                if (response.isSuccessful()) {
                    postProfilePhoto.onComplete(200);

                } else {
                    postProfilePhoto.onComplete(400);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                postProfilePhoto.onComplete(400);

            }
        });
    }


}
