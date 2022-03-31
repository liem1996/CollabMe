package com.example.collabme.TheSighUPProcess;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.Activites.Pop;
import com.example.collabme.R;
import com.example.collabme.model.ModelUsers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.Activites.MainActivity;
import com.example.collabme.objects.User;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginFragment extends Fragment {

    EditText username, password;
    Button login;
    TextView signup, forgotpassword;
    View view;
    LoginButton facebook;
    TextView info;
    ImageView profileImg;
    private CallbackManager callbackManager;
    private static final String EMAIL = "email";
    String email="";
    String name = "";
    boolean isCreated = false;


    AccessToken accessToken ;
    private void toFeedActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        username = view.findViewById(R.id.fragment_login_username);
        password = view.findViewById(R.id.fragment_login_password);
        forgotpassword = view.findViewById(R.id.fragment_login_forgotpass);

        facebook = view.findViewById(R.id.fragment_login_facebook);
        facebook.setReadPermissions(Arrays.asList(EMAIL));
        facebook.setFragment(this);


        login = view.findViewById(R.id.fragment_login_loginbtn);
        login.setOnClickListener(v -> Modelauth.instance2.Login(username.getText().toString(),password.getText().toString(), new Modelauth.loginListener() {
            @Override
            public void onComplete(int code) {
                if(code==200) {
                    toFeedActivity();

                }
                else{
                    Toast.makeText(getActivity(), "boo! it failed!", Toast.LENGTH_LONG).show();
                }
            }
        }));
        signup = view.findViewById(R.id.fragment_login_newuser);
        signup.setOnClickListener(v -> handleSighUp());

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , Pop.class));
            }
        });


        callbackManager = CallbackManager.Factory.create();
        facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                ProfileTracker profileTracker;
                AccessTokenTracker accessTokenTracker;
                if(Profile.getCurrentProfile() == null) {

                    profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            currentProfile.getName();
                            GraphRequest request = GraphRequest.newMeRequest(
                                    accessToken,
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                                            try {
                                                email = jsonObject.getString("email");
                                                name = jsonObject.getString("name");
                                                Long id = jsonObject.getLong("id");
                                               // String token = loginResult.getAccessToken().getToken();
//                                                Modelauth.instance2.Login(name, "facebook", new Modelauth.loginListener() {
//                                                    @Override
//                                                    public void onComplete(int code) {
//                                                        //Navigation.findNavController(view).navigate(LoginFragmentDirections);
//                                                        toFeedActivity();
//                                                    }
//                                                });
                                                ModelUsers.instance3.getUserByEmail(email, new ModelUsers.GetUserByUserEmail(){

                                                    @Override
                                                    public void onComplete(User profile) {
                                                        if (profile != null) {
                                                            Toast.makeText(getContext(), "Your username is taken", Toast.LENGTH_SHORT).show();
                                                            Modelauth.instance2.Login(name, "facebook", new Modelauth.loginListener() {
                                                                @Override
                                                                public void onComplete(int code) {
                                                                    //Navigation.findNavController(view).navigate(LoginFragmentDirections);
                                                                    toFeedActivity();
                                                                }
                                                            });
                                                            isCreated = true;
                                                            return;
                                                        }
                                                        else if(!isCreated) {
                                                            Navigation.findNavController(view).navigate(LoginFragmentDirections.actionGlobalSocialmedia(name, "facebook", false,
                                                                    true, email, "0", "?", null, null, null));
                                                        }
                                                    }
                                                });


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id,name,email");
                            request.setParameters(parameters);
                            request.executeAsync();
                        }
                    };
                    profileTracker.startTracking();
                }else {

                    Profile profile = Profile.getCurrentProfile();
                    if (profile != null) {
                        String facebookUserId = profile.getId();
                        String facebookName = profile.getName();
                        GraphRequest request = GraphRequest.newMeRequest(
                                accessToken,
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                                        try {
                                            email = jsonObject.getString("email");
                                            name = jsonObject.getString("name");
                                            Long id = jsonObject.getLong("id");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }
                }

                //facebook.setOnClickListener(v -> {
                    //saveDetails();
//                    Modelauth.instance2.getUserByEmail(email, new Modelauth.GetUserByUserEmail() {
//                        @Override
//                        public void onComplete(User profile) {
//                            if (profile != null) {
//                                Toast.makeText(getContext(), "Your username is taken", Toast.LENGTH_SHORT).show();
//                                isCreated = false;
//                                return;
//                            }
//                           else if(isCreated) {
//                                Navigation.findNavController(view).navigate(SignupFragmentDirections.actionSignupFragment2ToSocialmedia(name, "facebook", false,
//                                        true, email, "0", "?", null, null, null));
//                            }
//                        }
//                    });
                //});
               // Modelauth.instance2.Login(loginResult.getAccessToken().getUserId());
//                info.setText("User ID: " + loginResult.getAccessToken().getUserId() + "\n" + "Auth Token: " + loginResult.getAccessToken().getToken());
//                String imageURL = "https://graph.facebook.com/"+loginResult.getAccessToken().getUserId() +"/picture?return_ssl_resources=1";
//                Picasso.get().load(imageURL).into(profileImg);
//                accessToken = loginResult.getAccessToken();

                //handleFacebookToken(loginResult.getAccessToken());
              //  Navigation.findNavController(view).navigate(LoginFragmentDirections.actionGlobalSocialmedia("null","null",false,false,email,"null","null",null,null,null));
                   //toFeedActivity();
            }

            @Override
            public void onCancel() {
//                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
            //    info.setText("Login attempt failed.");
            }
        });


        return view;
    }

    private void handleFacebookToken(AccessToken accessToken) {
        Profile profile =Profile.getCurrentProfile();
        if(profile!=null) {
            String facebookUserId = profile.getId();
            String facebookName = profile.getName();
            //String facebookEmail = profile.getEma
                            GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                                try {
                                    String email = jsonObject.getString("email");
                                    String name = jsonObject.getString("name");
                                    Long id = jsonObject.getLong("id");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();


        }


    }


    private void handleSighUp() {

        Navigation.findNavController(view).navigate(R.id.action_fragment_login_to_signupFragment2);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if you don't add following block,
        // your registered `FacebookCallback` won't be called
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        // accessTokenTracker.stopTracking();
//        profileTracker.stopTracking();
//    }
//
//    public void onStop(){
//        super.onStop();
//        accessTokenTracker.stopTracking();
//        profileTracker.stopTracking();
//    }
}