package com.example.collabme.HomeOffers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.collabme.model.ModelUsers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.User;
import com.example.collabme.viewmodel.offersviewmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class MyOffersFragment extends Fragment {

    MyoffersAdapter adapter1;
    SwipeRefreshLayout swipeRefresh;
    OnItemClickListeneroffers listener;
    ImageView imagePostFrame, logout;
    offersviewmodel viewModel;
    String offerId;
    Offer offer;
    ArrayList<Offer> offers = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(offersviewmodel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_offers, container, false);

        swipeRefresh = view.findViewById(R.id.myoffers_swiperefresh);
        swipeRefresh.setOnRefreshListener(ModelOffers.instance::refreshPostList);

        logout = view.findViewById(R.id.fragment_myoffers_logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modelauth.instance2.logout(code -> {
                    if (code == 200) {
                        toLoginActivity();
                    }
                });
            }
        });

        RecyclerView list2 = view.findViewById(R.id.myoffers_rv);
        list2.setHasFixedSize(true);
        list2.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter1 = new MyoffersAdapter();
        list2.setAdapter(adapter1);

        adapter1.setListener(new OnItemClickListeneroffers() {
            @Override
            public void onItemClickoffer(int position, View view, int idview) {
                offerId = viewModel.getDataMyOffer().getValue().get(position).getIdOffer();
                offer = viewModel.getDataMyOffer().getValue().get(position);

                if (view.findViewById(R.id.myoffers_listrow_check).getId() == idview) {
                    offerCheckClicked(position);
                } else if (view.findViewById(R.id.myoffers_listrow_delete).getId() == idview) {
                    List<Offer> homeOfferLst = viewModel.getDataMyOffer().getValue();
                    homeOfferLst.remove(offer);
                    ModelOffers.instance.refreshPostList();
                } else if (view.findViewById(R.id.fragemnt_item_edit).getId() == idview) {
                    Navigation.findNavController(view).navigate(MyOffersFragmentDirections.actionMyOffersFragmentToEditOfferFragment(offerId));
                } else {
                    String status = offer.getStatus();
                    switch (status) {
                        case "Open":
                            Navigation.findNavController(view).navigate(MyOffersFragmentDirections.actionMyOffersFragmentToOfferDetailsFragment(offerId));
                            break;
                        case "InProgress":
                            Navigation.findNavController(view).navigate(MyOffersFragmentDirections.actionMyOffersFragmentToInprogressfragment(offerId));
                            break;
                        case "Close":
                            Navigation.findNavController(view).navigate(MyOffersFragmentDirections.actionMyOffersFragmentToCloseStatusfragment(offerId));
                            break;
                        case "Done":
                            Navigation.findNavController(view).navigate(MyOffersFragmentDirections.actionMyOffersFragmentToDoneStatusFragment(offerId));
                            break;
                    }
                }
            }
        });

        refresh();
        setHasOptionsMenu(true);
        viewModel.getDataMyOffer().observe(getViewLifecycleOwner(), list4 -> refresh());
        swipeRefresh.setRefreshing(ModelOffers.instance.getoffersListLoadingState().getValue() == ModelOffers.OffersListLoadingState.loading);
        ModelOffers.instance.getoffersListLoadingState().observe(getViewLifecycleOwner(), PostsListLoadingState -> {
            if (PostsListLoadingState == ModelOffers.OffersListLoadingState.loading) {
                swipeRefresh.setRefreshing(true);
            } else {
                swipeRefresh.setRefreshing(false);
            }
        });

        //adapter1.notifyDataSetChanged();

        return view;
    }

    private void offerCheckClicked(int position) {
        List<String> arrayList = new LinkedList<>();
        String userConnected = ModelUsers.instance3.getUser().getUsername();
        arrayList = offer.setusersandadd(viewModel.getDataMyOffer().getValue().get(position).getUsers(), userConnected);
        offer.setUsers(ChangeToArray(arrayList));
        ModelOffers.instance.editOffer(offer, new ModelOffers.EditOfferListener() {
            @Override
            public void onComplete(int code) {
                if (code == 200) {
                    Toast.makeText(getActivity(), "offer updated!", Toast.LENGTH_LONG).show();
                    ModelOffers.instance.refreshPostList();
                } else {
                    Toast.makeText(getActivity(), "error updating offer!", Toast.LENGTH_LONG).show();
                }
            }
        });
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

    class MyViewHolderoffers extends RecyclerView.ViewHolder {
        TextView offer_date, offer_status;
        TextView offer_headline, offer_username;
        ImageView offer_X_imb, offer_V_imb, offer_image;
        ImageButton offer_edit_imb;

        public MyViewHolderoffers(@NonNull View itemView) {
            super(itemView);
            offer_username = (TextView) itemView.findViewById(R.id.myoffers_listrow_username);
            offer_headline = (TextView) itemView.findViewById(R.id.myoffers_listrow_headline_et);
            offer_date = (TextView) itemView.findViewById(R.id.myoffers_listrow_date_et);
            offer_status = (TextView) itemView.findViewById(R.id.myoffers_listrow_status_et);
            offer_image = (ImageView) itemView.findViewById(R.id.myoffers_listrow_image);
            offer_V_imb = (ImageView) itemView.findViewById(R.id.myoffers_listrow_check);
            offer_X_imb = (ImageView) itemView.findViewById(R.id.myoffers_listrow_delete);
            offer_edit_imb = itemView.findViewById(R.id.fragemnt_item_edit);

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
            offer_date.setText(setValidDate(offer.getFinishDate()));
            offer_status.setText(offer.getStatus());

            ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
                @Override
                public void onComplete(User profile) {
                    if (!profile.getUsername().equals(offer.getUser())) {
                        //adapter1.offers.remove(offer);
                        offer_edit_imb.setVisibility(View.INVISIBLE);
                    } else {
                        offer_V_imb.setVisibility(View.INVISIBLE);
                        offer_X_imb.setVisibility(View.INVISIBLE);
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

    interface OnItemClickListeneroffers {
        void onItemClickoffer(int position, View view, int idview);
    }

    class MyoffersAdapter extends RecyclerView.Adapter<MyViewHolderoffers> {
        List<Offer> offers = new LinkedList<>();
        View view;

        public void setListener(OnItemClickListeneroffers listener1) {
            listener = listener1;
        }

        @NonNull
        @Override
        public MyViewHolderoffers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            view = getLayoutInflater().inflate(R.layout.offers_list_row, parent, false);
            MyViewHolderoffers holder = new MyViewHolderoffers(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolderoffers holder, int position) {
            offers = viewModel.getDataMyOffer().getValue();
            Offer offer = offers.get(position);
            holder.bindoffer(offer, position, view);
        }

        @Override
        public int getItemCount() {
            if (viewModel.getDataMyOffer().getValue() == null) {
                return 0;
            }
            return viewModel.getDataMyOffer().getValue().size();
        }
    }

    public String[] ChangeToArray(List<String> array) {
        String[] arrayList = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            arrayList[i] = array.get(i);
        }
        return arrayList;
    }
}