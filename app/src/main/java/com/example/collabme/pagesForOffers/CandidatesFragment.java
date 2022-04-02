package com.example.collabme.pagesForOffers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collabme.Activites.LoginActivity;
import com.example.collabme.HomeOffers.HomeFragment;
import com.example.collabme.HomeOffers.HomeFragmentDirections;
import com.example.collabme.R;
import com.example.collabme.model.ModelOffers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.User;
import com.example.collabme.viewmodel.offersviewmodel;

import java.util.LinkedList;
import java.util.List;

public class CandidatesFragment extends Fragment {

    ImageView logout;
    CandidatesAdapter adapter;
    offersviewmodel viewModel;
    SwipeRefreshLayout swipeRefresh;
    String offerId;
    OnItemClickListenerUsers listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(offersviewmodel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_candidates, container, false);

        logout = view.findViewById(R.id.fragment_candidates_logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modelauth.instance2.logout(new Modelauth.logout() {
                    @Override
                    public void onComplete(int code) {
                        if(code==200) {
                            toLoginActivity();
                        }
                        else{

                        }
                    }
                });
            }
        });



        offerId = CandidatesFragmentArgs.fromBundle(getArguments()).getOfferId();
        swipeRefresh = view.findViewById(R.id.candidates_swiperefresh);
        swipeRefresh.setOnRefreshListener(()->refreshList());

        RecyclerView list2 = view.findViewById(R.id.candidates_rv);
        list2.setHasFixedSize(true);
        list2.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CandidatesAdapter();
        list2.setAdapter(adapter);

        adapter.setListener(new OnItemClickListenerUsers() {
            @Override
            public void onItemClickUser(int position, View view, int idview) {
                offerId = viewModel.getData().getValue().get(position).getIdOffer();

//                if(view.findViewById(R.id.myoffers_listrow_check).getId()==idview) {
//                    User user =viewModel.getCandidates(offerId).getValue().get(position);
//                    List<String> arrayList = new LinkedList<>();
//                    arrayList= user.setusersandadd(viewModel.getData().getValue().get(position).getUsers(),viewModel.getData().getValue().get(position).getUser());
//                    user.setUsers(ChangeToArray(arrayList));
//
//                }else if(view.findViewById(R.id.fragemnt_item_edit).getId()==idview) {
//                    Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToEditOfferFragment(idoffer));
//                } else{
//                    Offer offer =viewModel.getData().getValue().get(position);
//                    String offerId = offer.getIdOffer();
//                    Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToOfferDetailsFragment(offerId));
//                }

            }
        });

        setHasOptionsMenu(true);
        viewModel.getCandidates(offerId).observe(getViewLifecycleOwner(), list4 -> refresh());
        swipeRefresh.setRefreshing(ModelOffers.instance.candidatesListLoadingState().getValue() == ModelOffers.OffersListLoadingState.loading);
        ModelOffers.instance.candidatesListLoadingState().observe(getViewLifecycleOwner(), PostsListLoadingState -> {
            if (PostsListLoadingState == ModelOffers.OffersListLoadingState.loading){
                swipeRefresh.setRefreshing(true);
            }else{
                swipeRefresh.setRefreshing(false);
            }

        });

        adapter.notifyDataSetChanged();
        return view;

    }

    private void refresh() {
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);

    }

    private void refreshList(){
        viewModel.refreshCandidatesList(offerId);

    }

    //////////////////////////view holder////////////////////////////////////

    class MyViewHolderCandidates extends RecyclerView.ViewHolder {

        TextView username;

        public MyViewHolderCandidates(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.candidates_listrow_username);


        }
        public void bindUser(User user){
            username.setText(user.getUsername());
            Log.d("TAG","username " + username);

        }
    }

    //////////////////////////My Adapter///////
    interface OnItemClickListenerUsers {
        void onItemClickUser(int position, View view, int idview);
    }

    class CandidatesAdapter extends RecyclerView.Adapter<MyViewHolderCandidates> {
        List<User> users = new LinkedList<>();
        View view;

        public void setListener(OnItemClickListenerUsers listener1) {
            listener = listener1;
        }

        @NonNull
        @Override
        public MyViewHolderCandidates onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            view = getLayoutInflater().inflate(R.layout.candidtaes_list_row, parent, false);
            MyViewHolderCandidates holder = new MyViewHolderCandidates(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolderCandidates holder, int position) {
            users = viewModel.getCandidates(offerId).getValue();
            Log.d("TAG", "users" + String.valueOf(users.size()));
            User user = users.get(position);
            Log.d("TAG", "users" + String.valueOf(users.get(position)));
            holder.bindUser(user);
        }

        @Override
        public int getItemCount() {
            if (viewModel.getCandidates(offerId).getValue() == null) {
                return 0;
            }
            Log.d("TAG", String.valueOf(viewModel.getCandidates(offerId).getValue().size()));
            return viewModel.getCandidates(offerId).getValue().size();

        }

        public String [] ChangeToArray(List<String> array){
            String [] arrayList = new String [array.size()];
            for(int i=0;i<array.size();i++){
                arrayList[i]=array.get(i);
            }

            return arrayList;
        }


    }

    private void toLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}