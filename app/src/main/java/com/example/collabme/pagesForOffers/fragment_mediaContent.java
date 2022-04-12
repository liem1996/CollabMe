
package com.example.collabme.pagesForOffers;

import static com.example.collabme.objects.MyApplication.getContext;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.collabme.Activites.MediaContentActivity;
import com.example.collabme.R;
import com.example.collabme.model.ModelOffers;
import com.example.collabme.objects.Offer;
import com.example.collabme.status.OpenStatusFragmentArgs;
import com.example.collabme.status.inprogressfragmentDirections;

public class fragment_mediaContent extends Fragment {

    MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;
    String offerId;
    String[] MediaContent;
    Button AgreeBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_media_content, container, false);

        RecyclerView list = view.findViewById(R.id.mediacontent_rv);

        offerId = fragment_mediaContentArgs.fromBundle(getArguments()).getOfferId();

        AgreeBtn = view.findViewById(R.id.fragment_mediacontent_agree);

        ModelOffers.instance.getOfferById(offerId, new ModelOffers.GetOfferListener() {
            @Override
            public void onComplete(Offer offer) {
                MediaContent = offer.getMediaContent();
                list.setHasFixedSize(true);

                list.setLayoutManager(new LinearLayoutManager(getContext()));

                adapter = new MyAdapter();
                list.setAdapter(adapter);

            }
        });

        AgreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(fragment_mediaContentDirections.actionFragmentMediaContentToDoneStatusFragment(offerId));

            }
        });

        return view;
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
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
