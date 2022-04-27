
package com.example.collabme.pagesForOffers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collabme.Activites.LoginActivity;
import com.example.collabme.R;
import com.example.collabme.model.ModelOffers;
import com.example.collabme.model.ModelUsers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.User;

public class fragment_mediaContent extends Fragment {

    MyAdapter adapter;
    String offerId, description;
    int price;
    String[] MediaContent;
    Button AgreeBtn;
    String offerOwner;
    ImageView logout;
    ImageButton backBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_media_content, container, false);

        RecyclerView list = view.findViewById(R.id.mediacontent_rv);

        offerId = fragment_mediaContentArgs.fromBundle(getArguments()).getOfferId();
        description = fragment_mediaContentArgs.fromBundle(getArguments()).getHeadline();
        price = fragment_mediaContentArgs.fromBundle(getArguments()).getPrice();
        logout = view.findViewById(R.id.fragment_mediacontent_logoutBtn);
        backBtn = view.findViewById(R.id.fragment_mediacontent_backBtn);
        AgreeBtn = view.findViewById(R.id.fragment_mediacontent_agree);

        ModelOffers.instance.getOfferById(offerId, new ModelOffers.GetOfferListener() {
            @Override
            public void onComplete(Offer offer) {
                MediaContent = offer.getMediaContent();
                list.setHasFixedSize(true);

                list.setLayoutManager(new LinearLayoutManager(getContext()));

                adapter = new MyAdapter();
                list.setAdapter(adapter);

                offerOwner = offer.getUser();

                ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
                    @Override
                    public void onComplete(User profile) {
                        if (profile.getUsername().equals(offerOwner)) {
                            AgreeBtn.setVisibility(View.VISIBLE);
                            AgreeBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(fragment_mediaContentDirections.actionFragmentMediaContentToDoneStatusFragment(offerId,description,price)));
                        } else {
                            AgreeBtn.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        backBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

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

        return view;
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
    }

    //////////////////////////VIEWHOLDER////////////////////////////////////

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mediaContentURL;
        ImageView URLtypeImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mediaContentURL = (TextView) itemView.findViewById(R.id.mediacontent_listrow_url);
            URLtypeImage = (ImageView) itemView.findViewById(R.id.mediacontent_listrow_socialmedia);
        }


        public void bind(String mediaURL) {
            // adding the image of the shared application of the link
            if (mediaURL.contains("youtu")) {
                URLtypeImage.setImageResource(R.drawable.youtub);
            } else if (mediaURL.contains("facebook")) {
                URLtypeImage.setImageResource(R.drawable.facebook);
            } else if (mediaURL.contains("twitter")) {
                URLtypeImage.setImageResource(R.drawable.twitter);
            } else if (mediaURL.contains("tiktok")) {
                URLtypeImage.setImageResource(R.drawable.tiktok);
            } else if (mediaURL.contains("instagram")) {
                URLtypeImage.setImageResource(R.drawable.instegram);
            }
            mediaContentURL.setText(mediaURL);
        }
    }

    //////////////////////////MYYYYYYYY APATERRRRRRRR///////////////////////

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.fragment_mediacontent_list_row, parent, false);
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
            if (MediaContent == null) {
                return 0;
            }
            return MediaContent.length;
        }
    }

    private void toLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}
