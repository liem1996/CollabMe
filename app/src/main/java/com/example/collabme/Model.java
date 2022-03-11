package com.example.collabme;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Model {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:4000";
    public static final Model instance = new Model();


    public interface sighup{
        void onComplete(int code);
    }
    public interface Login{
        void onComplete();
    }

    public interface getacssesToken{
        void onComplete(String acssestoken);
    }
    public interface logout{
        void onComplete();
    }
    public interface GetUserByUserName{
        void onComplete(User profile);
    }


    public void sighup(User profile,Model.sighup sighup) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
            HashMap<String, Object> map = new HashMap<>();
            map.put("Username", profile.getUsername());
            map.put("Password", profile.getPassword());
            map.put("Email", profile.getEmail());
            map.put("Sex", profile.getSex());
            map.put("Age", profile.getAge());
            map.put("Followers", profile.getFollowers());
            map.put("NumberOfPosts", profile.getNumOfPosts());
            map.put("Company", profile.getCompany());
            map.put("Influencer", profile.getInfluencer());
            map.put("Profession", profile.getProfessions());
            map.put("Platform", profile.getPlatforms());

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



}
