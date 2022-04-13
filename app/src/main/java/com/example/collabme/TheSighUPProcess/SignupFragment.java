package com.example.collabme.TheSighUPProcess;

import static android.app.Activity.RESULT_OK;
import static android.graphics.Color.rgb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.R;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.User;
import com.facebook.login.LoginManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SignupFragment extends Fragment {

    EditText username, password, email, age;
    Button signup, uploads;
    ImageButton backBtn;
    CheckBox company, influencer;
    Spinner gender;
    String selectedGender;
    List<String> genderStrings;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    boolean goodsign;
    private String mImageUrl = "";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PIC = 2;
    Bitmap imageBitmap;
    String username1, password1, email1, age1, selectedGender1;
    boolean company1, influencer1;

    String facebookUsername, facebookEmail, facebookPassword;
    ProgressBar progressBar;


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

        progressBar = view.findViewById(R.id.signup_progressbar);
        progressBar.setVisibility(View.GONE);
        progressBar.getIndeterminateDrawable().setColorFilter(rgb(132, 80, 160), android.graphics.PorterDuff.Mode.MULTIPLY);

        username = view.findViewById(R.id.fragemnt_signup_username);
        password = view.findViewById(R.id.fragemnt_signup_password);
        email = view.findViewById(R.id.fragemnt_signup_email);
        age = view.findViewById(R.id.fragemnt_signup_age);
        company = view.findViewById(R.id.fragment_signup_company);
        influencer = view.findViewById(R.id.fragment_signup_influencer);

        facebookEmail = SignupFragmentArgs.fromBundle(getArguments()).getEmail();
        facebookPassword = SignupFragmentArgs.fromBundle(getArguments()).getPassword();
        facebookUsername = SignupFragmentArgs.fromBundle(getArguments()).getUsername();

        if (facebookUsername != null && facebookEmail != null && facebookPassword != null) {
            username.setText(facebookUsername);
            password.setText(facebookPassword);
            email.setText(facebookEmail);
            username.setEnabled(false);
            password.setEnabled(false);
            email.setEnabled(false);
        }

        signup = view.findViewById(R.id.fragemnt_signup_continuebtn);
        signup.setOnClickListener(v -> {
            saveDetails();
            Modelauth.instance2.getUserByUserNameInSignIn(username1, new Modelauth.getUserByUserNameInSignIn() {
                @Override
                public void onComplete(User profile) {
                    if (profile != null) {
                        Toast.makeText(getContext(), "Your username is taken", Toast.LENGTH_SHORT).show();
                        goodsign = false;


                        return;
                    }
                    authforuser();
                    if (goodsign) {
                        progressBar.setVisibility(View.VISIBLE);
                        Navigation.findNavController(v).navigate(SignupFragmentDirections.actionSignupFragment2ToSocialmedia(username1, password1, influencer1,
                                company1, email1, age1, selectedGender1, null, null, null, imageBitmap));
                    }
                }
            });
        });

        backBtn = view.findViewById(R.id.fragment_signup_backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                LoginManager.getInstance().logOut();

                Navigation.findNavController(view).navigate(R.id.action_signupFragment2_to_fragment_login);
            }
        });

        uploads = view.findViewById(R.id.fragemnt_signup_uploadbtn);
        uploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        return view;
    }

    public void openGallery() {
        Intent photoPicerIntent = new Intent(Intent.ACTION_PICK);
        photoPicerIntent.setType("image/jpeg");
        startActivityForResult(photoPicerIntent, REQUEST_IMAGE_PIC);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
            }
        } else if (requestCode == REQUEST_IMAGE_PIC) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                    imageBitmap = BitmapFactory.decodeStream(imageStream);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void saveDetails() {
        username1 = username.getText().toString();
        password1 = password.getText().toString();
        influencer1 = influencer.isChecked();
        company1 = company.isChecked();
        email1 = email.getText().toString();
        age1 = age.getText().toString();
        selectedGender1 = selectedGender;
    }

    private List<String> getAllGenders() {
        List<String> tmp = new ArrayList<>();
        tmp.add("Female");
        tmp.add("Male");
        tmp.add("Undefined");
        return tmp;
    }


    private void initSpinnerFooter() {
        String[] items = new String[genderStrings.size()];

        for (int i = 0; i < genderStrings.size(); i++) {
            items[i] = genderStrings.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        gender.setAdapter(adapter);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                selectedGender = items[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
    }

    public void authforuser() {
        if (!email1.matches(emailPattern)) {
            Toast.makeText(getContext(), "Your email was written wrong", Toast.LENGTH_SHORT).show();
            goodsign = false;
            return;
        }

        if (password1.isEmpty() || username1.isEmpty() || email1.isEmpty()) {
            Toast.makeText(getContext(), "You have to fill username,password and email fields", Toast.LENGTH_SHORT).show();
            goodsign = false;
            return;
        }

        if ((!age1.equals("")) && !isInteger(age1)) {
            Toast.makeText(getContext(), "Your age field is not an integer", Toast.LENGTH_SHORT).show();
            goodsign = false;
            return;
        }

        goodsign = true;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

}