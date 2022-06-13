package com.example.collabme.TheSighUPProcess;

import static android.graphics.Color.rgb;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.Activites.MainActivity;
import com.example.collabme.Activites.Pop;
import com.example.collabme.R;
import com.example.collabme.model.ModelOffers;
import com.example.collabme.model.ModelUsers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * the Login fragment - inclused :
 * the fields to user to fill to enter the application
 * have a new user button to navigate to create user
 * have a  forget password button to navigate to change password
 */

public class LoginFragment extends Fragment {

    EditText username, password;
    Button login;
    TextView signup, forgotpassword;
    View view;
    LoginButton facebook;
    private CallbackManager callbackManager;
    private static final String EMAIL = "email";
    String email="";
    String name = "";
    String gender = "";
    boolean isCreated = false;
    String facebookToken;
    ProfileTracker profileTracker;

    ProgressBar progressBar;


    AccessToken accessToken ;
    private void toFeedActivity() {
        if(getContext() !=null) {
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        username = view.findViewById(R.id.fragment_login_username);
        password = view.findViewById(R.id.fragment_login_password);
        forgotpassword = view.findViewById(R.id.fragment_login_forgotpass);
        facebook = view.findViewById(R.id.fragment_login_facebook);

        facebook.setFragment(this);

        progressBar = view.findViewById(R.id.login_progressbar);
        progressBar.setVisibility(View.GONE);
        progressBar.getIndeterminateDrawable().setColorFilter(rgb(132, 80, 160), android.graphics.PorterDuff.Mode.MULTIPLY);


        login = view.findViewById(R.id.fragment_login_loginbtn);
        login.setOnClickListener(v -> Modelauth.instance2.Login(username.getText().toString(),password.getText().toString(), new Modelauth.loginListener() {
            @Override
            public void onComplete(int code) {
                if(code==200) {
                    ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
                        @Override
                        public void onComplete(User profile) {
                            ModelOffers.instance.refreshPostList();
                            ModelUsers.instance3.setUserConnected(profile);
                            toFeedActivity();
                        }
                    });
                }
                else{
                    Toast.makeText(getActivity(), "Failed to login!", Toast.LENGTH_LONG).show();
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


        facebook.setOnClickListener(v -> facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                if(Profile.getCurrentProfile() == null) {


                    profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            GraphRequest request = GraphRequest.newMeRequest(
                                    accessToken,
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                                            try {
                                                if (AccessToken.getCurrentAccessToken() == null) {

                                                    return; // already logged out
                                                }
                                                email = jsonObject.getString("email");
                                                name = jsonObject.getString("name");
                                                Long id = jsonObject.getLong("id");

                                                String token = loginResult.getAccessToken().getToken();
                                                facebookToken = token;
                                                String username2 = setUsername(email);

                                                Modelauth.instance2.getUserByUserNameInSignIn(username2, new Modelauth.getUserByUserNameInSignIn() {
                                                    @Override
                                                    public void onComplete(User profile) {
                                                        if (profile != null) {
                                                            isCreated=false;
                                                            Modelauth.instance2.Login(username2, "facebook", new Modelauth.loginListener() {
                                                                @Override
                                                                public void onComplete(int code) {
                                                                    toFeedActivity();
                                                                    Toast.makeText(getActivity(), "Welcome to Collab Me!", Toast.LENGTH_LONG).show();
                                                                }
                                                            });

                                                            return;
                                                        }
                                                        else {
//
                                                            Navigation.findNavController(v).navigate(LoginFragmentDirections.actionGlobalSignupFragment2(username2,"facebook",email));
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
                }

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {
            }
        }));


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
        progressBar.setVisibility(View.VISIBLE);

        Navigation.findNavController(view).navigate(LoginFragmentDirections.actionGlobalSignupFragment2(null,null,null));

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(profileTracker != null) {

            profileTracker.stopTracking();
        }
    }

    public void onStop(){
        super.onStop();
        if(profileTracker != null) {
            profileTracker.stopTracking();
        }
    }


    public String setUsername(String email){
        int index = email.indexOf('@');
        return email.substring(0,index);
    }

}