package com.example.collabme.search;

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
import com.facebook.login.LoginManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class fragment_search_results extends Fragment {

    MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;
    OnItemClickListener listener;
    ImageView logout;
    String offerId;
    Offer offer;
    User userConnected;
    FloatingActionButton addOfferBtn;
    Button checkBtn, deleteBtn;
    String usernameConnected = "";
    Offer [] offersFromSearch;
    View view;
    ArrayList<Offer> Offerlist;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_search_results, container, false);

        offersFromSearch = fragment_search_resultsArgs.fromBundle(getArguments()).getSearchoffers();
        Offerlist =new ArrayList<>();
        Offerlist = changetoArrylist(offersFromSearch);
        swipeRefresh = view.findViewById(R.id.fragment_search_results_swiperefresh);

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

        logout = view.findViewById(R.id.fragment_search_results_logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modelauth.instance2.logout(code -> {
                    if (code == 200) {
                        LoginManager.getInstance().logOut();
                        toLoginActivity();
                    }
                });
            }
        });


        RecyclerView list = view.findViewById(R.id.search_results_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        checkBtn = view.findViewById(R.id.myoffers_listrow_check);
        deleteBtn = view.findViewById(R.id.myoffers_listrow_delete);
        adapter = new MyAdapter();
        list.setAdapter(adapter);

        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, int idview) {
                offerId = Offerlist.get(position).getIdOffer();
                offer = Offerlist.get(position);
                String headline = offer.getHeadline();
                int price = offer.getPrice();
                if (view.findViewById(R.id.myoffers_listrow_check).getId() == idview) {
                    offerCheckClicked(position);
                } else if (view.findViewById(R.id.myoffers_listrow_delete).getId() == idview) {
                    updateRejectedOfferArr(offerId);
                } else if (view.findViewById(R.id.fragemnt_item_edit).getId() == idview) {
                    Navigation.findNavController(view).navigate(fragment_search_resultsDirections.actionFragmentSearchResultsToEditOfferFragment(offerId));
                } else {
                    String status = offer.getStatus();
                    switch (status) {
                        case "Open":
                            Navigation.findNavController(view).navigate(fragment_search_resultsDirections.actionFragmentSearchResultsToOfferDetailsFragment(offerId, null));
                            break;
                        case "InProgress":
                            Navigation.findNavController(view).navigate(fragment_search_resultsDirections.actionFragmentSearchResultsToInprogressfragment(offerId));
                            break;
                        case "Close":
                            Navigation.findNavController(view).navigate(fragment_search_resultsDirections.actionFragmentSearchResultsToCloseStatusfragment2(offerId));
                            break;
                        case "Done":
                            Navigation.findNavController(view).navigate(fragment_search_resultsDirections.actionFragmentSearchResultsToDoneStatusFragment(offerId, headline, price));
                            break;
                    }
                }
            }
        });

        refresh();
        setHasOptionsMenu(true);
        swipeRefresh.setRefreshing(ModelOffers.instance.getoffersListLoadingState().getValue() == ModelOffers.OffersListLoadingState.loading);
        ModelOffers.instance.getoffersListLoadingState().observe(getViewLifecycleOwner(), PostsListLoadingState -> {
            if (PostsListLoadingState == ModelOffers.OffersListLoadingState.loading) {
                swipeRefresh.setRefreshing(true);
            } else {
                swipeRefresh.setRefreshing(false);
            }
        });
        adapter.notifyDataSetChanged();

        return view;
    }



    private void updateRejectedOfferArr(String offerId) {
        if (ModelUsers.instance3.getUser() == null) {
            ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
                @Override
                public void onComplete(User profile) {
                    userConnected = profile;
                    ModelUsers.instance3.setUserConnected(profile);
                    updateUserWitnRejectedList(offerId);
                }
            });
        } else {
            updateUserWitnRejectedList(offerId);
        }
    }



    private void updateUserWitnRejectedList(String offerId) {
        ArrayList<String> rejectedArr = userConnected.getRejectedOffers();
        if (rejectedArr == null)
            rejectedArr = new ArrayList<>();
        rejectedArr.add(offerId);
        userConnected.setRejectedOffers(rejectedArr);
        ModelUsers.instance3.EditUser(userConnected, new ModelUsers.EditUserListener() {
            @Override
            public void onComplete(int code) {
                if (code == 200) {
                    Toast.makeText(getActivity(), "user changes saved", Toast.LENGTH_LONG).show();
                    Offerlist.remove(offer);
                    refresh();
                } else {
                    Toast.makeText(getActivity(), "user changes not saved", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void offerCheckClicked(int position) {
        List<String> arrayList = new LinkedList<>();
        if (userConnected != null) {
            usernameConnected = userConnected.getUsername();
        } else {
            getUsernameConnected();
        }

        arrayList = offer.setusersandadd(Offerlist.get(position).getUsers(), usernameConnected);
        offer.setUsers(ChangeToArray(arrayList));
        ModelOffers.instance.editOffer(offer, new ModelOffers.EditOfferListener() {
            @Override
            public void onComplete(int code) {
                if (code == 200) {
                    Toast.makeText(getActivity(), "offer updated!", Toast.LENGTH_LONG).show();
                    Offerlist.remove(offer);
                    refresh();

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
            offer_date.setText(setValidDate(offer.getFinishDate()));
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
                            if (!profile.getUsername().equals(offer.getUser()))
                                offer_edit_imb.setVisibility(View.INVISIBLE);
                            else {
                                offer_V_imb.setVisibility(View.INVISIBLE);
                                offer_X_imb.setVisibility(View.INVISIBLE);
                            }

                            String[] offerCandidates = offer.getUsers();
                            for (int i = 0; i < offerCandidates.length; i++) {
                                if (offerCandidates[i].equals(profile.getUsername())) {
                                    offer_V_imb.setVisibility(View.INVISIBLE);
                                    offer_X_imb.setVisibility(View.INVISIBLE);
                                    break;
                                }
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

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        public void setListener(OnItemClickListener listener1) {
            listener = listener1;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.offers_list_row, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Offer offer = Offerlist.get(position);
            holder.bind(offer);
        }

        @Override
        public int getItemCount() {
            if (Offerlist == null) {
                return 0;
            }
            return Offerlist.size();
        }
    }

    public String[] ChangeToArray(List<String> array) {
        String[] arrayList = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            arrayList[i] = array.get(i);
        }
        return arrayList;
    }

    public ArrayList<Offer> changetoArrylist(Offer[] array) {
        ArrayList<Offer> arrayList = new ArrayList<>(Arrays.asList(array));
        return arrayList;
    }

    public void getUsernameConnected() {
        ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
            @Override
            public void onComplete(User profile) {
                userConnected = profile;
                usernameConnected = profile.getUsername();
            }
        });
    }
}