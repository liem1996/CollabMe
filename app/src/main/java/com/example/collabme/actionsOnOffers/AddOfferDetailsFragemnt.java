package com.example.collabme.actionsOnOffers;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.collabme.Activites.LoginActivity;
import com.example.collabme.R;
import com.example.collabme.model.ModelOffers;
import com.example.collabme.model.ModelPhotos;
import com.example.collabme.model.ModelUsers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.User;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;


public class AddOfferDetailsFragemnt extends Fragment {

    EditText headline, description, finishdate, price;
    TextView status, candidates, profession, proposer;
    Button save;
    ImageButton backBtn;
    Offer offer;
    User userConnected;
    boolean[] selectedLanguage;
    String[] chosenOffers, dateSplitArr;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"Sport", "Cooking", "Fashion", "Music", "Dance", "Cosmetic", "Travel", "Gaming", "Tech", "Food",
            "Art", "Animals", "Movies", "Photograph", "Lifestyle", "Other"};
    ImageView logout,camra,gallery,profilepic;;
    String date;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PIC = 2;
    Bitmap imageBitmap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_offer_details_fragemnt, container, false);

        proposer = view.findViewById(R.id.fragemnt_newoffer_proposer);
        headline = view.findViewById(R.id.fragemnt_newoffer_headline);
        description = view.findViewById(R.id.fragemnt_newoffer_description);
        finishdate = view.findViewById(R.id.fragemnt_newoffer_finishdate);
        status = view.findViewById(R.id.fragment_addoffer_status);
        profession = view.findViewById(R.id.fragment_addoffer_profession);
        candidates = view.findViewById(R.id.fragment_newoffer_candidates);
        price = view.findViewById(R.id.fragemnt_newoffer_price);
        camra = view.findViewById(R.id.fragemnt_newoffer_camera);
        gallery = view.findViewById(R.id.fragemnt_newoffer_gallery);

        profilepic = view.findViewById(R.id.fragemnt_newoffer_image);
        save = view.findViewById(R.id.fragemnt_newoffer_saveBtn);
        logout = view.findViewById(R.id.fragment_newoffer_logoutBtn);
        backBtn = view.findViewById(R.id.fragment_newoffer_backBtn);

        status.setText("Open");

        selectedLanguage = new boolean[langArray.length];

        String uniqueKey = UUID.randomUUID().toString();

        ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
            @Override
            public void onComplete(User profile) {
                if (profile != null) {
                    userConnected = profile;
                    proposer.setText(profile.getUsername());
                }
            }
        });

        profession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Select Profession:");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position in lang list
                            langList.add(i);
                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        chosenOffers = new String[langList.size()];

                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(langArray[langList.get(j)]);

                            chosenOffers[j] = (langArray[langList.get(j)]); //to check again

                            System.out.println("ko");
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        profession.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            profession.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidDate()) {
                    offer = new Offer(description.getText().toString(), headline.getText().toString(), date,
                            price.getText().toString(), uniqueKey, status.getText().toString(), chosenOffers, userConnected.getUsername()
                            );

                    if(imageBitmap!=null) {
                        ModelPhotos.instance3.uploadImage(imageBitmap, getActivity(), new ModelPhotos.PostProfilePhoto() {
                            @Override
                            public void onComplete(String uri) {
                                offer.setImage(uri);
                                ModelOffers.instance.addOffer(offer, new ModelOffers.addOfferListener() {
                                    @Override
                                    public void onComplete(int code) {

                                        if (code == 200) {
                                            //   Model.instance.Login(userConnected.getUsername(), userConnected.getPassword(), code1 -> { });
                                            Toast.makeText(getActivity(), "added offer", Toast.LENGTH_LONG).show();
                                            ModelOffers.instance.refreshPostList();
                                            Navigation.findNavController(view).navigate(R.id.action_addOfferDetailsFragemnt_to_homeFragment);
                                        } else {
                                            Toast.makeText(getActivity(), "not add", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        });
                    }else {
                        ModelOffers.instance.addOffer(offer, new ModelOffers.addOfferListener() {
                            @Override
                            public void onComplete(int code) {

                                if (code == 200) {
                                    //   Model.instance.Login(userConnected.getUsername(), userConnected.getPassword(), code1 -> { });
                                    Toast.makeText(getActivity(), "added offer", Toast.LENGTH_LONG).show();
                                    Navigation.findNavController(view).navigate(R.id.action_addOfferDetailsFragemnt_to_homeFragment);
                                } else {
                                    Toast.makeText(getActivity(), "not add", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modelauth.instance2.logout(new Modelauth.logout() {
                    @Override
                    public void onComplete(int code) {
                        if (code == 200) {
                            toLoginActivity();
                        }
                    }
                });
            }
        });

        backBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        camra.setOnClickListener(v -> openCam());
        gallery.setOnClickListener(v -> openGallery());

        return view;
    }

    public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

    public boolean checkValidDate() {
        if (!isValidFormat("dd/MM/yyyy", finishdate.getText().toString()) || (finishdate.getText().toString().equals(""))) {
            //Toast.makeText(getContext(), "date is not a date format", Toast.LENGTH_SHORT).show();
            finishdate.setError("date is not a date format");
            return false;
        }
        else if (headline.getText().toString().isEmpty()){
            headline.setError("Headline is required");
            return false;
        }
        else if (profession.getText().toString().isEmpty()){
            profession.setError("Profession is required");
            return false;
        }
        else if (description.getText().toString().isEmpty()){
            description.setError("Description is required");
            return false;
        }
        else if (price.getText().toString().isEmpty() || !(price.getText().toString().matches("^[1-9]{1}(?:[0-9])*?$"))) {
            price.setError("Price is required");
            return false;
        }
        else {
            dateSplitArr = finishdate.getText().toString().split("/" /*<- Regex */);
            if (dateSplitArr[0].length() == 2 && dateSplitArr[1].length() == 2 && dateSplitArr[2].length() == 4) {
                date = dateSplitArr[0] + dateSplitArr[1] + dateSplitArr[2];
                return true;
            } else {
                finishdate.setError("date is not a date format");
                //Toast.makeText(getContext(), "date is not a date format", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private void toLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE_CAPTURE){
            if(resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                profilepic.setImageBitmap(imageBitmap);
            }
        }else if(requestCode==REQUEST_IMAGE_PIC){
            if(resultCode==RESULT_OK){
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                    imageBitmap = BitmapFactory.decodeStream(imageStream);
                    profilepic.setImageBitmap(imageBitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void openGallery() {
        Intent photoPicerIntent = new Intent(Intent.ACTION_PICK);
        photoPicerIntent.setType("image/jpeg");
        startActivityForResult(photoPicerIntent,REQUEST_IMAGE_PIC);
    }

    public void openCam() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
    }


}