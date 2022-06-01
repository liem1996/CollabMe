package com.example.collabme.HomeOffers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.collabme.Activites.LoginActivity;
import com.example.collabme.R;
import com.example.collabme.model.ModelOffers;
import com.example.collabme.model.ModelPhotos;
import com.example.collabme.model.ModelUsers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.User;
import com.example.collabme.viewmodel.OffersViewmodel;
import com.facebook.login.LoginManager;

import java.util.LinkedList;
import java.util.List;

/**
 * the Waiting offers fragment - included :
 * viewholder for the recyclerview in the Waiting offers
 * viewmodel for the offers that the user is been part of the candidates and is in its process
 * refreshpost call from the model for refreshing the offers
 * Adapter for the recyclerview items -offers items
 * radio button for changing between mt offers and waiting offers
 */

public class WaitingOffersFragment extends Fragment {

    WaitingOffersAdapter adapter1;
    SwipeRefreshLayout swipeRefresh;
    OnItemClickListenerWaitingOffers listener;
    ImageView logout;
    OffersViewmodel viewModel;
    String offerId, headline;
    int price;
    Offer offer;
    RadioButton radioButton;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(OffersViewmodel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting_offers, container, false);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioButton = view.findViewById(R.id.radioButton7);
        radioButton.setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.radioButton7:
                        Navigation.findNavController(view).navigate(WaitingOffersFragmentDirections.actionGlobalWaitingOffersFragment());
                        break;
                    case R.id.radioButton6:
                        Navigation.findNavController(view).navigate(WaitingOffersFragmentDirections.actionGlobalMyOffersFragment(offerId));
                        break;
                }
            }
        });
        swipeRefresh = view.findViewById(R.id.myoffers_swiperefresh);
        swipeRefresh.setOnRefreshListener(ModelOffers.instance::refreshPostList);

        logout = view.findViewById(R.id.fragment_myoffers_logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modelauth.instance2.logout(code -> {
                    if (code == 200) {
                        ModelUsers.instance3.setUserConnected(null);
                        LoginManager.getInstance().logOut();
                        toLoginActivity();
                    }
                });
            }
        });

        RecyclerView list2 = view.findViewById(R.id.myoffers_rv);
        list2.setHasFixedSize(true);
        list2.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter1 = new WaitingOffersAdapter();
        list2.setAdapter(adapter1);

        adapter1.setListener(new OnItemClickListenerWaitingOffers() {
            @Override
            public void onItemClickoffer(int position, View view, int idview) {
                offerId = viewModel.getDataWaitingOffer().getValue().get(position).getIdOffer();
                offer = viewModel.getDataWaitingOffer().getValue().get(position);
                headline = viewModel.getDataWaitingOffer().getValue().get(position).getHeadline();
                price = offer.getPrice();
                String status = offer.getStatus();
                switch (status) {
                    case "Open":
                        Navigation.findNavController(view).navigate(WaitingOffersFragmentDirections.actionWaitingOffersFragmentToOfferDetailsFragment(offerId, null));
                        break;
                    case "InProgress":
                        Navigation.findNavController(view).navigate(WaitingOffersFragmentDirections.actionWaitingOffersFragmentToInprogressfragment(offerId));
                        break;
                    case "Close":
                        Navigation.findNavController(view).navigate(WaitingOffersFragmentDirections.actionWaitingOffersFragmentToCloseStatusfragment(offerId));
                        break;
                    case "Done":
                        Navigation.findNavController(view).navigate(WaitingOffersFragmentDirections.actionWaitingOffersFragmentToDoneStatusFragment(offerId, headline, price));
                        break;
                }
            }
        });

        refresh();
        setHasOptionsMenu(true);
        viewModel.getDataWaitingOffer().observe(getViewLifecycleOwner(), list4 -> refresh());
        swipeRefresh.setRefreshing(ModelOffers.instance.getoffersListLoadingState().getValue() == ModelOffers.OffersListLoadingState.loading);
        ModelOffers.instance.getoffersListLoadingState().observe(getViewLifecycleOwner(), PostsListLoadingState -> {
            if (PostsListLoadingState == ModelOffers.OffersListLoadingState.loading) {
                swipeRefresh.setRefreshing(true);
            } else {
                swipeRefresh.setRefreshing(false);
            }
        });

        ModelOffers.instance.refreshPostList();
        return view;
    }

    private void toLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void refresh() {
        adapter1.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

    //////////////////////////VIEWHOLDER////////////////////////////////////

    class MyViewHolderWaitingOffers extends RecyclerView.ViewHolder {
        TextView offer_date, offer_status;
        TextView offer_headline, offer_username;
        ImageView offer_X_imb, offer_V_imb, offer_image, offer_image_profile;
        ImageButton offer_edit_imb;

        public MyViewHolderWaitingOffers(@NonNull View itemView) {
            super(itemView);
            offer_username = (TextView) itemView.findViewById(R.id.myoffers_listrow_username);
            offer_headline = (TextView) itemView.findViewById(R.id.myoffers_listrow_headline_et);
            offer_date = (TextView) itemView.findViewById(R.id.myoffers_listrow_date_et);
            offer_status = (TextView) itemView.findViewById(R.id.myoffers_listrow_status_et);
            offer_image = (ImageView) itemView.findViewById(R.id.myoffers_listrow_image);
            offer_V_imb = (ImageView) itemView.findViewById(R.id.myoffers_listrow_check);
            offer_X_imb = (ImageView) itemView.findViewById(R.id.myoffers_listrow_delete);
            offer_edit_imb = itemView.findViewById(R.id.fragemnt_item_edit);
            offer_image_profile = itemView.findViewById(R.id.row_feed_profile);

            itemView.setOnClickListener(v -> {
                int viewId = v.getId();
                int pos = getAdapterPosition();
                listener.onItemClickoffer(pos, v, viewId);
            });

            offer_edit_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    int viewid = v.getId();
                    listener.onItemClickoffer(position, itemView, viewid);
                }
            });

            offer_X_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int viewid = v.getId();
                    int position = getAdapterPosition();
                    listener.onItemClickoffer(position, itemView, viewid);
                }
            });

            offer_V_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int viewid = v.getId();
                    int position = getAdapterPosition();
                    listener.onItemClickoffer(position, itemView, viewid);
                }
            });
        }

        public void bindoffer(Offer offer, int pos, View item) {
            offer_username.setText(offer.getUser());
            offer_headline.setText(offer.getHeadline());
            if (String.valueOf(offer.getFinishDate()).length() == 7) {
                String tmp = String.valueOf(offer.getFinishDate());
                tmp = "0" + tmp;
                offer_date.setText(setValidDate(tmp));
            } else
                offer_date.setText(setValidDate(String.valueOf(offer.getFinishDate())));
            offer_status.setText(offer.getStatus());
            offer_edit_imb.setVisibility(View.INVISIBLE);
            offer_V_imb.setVisibility(View.INVISIBLE);
            offer_X_imb.setVisibility(View.INVISIBLE);
            ModelPhotos.instance3.getimages(offer.getImage(), new ModelPhotos.getimagesfile() {
                @Override
                public void onComplete(Bitmap responseBody) {
                    if (responseBody != null) {
                        offer_image.setImageBitmap(responseBody);
                    }
                }
            });

            ModelUsers.instance3.getuserbyusername(offer.getUser(), new ModelUsers.GetUserByIdListener() {
                @Override
                public void onComplete(User profile) {
                    if (profile != null) {
                        if (profile.getImage() != null) {
                            ModelPhotos.instance3.getimages(profile.getImage(), new ModelPhotos.getimagesfile() {
                                @Override
                                public void onComplete(Bitmap responseBody) {
                                    if (responseBody != null) {
                                        offer_image_profile.setImageBitmap(responseBody);
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    private String setValidDate(String date) {
        String newDate = date.substring(0, 2) + "/" + date.substring(2, 4) + "/" + date.substring(4);
        return newDate;
    }

//////////////////////////MYYYYYYYY APATERRRRRRRR///////////////////////

    interface OnItemClickListenerWaitingOffers {
        void onItemClickoffer(int position, View view, int idview);
    }

    class WaitingOffersAdapter extends RecyclerView.Adapter<MyViewHolderWaitingOffers> {
        List<Offer> offers = new LinkedList<>();
        View view;

        public void setListener(OnItemClickListenerWaitingOffers listener1) {
            listener = listener1;
        }

        @NonNull
        @Override
        public MyViewHolderWaitingOffers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            view = getLayoutInflater().inflate(R.layout.offers_list_row, parent, false);
            MyViewHolderWaitingOffers holder = new MyViewHolderWaitingOffers(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolderWaitingOffers holder, int position) {
            offers = viewModel.getDataWaitingOffer().getValue();
            Offer offer = offers.get(position);
            holder.bindoffer(offer, position, view);
        }

        @Override
        public int getItemCount() {
            if (viewModel.getDataWaitingOffer().getValue() == null) {
                return 0;
            }
            return viewModel.getDataWaitingOffer().getValue().size();
        }
    }
}
