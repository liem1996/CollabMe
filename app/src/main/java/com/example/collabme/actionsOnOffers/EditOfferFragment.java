package com.example.collabme.actionsOnOffers;

import static android.app.Activity.RESULT_OK;
import static android.graphics.Color.rgb;
import static com.example.collabme.actionsOnOffers.AddOfferFragemnt.isValidFormat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.facebook.login.LoginManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * the edit offer fragment  -fragment that design to edit an offer in the application
 * using dialog for professions
 * setting field with navigation from the offer details and showing on the fragment screen
 * filling several felled including image and calling tha edit offer function and upload image
 *
 */


public class EditOfferFragment extends Fragment {

    EditText headline, description, finishDate, price;
    TextView proposer, profession, status;
    ImageView saveBtn, deleteBtn, logout, camera,gallery,profilepic;
    ImageButton  cancelBtn;
    String oldIdOffer, oldProposer, offerId, date;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] professionArr, oldProfession, chosen, newProfession, dateSplitArr;
    String[] langArray = {"Sport", "Cooking", "Fashion", "Music", "Dance", "Cosmetic", "Travel", "Gaming", "Tech", "Food",
            "Art", "Animals", "Movies", "Photograph", "Lifestyle", "Other"};
    boolean[] selectedProfessions = new boolean[16];
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PIC = 2;
    Bitmap imageBitmap;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_offer, container, false);
        proposer = view.findViewById(R.id.fragment_editOffer_proposer);
        headline = view.findViewById(R.id.fragment_editOffer_headline);
        description = view.findViewById(R.id.fragment_editOffer_description);
        finishDate = view.findViewById(R.id.fragment_editOffer_finishdate);
        status = view.findViewById(R.id.fragment_editOffer_status);
        profession = view.findViewById(R.id.fragment_editOffer_profession);

        price = view.findViewById(R.id.fragment_editOffer_price);

        cancelBtn = view.findViewById(R.id.fragment_editOffer_cancelBtn);
        saveBtn = view.findViewById(R.id.fragment_editOffer_saveBtn);
        newProfession = new String[16];
        offerId = EditOfferFragmentArgs.fromBundle(getArguments()).getOfferId();
        logout = view.findViewById(R.id.fragment_editOffer_logoutBtn);
        camera = view.findViewById(R.id.fragemnt_editoffer_camra2);
        gallery = view.findViewById(R.id.fragemnt_editofferr_gallery2);
        profilepic = view.findViewById(R.id.offer_edit_pic2);
        deleteBtn = view.findViewById(R.id.fragment_editOffer_deleteBtn);
        progressBar = view.findViewById(R.id.fragment_editOffer_progressbar);
        progressBar.setVisibility(View.GONE);
        progressBar.getIndeterminateDrawable().setColorFilter(rgb(132, 80, 160), android.graphics.PorterDuff.Mode.MULTIPLY);

        cancelBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        saveBtn.setOnClickListener(v -> saveOfferDetails(v));

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Delete Offer");
                builder.setMessage("Are you sure you want to delete this offer?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              ModelOffers.instance.getOfferById(offerId, new ModelOffers.GetOfferListener() {
                                  @Override
                                  public void onComplete(Offer offer) {
                                      ModelOffers.instance.deleteoffer(offer, new ModelOffers.deleteoffer() {
                                          @Override
                                          public void onComplete() {
                                              Toast.makeText(getActivity(), "offer deleted", Toast.LENGTH_LONG).show();
                                              Navigation.findNavController(v).navigate(EditOfferFragmentDirections.actionGlobalHomeFragment2(offerId));
                                          }
                                      });
                                  }
                              });
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ModelOffers.instance.getOfferById(offerId, offer -> {
            if (offer != null) {
                oldIdOffer = offer.getIdOffer();
                oldProfession = offer.getProfession();
                oldProposer = offer.getUser();
                String[] strings = oldProfession;
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < strings.length; i++) {
                    sb.append(strings[i]);
                    if (i < strings.length - 1) {
                        sb.append(", ");
                    }
                }
                String str = sb.toString();

                headline.setText(offer.getHeadline());
                description.setText(offer.getDescription());
                if(String.valueOf(offer.getFinishDate()).length() == 7){
                    String tmp = String.valueOf(offer.getFinishDate());
                    tmp = "0" + tmp;
                    finishDate.setText(setValidDate(tmp));
                }else finishDate.setText(setValidDate(String.valueOf(offer.getFinishDate())));
                status.setText(offer.getStatus());
                price.setText(String.valueOf(offer.getPrice()));

                ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
                    @Override
                    public void onComplete(User profile) {
                        if (profile != null) {
                            proposer.setText(profile.getUsername());
                        }
                    }
                });

                professionArr = offer.getProfession();
                if (offer.getImage() != null) {
                    ModelPhotos.instance3.getimages(offer.getImage(), new ModelPhotos.getimagesfile() {
                        @Override
                        public void onComplete(Bitmap responseBody) {

                            if (responseBody != null) {
                                profilepic.setImageBitmap(responseBody);

                            }
                        }
                    });
                }
            }
        });

        profession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Select Professions:");

                builder.setCancelable(false);

                int m = 0;

                for (int k = 0; k < langArray.length; k++) {
                    for (int h = 0; h < professionArr.length; h++) {
                        if (professionArr[h] != null) {
                            if (professionArr[h].equals(langArray[k])) {
                                if (!langList.contains(k)) {
                                    langList.add(k);
                                    Collections.sort(langList);
                                    selectedProfessions[k] = true;
                                }
                            }
                        }
                    }
                }

                builder.setMultiChoiceItems(langArray, selectedProfessions, new DialogInterface.OnMultiChoiceClickListener() {
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
                        chosen = new String[langList.size()];
                        profession.clearComposingText();
                        professionArr = chosen;

                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            chosen[j] = (langArray[langList.get(j)]); //to check again

                        }

                        // set text on textView

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
                        for (int j = 0; j < selectedProfessions.length; j++) {
                            // remove all selection
                            selectedProfessions[j] = false;
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

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                openCam();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                openGallery();
            }
        });

           saveBtn.setOnClickListener(v -> saveOfferDetails(v));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modelauth.instance2.logout(new Modelauth.logout() {
                    @Override
                    public void onComplete(int code) {
                        if (code == 200) {
                            ModelUsers.instance3.setUserConnected(null);
                            LoginManager.getInstance().logOut();
                            toLoginActivity();
                        }
                    }
                });
            }
        });

        return view;
    }

    private String setValidDate(String date) {
        String newDate = date.substring(0, 2) + "/" + date.substring(2, 4) + "/" + date.substring(4);
        return newDate;
    }

    private void saveOfferDetails(View v) {
        if (checkValidDate()) {
            newProfession = chosen;
            String headline2 = headline.getText().toString();
            String description2 = description.getText().toString();
            String finishDate2 = date;
            String status2 = status.getText().toString();
            int price2 = Integer.parseInt(price.getText().toString());
            progressBar.setVisibility(View.VISIBLE);
            Offer offer = new Offer(description2, headline2, Integer.parseInt(finishDate2), price2, oldIdOffer, status2, newProfession, null);


            if (imageBitmap != null) {
                ModelPhotos.instance3.uploadImage(imageBitmap, getActivity(), new ModelPhotos.PostProfilePhoto() {
                    @Override
                    public void onComplete(String uri) {
                        offer.setImage(uri);
                        ModelOffers.instance.editOffer(offer, code -> {
                            if (code == 200) {
                                Toast.makeText(getActivity(), "offer details saved", Toast.LENGTH_LONG).show();
                                ModelOffers.instance.refreshPostList();
                                Navigation.findNavController(v).navigateUp();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getActivity(), "offer details not saved", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }else {
                ModelOffers.instance.editOffer(offer, code -> {
                    if (code == 200) {
                        Toast.makeText(getActivity(), "offer details saved", Toast.LENGTH_LONG).show();
                        ModelOffers.instance.refreshPostList();
                        Navigation.findNavController(v).navigateUp();
                    } else {
                        Toast.makeText(getActivity(), "offer details not saved", Toast.LENGTH_LONG).show();
                    }
                    Log.d("TAG", "new Offer : " + offer);
                });


            }
        }
    }


    public boolean checkValidDate() {
        if (!isValidFormat("dd/MM/yyyy", finishDate.getText().toString()) || (finishDate.getText().toString().equals(""))) {
            //Toast.makeText(getContext(), "date is not a date format", Toast.LENGTH_SHORT).show();
            finishDate.setError("date is not a date format");
            return false;
        }
        else if (headline.getText().toString().isEmpty()){
            headline.setError("Headline is required");
            return false;
        }
        else if (chosen.length==0){
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
            dateSplitArr = finishDate.getText().toString().split("/" /*<- Regex */);
            if (dateSplitArr[0].length() == 2 && dateSplitArr[1].length() == 2 && dateSplitArr[2].length() == 4) {
                date = dateSplitArr[0] + dateSplitArr[1] + dateSplitArr[2];
                return true;
            } else {
                finishDate.setError("date is not a date format");
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
        progressBar.setVisibility(View.GONE);
        Intent photoPicerIntent = new Intent(Intent.ACTION_PICK);
        photoPicerIntent.setType("image/jpeg");
        startActivityForResult(photoPicerIntent,REQUEST_IMAGE_PIC);
    }

    public void openCam() {
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
    }
}