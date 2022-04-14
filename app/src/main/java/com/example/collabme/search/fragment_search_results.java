package com.example.collabme.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

import java.util.LinkedList;
import java.util.List;


public class fragment_search_results extends Fragment {

    Offer[] offersFromSearch;
    MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;
    String idoffer;

    OnItemClickListener listener;
    String stUsername;
    ImageView logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        offersFromSearch = fragment_search_resultsArgs.fromBundle(getArguments()).getSearchoffers();

        logout = view.findViewById(R.id.fragment_search_results_logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modelauth.instance2.logout(new Modelauth.logout() {
                    @Override
                    public void onComplete(int code) {
                        if(code==200) {
                            toLoginActivity();
                        }
                    }
                });
            }
        });


        //////////////////////////////////////////////////////////////////////////

     //   swipeRefresh = view.findViewById(R.id.fragment_search_results_swiperefresh);
      //  swipeRefresh.setOnRefreshListener(ModelOffers.instance::refreshPostList);

        RecyclerView list = view.findViewById(R.id.search_results_rv);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new fragment_search_results.MyAdapter();
        list.setAdapter(adapter);

        adapter.setListener(new fragment_search_results.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view,int idview) {
                idoffer = offersFromSearch[position].getIdOffer();

                if(view.findViewById(R.id.fragment_search_results_check).getId()==idview) {

                    Offer offer =offersFromSearch[position];
                    List<String> arrayList = new LinkedList<>();

                    arrayList= offer.setusersandadd(offersFromSearch[position].getUsers(),
                            offersFromSearch[position].getUser());

                    offer.setUsers(ChangeToArray(arrayList));

                    ModelOffers.instance.editOffer(offer, new ModelOffers.EditOfferListener() {
                        @Override
                        public void onComplete(int code) {
                            if(code==200){
                                Toast.makeText(getActivity(), "Edit offer succeded", Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(getActivity(), "Edit offer not succeded", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }else if(view.findViewById(R.id.fragment_search_results_edit).getId()==idview) {
                //    Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToEditOfferFragment(idoffer));
                } else{
                    Offer offer =offersFromSearch[position];
                    String offerId = offer.getIdOffer();
                 //   Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToOfferDetailsFragment(offerId));
                }
            }


        });

        setHasOptionsMenu(true);
        /*
        viewModel.getOfferFromFreeSearch(freeSearch1).observe(getViewLifecycleOwner(), list1 -> refresh());

        swipeRefresh.setRefreshing(ModelOffers.instance.getoffersListLoadingState().getValue() == ModelOffers.OffersListLoadingState.loading);
        ModelOffers.instance.getoffersListLoadingState().observe(getViewLifecycleOwner(), PostsListLoadingState -> {
            if (PostsListLoadingState == ModelOffers.OffersListLoadingState.loading){
                swipeRefresh.setRefreshing(true);
            }else{
                swipeRefresh.setRefreshing(false);
            }

        });*/

        //////////////////////////////////////////////////////////////////////////

        return view;
    }

    private void toLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
    }

    //////////////////////////VIEWHOLDER////////////////////////////////////

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Offer_date,Offer_status;
        TextView headline_offer,username;
        ImageView imge_x, image_vi,image_offer;
        ImageView Editview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            headline_offer=(TextView)itemView.findViewById(R.id.fragment_search_results_headline_et);
            Offer_date=(TextView)itemView.findViewById(R.id.fragment_search_results_date);
            username=(TextView)itemView.findViewById(R.id.fragment_search_results_username);
            image_offer =(ImageView)itemView.findViewById(R.id.fragment_search_results_offerimage);
            image_vi =(ImageView)itemView.findViewById(R.id.fragment_search_results_check);
            imge_x =(ImageView)itemView.findViewById(R.id.fragment_search_results_delete);
            Editview = itemView.findViewById(R.id.fragment_search_results_edit);

            Editview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    int viewid = v.getId();
                    listener.onItemClick(position,itemView,viewid);
                }
            });

            itemView.setOnClickListener(v -> {
                int viewId = v.getId();

                int pos = getAdapterPosition();
                listener.onItemClick(pos,v,viewId);

            });


            imge_x.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int viewid = v.getId();
                    int position = getAdapterPosition();
                    itemView.setVisibility(View.GONE);
                }
            });
            image_vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int viewid = v.getId();
                    int position = getAdapterPosition();
                    listener.onItemClick(position,itemView,viewid);
                }
            });



        }
        public void bind(Offer offer){
            headline_offer.setText(offer.getHeadline());
            Offer_date.setText(offer.getFinishDate());
            ModelUsers.instance3.getuserbyusername(offer.getUser(), new ModelUsers.GetUserByIdListener() {
                @Override
                public void onComplete(User profile) {
                    if(profile==null){
                        ModelOffers.instance.deleteoffer(offer, new ModelOffers.deleteoffer() {
                            @Override
                            public void onComplete() {
                                refresh();
                            }
                        });
                    }else{
                        stUsername = profile.getUsername();
                        username.setText(stUsername);
                    }

                }
            });
        }
    }

    //////////////////////////MYYYYYYYY APATERRRRRRRR///////////////////////
    interface OnItemClickListener{
        void onItemClick(int position,View view,int idview);
    }
    class MyAdapter extends RecyclerView.Adapter<fragment_search_results.MyViewHolder>{

        public void setListener(fragment_search_results.OnItemClickListener listener1) {
            listener = listener1;
        }

        @NonNull
        @Override
        public fragment_search_results.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.offers_search_results_list_row,parent,false);
            fragment_search_results.MyViewHolder holder = new fragment_search_results.MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull fragment_search_results.MyViewHolder holder, int position) {
            Offer offer = offersFromSearch[position];
            holder.bind(offer);

        }

        @Override
        public int getItemCount() {
            if(offersFromSearch == null){
                return 0;
            }
            return offersFromSearch.length;
        }
    }

    public String [] ChangeToArray(List<String> array){
        String [] arrayList = new String [array.size()];
        for(int i=0;i<array.size();i++){
            arrayList[i]=array.get(i);
        }

        return arrayList;
    }

}