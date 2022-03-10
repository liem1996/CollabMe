package com.example.collabme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupFragment extends Fragment {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:4000";
    EditText username, password,email,age;
    Button signup;
    CheckBox company, influencer;
    Spinner gender;
    String selectedGender;
    List<String> genderStrings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        gender = view.findViewById(R.id.fragemnt_signup_gender);
        genderStrings = new ArrayList<>();
        genderStrings = getAllGenders();
        initSpinnerFooter();

        username = view.findViewById(R.id.fragemnt_signup_username);
        password = view.findViewById(R.id.fragemnt_signup_password);
        email = view.findViewById(R.id.fragemnt_signup_email);
        age = view.findViewById(R.id.fragemnt_signup_age);
        company = view.findViewById(R.id.fragment_signup_company);
        influencer = view.findViewById(R.id.fragment_signup_influencer);

        //EditText gender = view.findViewById(R.id.fragemnt_signup_gender);
//        EditText professions = view.findViewById(R.id.fragemnt_signup_age);
//        EditText platforms = view.findViewById(R.id.fragemnt_signup_age);
//        EditText followers = view.findViewById(R.id.fragemnt_signup_age);
//        EditText numOfPosts = view.findViewById(R.id.fragemnt_signup_age);

        signup = view.findViewById(R.id.fragemnt_signup_continuebtn);
        signup.setOnClickListener(v -> handleSighUp());

        return view;
    }

    private void handleSighUp() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("Username", username.getText().toString());
        map.put("Password", password.getText().toString());
        map.put("Email", email.getText().toString());
        map.put("Sex", null);
        map.put("Age", age.getText().toString());
        map.put("Followers", null);
        map.put("NumberOfPosts", null);
        map.put("Company", company.isChecked());
        map.put("Influencer", influencer.isChecked());
        map.put("Profession", null);
        map.put("Platform", null);

        Call<Void> call = retrofitInterface.executeSignup(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(getActivity(), "sigh up", Toast.LENGTH_LONG).show();

                } else if (response.code() == 400) {
                    Toast.makeText(getActivity(), "not sighup", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<String> getAllGenders(){
        List<String> tmp = new ArrayList<>();
        tmp.add("Female");
        tmp.add("Male");
        tmp.add("Undefined");
        return tmp;
    }


    private void initSpinnerFooter() {
        String[] items = new String[genderStrings.size()];

        for(int i = 0 ; i<genderStrings.size();i++){
            items[i] = genderStrings.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        gender.setAdapter(adapter);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(25);
                selectedGender = items[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
    }

}