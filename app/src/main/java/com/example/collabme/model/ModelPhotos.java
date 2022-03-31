package com.example.collabme.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.example.collabme.objects.tokensrefresh;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ModelPhotos {

    public static final ModelPhotos instance3 = new ModelPhotos();
    public com.example.collabme.objects.tokensrefresh tokensrefresh = new tokensrefresh();

    public interface PostProfilePhoto{
        void onComplete(Uri uri);

    }


    public void uploadImage(Bitmap imageBytes, Context context, PostProfilePhoto postProfilePhoto) {
        tokensrefresh.retroServer();
        File filesDir = context.getFilesDir();
        File file = new File(filesDir, "image" + ".png");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        imageBytes.compress(Bitmap.CompressFormat.PNG, 0, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bitmapdata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");

        Call<Uri> call = tokensrefresh.retrofitInterface.postImage(body,name);
        call.enqueue(new Callback<Uri>() {
            @Override
            public void onResponse(Call<Uri> call, retrofit2.Response<Uri> response1) {
                if (response1.code()==200) {
                    postProfilePhoto.onComplete(response1.body());

                } else {
                    postProfilePhoto.onComplete(null);
                }
            }

            @Override
            public void onFailure(Call<Uri> call, Throwable t) {
                postProfilePhoto.onComplete(null);

            }
        });
    }


}
