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
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupFragment extends Fragment {

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

        String username1 = username.getText().toString();
        String password1 = password.getText().toString();
        Boolean influencer1 =  influencer.isChecked();
        Boolean company1 =   company.isChecked();
        String email1 =  email.getText().toString();
        String age1 = age.getText().toString();
        String selectedgen1 = selectedGender;

        signup = view.findViewById(R.id.fragemnt_signup_continuebtn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(SignupFragmentDirections.actionSignupFragment2ToSocialmedia(username1, password1,influencer1,
                        company1,email1,age1 ,selectedgen1 ,null,null, null));
            }
        });

        return view;
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