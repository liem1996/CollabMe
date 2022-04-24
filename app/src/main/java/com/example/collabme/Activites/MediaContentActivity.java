package com.example.collabme.Activites;

import static com.example.collabme.objects.MyApplication.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collabme.R;
import com.example.collabme.model.ModelMediaContent;
import com.example.collabme.model.ModelOffers;
import com.example.collabme.model.ModelUsers;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.User;
import com.example.collabme.search.fragment_search_results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MediaContentActivity extends AppCompatActivity {

    MyAdapter adapter;
    Intent intent;
    String action;
    String type;
    String[] MediaContent;
    String[] offersToSelect;
    boolean[] checkedItems;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] chosenOffers;
    String[] showingOffers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_media_content);

        ViewGroup view = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        // Get intent, action and MIME type
        intent = getIntent();
        action = intent.getAction();
        type = intent.getType();

        ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
            @Override
            public void onComplete(User profile) {
                ModelOffers.instance.getoffersfromuserinCandidates(profile.getUsername(), new ModelOffers.getoffersfromuserinCandidates() {
                    @Override
                    public void onComplete(List<Offer> offer) {
                      //  offersToSelect1 = offer.toArray(new String[0]);
                        offersToSelect = new String[offer.size()];
                        showingOffers = new String[offer.size()];


                        for (int i=0;i<offersToSelect.length; i++)
                        {
                            offersToSelect[i] = offer.get(i).getIdOffer();
                            showingOffers[i] = offer.get(i).getHeadline();
                        }
                        // should be the offer size
                        checkedItems = new boolean[offersToSelect.length];

                        // Initialize alert dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(MediaContentActivity.this);

                        // set title
                        builder.setTitle("Select an offer:");

                        // set dialog non cancelable
                        builder.setCancelable(false);

                        builder.setMultiChoiceItems(showingOffers, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {

                            int count =0; // counter to limit number of selection (only one can be chosen)

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                // check condition
                                if (b) {
                                    count++;
                                    // when checkbox selected
                                    // Add position in lang list
                                    langList.add(i);
                                    // Sort array list
                                    Collections.sort(langList);
                                    if (count>1){
                                        Toast.makeText(MediaContentActivity.this, "You selected too many.", Toast.LENGTH_SHORT).show();
                                        count--;
                                    }
                                        /*
                                        final boolean[] selected = new boolean[25];

builder.setMultiChoiceItems(R.array.values, selected, new DialogInterface.OnMultiChoiceClickListener() {
    int count = 0;
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        count += isChecked ? 1 : -1;
        selected[which] = isChecked;

        if (count > 5) {
            Toast.makeText(getActivity(), "You selected too many.", Toast.LENGTH_SHORT).show();
            selected[which] = false;
            count--;
            ((AlertDialog) dialog).getListView().setItemChecked(which, false);
        }
    }

});
                                         */
                                } else {
                                    // when checkbox unselected
                                    // Remove position from langList
                                    langList.remove(Integer.valueOf(i));
                                }
                            }
                        });

                        if (showingOffers.length!=0) {
                            builder.setPositiveButton("Choose", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Initialize string builder
                                    StringBuilder stringBuilder = new StringBuilder();
                                    chosenOffers = new String[langList.size()];

                                    // use for loop
                                    for (int j = 0; j < langList.size(); j++) {
                                        // concat array value
                                        stringBuilder.append(offersToSelect[langList.get(j)]);

                                        chosenOffers[j] = (offersToSelect[langList.get(j)]); //to check again

                                    }
                                    if (chosenOffers.length==1) {
                                        ModelOffers.instance.getOfferById(chosenOffers[0], new ModelOffers.GetOfferListener() {
                                            @Override
                                            public void onComplete(Offer offer) {
                                                MediaContent = offer.getMediaContent();
                                                increaseSize();
                                                if (Intent.ACTION_SEND.equals(action) && type != null) {
                                                    if ("text/plain".equals(type)) {
                                                        String sharedText1 = intent.getStringExtra(Intent.EXTRA_TEXT);
                                                        if (sharedText1 != null) {
                                                            MediaContent[MediaContent.length - 1] = (sharedText1);
                                                            ModelMediaContent.instance.addMediaContent(chosenOffers[0], MediaContent, new ModelMediaContent.addMediaContentListener() {
                                                                @Override
                                                                public void onComplete(int code) {
                                                                    if (code == 200) {
                                                                        System.out.println("add media content done successfully");
                                                                    }
                                                                }
                                                            });
                                                        }
                                                        RecyclerView list = view.findViewById(R.id.mediacontent_rv);
                                                        list.setHasFixedSize(true);

                                                        list.setLayoutManager(new LinearLayoutManager(getContext()));

                                                        adapter = new MyAdapter();
                                                        list.setAdapter(adapter);
                                                    }
                                                }
                                            }
                                        });
                                    }
                                    else{
                                   //     ((AlertDialog) dialogInterface).getListView().setItemChecked(i, false);
                                        return;
                                    }
                                }
                            });
                        }
                        // show dialog
                        builder.show();
                    }
                });

            }


        });
        //...
    }

    public void increaseSize() {
        String[] temp = new String[MediaContent.length + 1];

        for (int i = 0; i < MediaContent.length; i++){
            temp[i] = MediaContent[i];
        }
        MediaContent = temp;
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
    }

    //////////////////////////VIEWHOLDER////////////////////////////////////

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mediaContentURL;
        ImageView URLtypeImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mediaContentURL=(TextView)itemView.findViewById(R.id.mediacontent_listrow_url);
            URLtypeImage = (ImageView)itemView.findViewById(R.id.mediacontent_listrow_socialmedia);


        }


        public void bind(String mediaURL){
            // adding the image of the shared application of the link
            if (mediaURL.contains("youtu")){
                URLtypeImage.setImageResource(R.drawable.youtub);
            }
            else if (mediaURL.contains("facebook")){
                URLtypeImage.setImageResource(R.drawable.facebook);
            }
            else if (mediaURL.contains("twitter")){
                URLtypeImage.setImageResource(R.drawable.twitter);
            }
            else if (mediaURL.contains("tiktok")){
                URLtypeImage.setImageResource(R.drawable.tiktok);
            }
            else if (mediaURL.contains("instagram")){
                URLtypeImage.setImageResource(R.drawable.instegram);
            }
            mediaContentURL.setText(mediaURL);

        }
    }

    //////////////////////////MYYYYYYYY APATERRRRRRRR///////////////////////

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.fragment_mediacontent_list_row,parent,false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            String str = MediaContent[position];
            holder.bind(str);

        }

        @Override
        public int getItemCount() {
            if(MediaContent == null){
                return 0;
            }
            return MediaContent.length;
        }
    }

}