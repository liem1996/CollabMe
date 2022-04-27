package com.example.collabme.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.collabme.objects.tokensrefresh;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * this model is for images the function in this model are :
 * 1.get the image which is been used for profile and offers
 * 2.post the image which is been upload to an profile or an offer
 */


public class ModelPhotos {

    public static final ModelPhotos instance3 = new ModelPhotos();
    public com.example.collabme.objects.tokensrefresh tokensrefresh = new tokensrefresh();

    /**
     *
     *
     * interfaces
     */
    public interface PostProfilePhoto{
        void onComplete(String uri);

    }
    public interface getimagesfile{
        void onComplete(Bitmap responseBody);

    }

    /**
     *
     *
     * functions
     */

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

        Call<ResponseBody> call = tokensrefresh.retrofitInterface.postImage(body,name);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response1) {
                if (response1.code()==200) {
                    System.out.println(response1.body().contentType());
                    try {
                        postProfilePhoto.onComplete(response1.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    postProfilePhoto.onComplete(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                postProfilePhoto.onComplete(null);

            }
        });
    }


    public void getimages(String urlphoto, getimagesfile getimagesfile) {
        tokensrefresh.retroServer();
        Call<ResponseBody> call = tokensrefresh.retrofitInterface.getimage(urlphoto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response1) {
                if (response1.code()==200) {
                    try {
                        Bitmap bitmap =   convertCompressedByteArrayToBitmap(response1.body().bytes());
                        getimagesfile.onComplete(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    getimagesfile.onComplete(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                getimagesfile.onComplete(null);

            }
        });
    }

    public static Bitmap convertCompressedByteArrayToBitmap(byte[] src){
        return BitmapFactory.decodeByteArray(src, 0, src.length);
    }


}