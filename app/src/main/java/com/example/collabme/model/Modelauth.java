package com.example.collabme.model;

import android.content.Context;

import com.example.collabme.objects.MyApplication;
import com.example.collabme.objects.RetrofitInterface;
import com.example.collabme.objects.User;
import com.example.collabme.objects.tokenrespone;
import com.example.collabme.objects.tokensrefresh;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Modelauth {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:4000";
    public static final Modelauth instance2 = new Modelauth();
    public String username1="liem";
    public com.example.collabme.objects.tokensrefresh tokensrefresh = new tokensrefresh();


    /**
     * interfaces authentication
     */

    public interface signupListener{
        void onComplete(int code);
    }
    public interface loginListener{
        void onComplete(int code);
    }
    public interface logout{
        void onComplete(int code);
    }
    public interface islogin{
        void onComplete(boolean boo);
    }

    public interface getUserByUserNameInSignIn{
        void onComplete(User profile);
    }

    public void isSignIn (islogin isloginlisenter) {
        // Check if user is signed in (non-null) and update UI accordingly.

        tokensrefresh.retroServer();

        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");


        Call<Boolean> call = tokensrefresh.retrofitInterface.getuserislogin("Bearer " + tockenacsses);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (response.code() == 200) {
                    isloginlisenter.onComplete(true);

                } else if(response.code()==403) {
                    tokensrefresh.changeAcssesToken();
                    String tockennew = tokensrefresh.gettockenAcsses();
                    Call<Boolean> call1 = tokensrefresh.retrofitInterface.getuserislogin("Bearer "+tockennew);
                    call1.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if(response.code()==200){
                                isloginlisenter.onComplete(true);
                            }else{
                                isloginlisenter.onComplete(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            isloginlisenter.onComplete(false);
                        }
                    });
                }else{
                    isloginlisenter.onComplete(false);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                isloginlisenter.onComplete(false);
            }
        });

    }

    public void sighup(User profile,signupListener sighup) {
        tokensrefresh.retroServer();


        HashMap<String, Object> map = new HashMap<>();
        map = profile.tojson();
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


    public void Login(String username,String password,loginListener Login){
        tokensrefresh.retroServer();

        HashMap<String, String> map = new HashMap<>();
        map.put("Username", username);
        map.put("Password", password);
        username1=username;

        Call<tokenrespone> call = tokensrefresh.retrofitInterface.executeLogin(map);
        call.enqueue(new Callback<tokenrespone>() {
            @Override
            public void onResponse(Call<tokenrespone> call, Response<tokenrespone> response) {
                if (response.code() == 200) {
                    String tokenResponse = response.body().getaccessToken();
                    String tokenrefresh = response.body().getrefreshToken();
                    MyApplication.getContext()
                            .getSharedPreferences("TAG",Context.MODE_PRIVATE)
                            .edit()
                            .putString("tokenAcsses",tokenResponse)
                            .commit();
                    MyApplication.getContext()
                            .getSharedPreferences("TAG1",Context.MODE_PRIVATE)
                            .edit()
                            .putString("tokenrefresh",tokenrefresh)
                            .commit();
                    MyApplication.getContext()
                            .getSharedPreferences("TAG",Context.MODE_PRIVATE)
                            .edit()
                            .putString("username",username1)
                            .commit();
                    Login.onComplete(200);

                } else  {
                    Login.onComplete(400);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<tokenrespone> call, Throwable t) {
                Login.onComplete(400);
            }

        });



    }

    public void logout(logout logout){
        tokensrefresh.retroServer();

        String tokenrefresh = MyApplication.getContext()
                .getSharedPreferences("TAG1", Context.MODE_PRIVATE)
                .getString("tokenrefresh","");



        Call<Void> call = tokensrefresh.retrofitInterface.excutelogout("Bearer "+tokenrefresh);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    logout.onComplete(200);
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
                }else{
                    logout.onComplete(400);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                logout.onComplete(400);
            }
        });
    }

    /////////////////////////

    public void getUserByUserNameInSignIn(String username, Modelauth.getUserByUserNameInSignIn getUserByUserNameInSignIn) {
        tokensrefresh.retroServer();


        tokensrefresh.retrofitInterface = tokensrefresh.retrofit.create(RetrofitInterface.class);
        Call<User> call = retrofitInterface.getUserByUserNameInSignIn(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                getUserByUserNameInSignIn.onComplete(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                getUserByUserNameInSignIn.onComplete(null);
            }
        });
    }

}
