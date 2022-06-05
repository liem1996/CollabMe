package com.example.collabme.HomeOffers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * the home fragment - included :
 * viewholder for the recycleview in the home
 * viewmodel for the offers that the user which is connected open
 * refreshpost call from the model for refreshing the offers
 * Adapter for the recycleview items -offers items
 * check and dismiss of a offers functionalty
 */

public class CompanyHomeFragment extends Fragment {

    MyCompanyHomeOffersAdapter adapter;
    SwipeRefreshLayout swipeRefresh;
    OnItemClickListener listener;
    ImageView logout;
    OffersViewmodel viewModel;
    String offerId, headline;
    Offer offer;
    int price;
    User userConnected = null;
    FloatingActionButton addOfferBtn;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(OffersViewmodel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_home, container, false);

        swipeRefresh = view.findViewById(R.id.offers_swiperefresh);

        if (ModelUsers.instance3.getUser() == null) {
            ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
                @Override
                public void onComplete(User profile) {
                    userConnected = profile;
                    ModelUsers.instance3.setUserConnected(profile);
                    swipeRefresh.setOnRefreshListener(ModelOffers.instance::refreshPostList);
                }
            });
        } else {
            userConnected = ModelUsers.instance3.getUser();
            swipeRefresh.setOnRefreshListener(ModelOffers.instance::refreshPostList);
        }

        logout = view.findViewById(R.id.fragment_CompanyHome_logoutBtn);
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

        addOfferBtn = view.findViewById(R.id.fragment_CompanyHome_addOffer_fab);
        addOfferBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_global_addOfferDetailsFragemnt));

        RecyclerView list = view.findViewById(R.id.offers_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyCompanyHomeOffersAdapter();
        list.setAdapter(adapter);
        viewModel.refreshOffersList();

        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, int idview) {
                offerId = viewModel.getDataMyOffer().getValue().get(position).getIdOffer();
                offer = viewModel.getDataMyOffer().getValue().get(position);
                headline = offer.getHeadline();
                price = offer.getPrice();

                if (view.findViewById(R.id.fragemnt_item_edit).getId() == idview) {
                    Navigation.findNavController(view).navigate(CompanyHomeFragmentDirections.actionCompanyHomeFragmentToEditOfferFragment(offerId));
                } else {
                    String status = offer.getStatus();
                    switch (status) {
                        case "Open":
                            Navigation.findNavController(view).navigate(CompanyHomeFragmentDirections.actionCompanyHomeFragmentToOfferDetailsFragment(offerId, null));
                            break;
                        case "InProgress":
                            Navigation.findNavController(view).navigate(CompanyHomeFragmentDirections.actionCompanyHomeFragmentToInprogressfragment(offerId));
                            break;
                        case "Close":
                            Navigation.findNavController(view).navigate(CompanyHomeFragmentDirections.actionCompanyHomeFragmentToCloseStatusfragment(offerId));
                            break;
                        case "Done":
                            Navigation.findNavController(view).navigate(CompanyHomeFragmentDirections.actionCompanyHomeFragmentToDoneStatusFragment(offerId, headline, price));
                            break;
                    }
                }
            }
        });

        refresh();
        setHasOptionsMenu(true);
        viewModel.getDataMyOffer().observe(getViewLifecycleOwner(), list1 -> refresh());
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
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

//////////////////////////VIEWHOLDER////////////////////////////////////

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView offer_date, offer_status;
        TextView offer_headline, offer_username;
        ImageView offer_X_imb, offer_V_imb, offer_image, offer_image_profile;
        ImageButton offer_edit_imb;

        public MyViewHolder(@NonNull View itemView) {
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
                listener.onItemClick(pos, v, viewId);
            });

            offer_edit_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    int viewid = v.getId();
                    listener.onItemClick(position, itemView, viewid);
                }
            });

            offer_X_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int viewid = v.getId();
                    int position = getAdapterPosition();
                    listener.onItemClick(position, itemView, viewid);
                }
            });

            offer_V_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int viewid = v.getId();
                    int position = getAdapterPosition();
                    listener.onItemClick(position, itemView, viewid);
                }
            });
        }

        public void bind(Offer offer) {
            offer_username.setText(offer.getUser());
            offer_headline.setText(offer.getHeadline());
            if(String.valueOf(offer.getFinishDate()).length() == 7){
                String tmp = String.valueOf(offer.getFinishDate());
                tmp = "0" + tmp;
                offer_date.setText(setValidDate(tmp));
            }else offer_date.setText(setValidDate(String.valueOf(offer.getFinishDate())));
            offer_status.setText(offer.getStatus());

            ModelPhotos.instance3.getimages(offer.getImage(), new ModelPhotos.getimagesfile() {
                @Override
                public void onComplete(Bitmap responseBody) {
                    if (responseBody != null) {
                        offer_image.setImageBitmap(responseBody);
                    }
                    ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
                        @Override
                        public void onComplete(User profile) {
                            if(offer.getStatus().equals("Close")) {
                                offer_edit_imb.setVisibility(View.INVISIBLE);
                            }
                            if (!profile.getUsername().equals(offer.getUser()))
                                //if not the owner of the offer connected
                                offer_edit_imb.setVisibility(View.INVISIBLE);
                            else {
                                //if the owner of the offer connected
                                offer_V_imb.setVisibility(View.INVISIBLE);
                                offer_X_imb.setVisibility(View.INVISIBLE);
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
            });
        }
    }

    private String setValidDate(String date) {
        String newDate = date.substring(0, 2) + "/" + date.substring(2, 4) + "/" + date.substring(4);
        return newDate;
    }

//////////////////////////MYYYYYYYY ADAPTERRRRRRRR///////////////////////

    interface OnItemClickListener {
        void onItemClick(int position, View view, int idview);
    }

    class MyCompanyHomeOffersAdapter extends RecyclerView.Adapter<MyViewHolder> {
        List<Offer> offers = new LinkedList<>();
        View view;

        public void setListener(OnItemClickListener listener1) {
            listener = listener1;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            view = getLayoutInflater().inflate(R.layout.offers_list_row, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            offers = viewModel.getDataMyOffer().getValue();
            Offer offer = offers.get(position);
            holder.bind(offer);
        }

        @Override
        public int getItemCount() {
            if (viewModel.getDataMyOffer().getValue() == null) {
                return 0;
            }
            return viewModel.getDataMyOffer().getValue().size();
        }
    }
}