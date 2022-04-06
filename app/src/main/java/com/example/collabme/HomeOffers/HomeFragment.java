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
import com.facebook.login.LoginManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {

    MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;
    OnItemClickListener listener;
    ImageView imagePostFrame, logout;
    offersviewmodel viewModel;
    String idoffer;
    String stUsername;
    ArrayList<Offer> offers = new ArrayList<>();
    FloatingActionButton addOfferBtn;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(offersviewmodel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        swipeRefresh = view.findViewById(R.id.offers_swiperefresh);
        ModelOffers.instance.setPostListForHomeFragment();
        swipeRefresh.setOnRefreshListener(ModelOffers.instance::refreshPostList);

        logout = view.findViewById(R.id.fragment_home_logoutBtn);
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

        addOfferBtn = view.findViewById(R.id.fragment_home_addOffer_fab);
        addOfferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_global_addOfferDetailsFragemnt);
            }
        });

        RecyclerView list = view.findViewById(R.id.offers_rv);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyAdapter();
        list.setAdapter(adapter);

        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, int idview) {
                idoffer = viewModel.getDataHome().getValue().get(position).getIdOffer();

                if (view.findViewById(R.id.myoffers_listrow_check).getId() == idview) {
                    Offer offer = viewModel.getDataHome().getValue().get(position);
                    List<String> arrayList = new LinkedList<>();
                    arrayList = offer.setusersandadd(viewModel.getDataHome().getValue().get(position).getUsers(), viewModel.getDataHome().getValue().get(position).getUser());
                    offer.setUsers(ChangeToArray(arrayList));
                    ModelOffers.instance.editOffer(offer, new ModelOffers.EditOfferListener() {
                        @Override
                        public void onComplete(int code) {
                            if (code == 200) {
                                Toast.makeText(getActivity(), "yay i did it ", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), "bozzz off", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else if (view.findViewById(R.id.fragemnt_item_edit).getId() == idview) {
                    Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToEditOfferFragment(idoffer));
                } else {
                    Offer offer = viewModel.getDataHome().getValue().get(position);
                    String offerId = offer.getIdOffer();
                    Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToOfferDetailsFragment(offerId));
                }
                /*
                else if(view.findViewById(R.id.myoffers_listrow_check).getId()==idview){
                    viewModel.deletePost(viewModel.getDataHome().getValue().get(position), () -> {
                       // viewModel.getDataHome().getValue().get(position).setImagePostUrl("0");
                        Model.instance.refreshPostList();


                    });

                 */
            }
                /*
                else if(view.findViewById(R.id.myoffers_listrow_delete).getId()==idview){
                    viewModel.deletePost(viewModel.getDataHome().getValue().get(position), () -> {
                       // viewModel.getDataHome().getValue().get(position).setImagePostUrl("0");
                        Model.instance.refreshPostList();


                    });
                }

                 */

        });

        setHasOptionsMenu(true);
        viewModel.getDataHome().observe(getViewLifecycleOwner(), list1 -> refresh());
        swipeRefresh.setRefreshing(ModelOffers.instance.getoffersListLoadingState().getValue() == ModelOffers.OffersListLoadingState.loading);
        ModelOffers.instance.getoffersListLoadingState().observe(getViewLifecycleOwner(), PostsListLoadingState -> {
            if (PostsListLoadingState == ModelOffers.OffersListLoadingState.loading) {
                swipeRefresh.setRefreshing(true);
            } else {
                swipeRefresh.setRefreshing(false);
            }
        });


        //adapter.notifyDataSetChanged();

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
        ImageView offer_X_imb, offer_V_imb, offer_image;
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
                    itemView.setVisibility(View.GONE);
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

            ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
                @Override
                public void onComplete(User profile) {
                    if (!profile.getUsername().equals(offer.getUser()))
                        offer_edit_imb.setVisibility(View.INVISIBLE);
                    else {
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
            Offer offer = viewModel.getDataHome().getValue().get(position);
            holder.bind(offer);

        }

        @Override
        public int getItemCount() {
            if (viewModel.getDataHome().getValue() == null) {
                return 0;
            }
            return viewModel.getDataHome().getValue().size();
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