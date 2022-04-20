package com.example.collabme.model;

import android.content.Context;
import android.util.Log;

import com.example.collabme.objects.MyApplication;
import com.example.collabme.objects.RetrofitInterface;
import com.example.collabme.objects.User;
import com.example.collabme.objects.tokensrefresh;
import com.facebook.login.LoginManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelUsers {

    public static final ModelUsers instance3 = new ModelUsers();
    public com.example.collabme.objects.tokensrefresh tokensrefresh = new tokensrefresh();
    private User userConnected;
    //   public Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    public User getUser() {
        return userConnected;
    }

    public interface getuserconnect {
        void onComplete(User profile);

    }

    public interface editUserWithoutAuthListener{
        void onComplete(int code);
    }

    public interface GetUserByIdListener {
        void onComplete(User profile);

    }

    public interface EditUserListener {
        void onComplete(int code);

    }
    public interface DeleteUserListener {
        void onComplete(int code);

    }

    public interface GetUserByUserEmail {
        void onComplete(User profile);
    }

    public void getuserbyusername(String username1, GetUserByIdListener getUserByIdListener) {
        tokensrefresh.retroServer();

        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");


        Call<User> call = tokensrefresh.retrofitInterface.getUser(username1, "Bearer " + tockenacsses);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    userConnected = response.body();
                    getUserByIdListener.onComplete(userConnected);
                } else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    ModelUsers.instance3.getuserbyusername(username1, getUserByIdListener);
                } else {
                    getUserByIdListener.onComplete(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                getUserByIdListener.onComplete(null);
            }
        });
    }

    public void setUserConnected(User user) {
        this.userConnected = user;
    }

    public void getUserConnect(getuserconnect getuserconnect) {
        tokensrefresh.retroServer();
        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");

        String username2 = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("username", "");


        Call<User> call = tokensrefresh.retrofitInterface.getUser(username2, "Bearer " + tockenacsses);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    userConnected = response.body();
                    getuserconnect.onComplete(userConnected);
                } else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    ModelUsers.instance3.getUserConnect(getuserconnect);
                } else {
                    getuserconnect.onComplete(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                getuserconnect.onComplete(null);
            }
        });
    }

    public void EditUser(User profile, EditUserListener editUserListener) {
        tokensrefresh.retroServer();
        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");

        HashMap<String, Object> map = profile.tojson();


        Call<User> call = tokensrefresh.retrofitInterface.editUser(profile.getUsername(), "Bearer " + tockenacsses, map);
        HashMap<String, Object> finalMap = map;
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("TAG", "" + response);
                if (response.code() == 200) {
                    editUserListener.onComplete(200);
                } else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    ModelUsers.instance3.EditUser(profile, editUserListener);
                } else {
                    editUserListener.onComplete(400);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("TAG", "not " + t);

                editUserListener.onComplete(400);
            }
        });

    }

    public void editUserWithoutAuth(User profile, editUserWithoutAuthListener editUserWithoutAuthListener) {
        tokensrefresh.retroServer();

        HashMap<String, Object> map = profile.tojson();

        Call<User> call = tokensrefresh.retrofitInterface.editUserWithoutAuth(profile.getUsername() , map);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("TAG", "" + response);
                if (response.code() == 200) {
                    editUserWithoutAuthListener.onComplete(200);
                } else  {
                    editUserWithoutAuthListener.onComplete(400);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("TAG", "not " + t);

                editUserWithoutAuthListener.onComplete(400);
            }
        });

    }

    public void deleteUser(String username, DeleteUserListener deleteUserListener) {
        tokensrefresh.retroServer();
        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");


        Call<User> call = tokensrefresh.retrofitInterface.deleteUser(username, "Bearer " + tockenacsses);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("TAG", "" + response);

                if (response.code() == 200) {
                    deleteUserListener.onComplete(200);

                } else {
                    deleteUserListener.onComplete(400);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("TAG", "not " + t);

                deleteUserListener.onComplete(400);
            }
        });

    }

//    public void getUserByEmail(String email, ModelUsers.GetUserByUserEmail getUserByUserEmail) {
//        tokensrefresh.retroServer();
//
//
//        tokensrefresh.retrofitInterface = tokensrefresh.retrofit.create(RetrofitInterface.class);
//        Call<User> call = tokensrefresh.retrofitInterface.getUserByEmail(email);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                getUserByUserEmail.onComplete(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                getUserByUserEmail.onComplete(null);
//            }
//        });
//    }

    public void getUserByEmail(String email, String token, GetUserByUserEmail getUserByUserEmail) {
        tokensrefresh.retroServer();

        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");


        Call<User> call = tokensrefresh.retrofitInterface.getUserByEmail(email, "Bearer " + token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    getUserByUserEmail.onComplete(response.body());
                } else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    ModelUsers.instance3.getUserByEmail(email, token, getUserByUserEmail);
                } else {
                    getUserByUserEmail.onComplete(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                getUserByUserEmail.onComplete(null);
            }
        });
    }
}
