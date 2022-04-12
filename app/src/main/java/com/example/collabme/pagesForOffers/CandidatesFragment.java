package com.example.collabme.pagesForOffers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.example.collabme.model.ModelCandidates;
import com.example.collabme.model.ModelOffers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.User;
import com.example.collabme.viewmodel.CandidatesViewmodel;
import com.facebook.login.LoginManager;

public class CandidatesFragment extends Fragment {

    ImageView logout;
    MyAdapter adapter;
    CandidatesViewmodel viewModel;
    String offerId;
    OnItemClickListener listener;
    SwipeRefreshLayout swipeRefresh;
    Button choosen;
    CheckBox choosenCandidate;
    TextView username;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(CandidatesViewmodel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidates,container,false);

        offerId = CandidatesFragmentArgs.fromBundle(getArguments()).getOfferId();

        swipeRefresh = view.findViewById(R.id.candidates_swiperefresh);
        swipeRefresh.setOnRefreshListener(() -> ModelCandidates.instance2.refreshPostList(offerId));

        RecyclerView list = view.findViewById(R.id.candidates_rv);
        list.setHasFixedSize(true);
        choosen = view.findViewById(R.id.fragemnt_candidates_choosen);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyAdapter();
        list.setAdapter(adapter);

        setHasOptionsMenu(true);
        viewModel.getCandidates(offerId).observe(getViewLifecycleOwner(), list1 -> refresh());
        swipeRefresh.setRefreshing(ModelCandidates.instance2.getcandidateslistloding().getValue() == ModelCandidates.candidatelistloding.loading);
        ModelCandidates.instance2.getcandidateslistloding().observe(getViewLifecycleOwner(), PostsListLoadingState -> {
            if (PostsListLoadingState == ModelCandidates.candidatelistloding.loading){
                swipeRefresh.setRefreshing(true);
            }else{
                swipeRefresh.setRefreshing(false);
            }

        });

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



        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view,int idview) {


            }
        });


        choosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choosenCandidate.isChecked()) {
                    ModelOffers.instance.getOfferById(offerId, new ModelOffers.GetOfferListener() {
                        @Override
                        public void onComplete(Offer offer) {
                            offer.setStatus("InProgress");
                            String []  user = new String[1];
                            user[0]=username.getText().toString();
                            offer.setUsers(user);
                            ModelOffers.instance.editOffer(offer, new ModelOffers.EditOfferListener() {
                                @Override
                                public void onComplete(int code) {
                                    if (code == 200) {
                                        Navigation.findNavController(v).navigate(CandidatesFragmentDirections.actionCandidatesFragmentToInprogressfragment(offerId));
                                    }
                                }
                            });
                        }
                    });

                }else{
                    Toast.makeText(getActivity(), "you didnt choose candidate", Toast.LENGTH_LONG).show();
                }
            }
        });
        refresh();

        return view;
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }
    //////////////////////////VIEWHOLDER////////////////////////////////////

    class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            username = itemView.findViewById(R.id.candidates_listrow_username);
            choosenCandidate= itemView.findViewById(R.id.candidates_listrow_checkBox);
            itemView.setOnClickListener(v -> {
                int viewId = v.getId();
                int pos = getAdapterPosition();
                listener.onItemClick(pos, v, viewId);
            });
            choosenCandidate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int viewid = v.getId();
                    int position = getAdapterPosition();

                    listener.onItemClick(position, itemView, viewid);
                }
            });

        }
        public void bind(User user){
            username.setText(user.getUsername());
            choosenCandidate.setChecked(false);

        }
    }

    private void toLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    //////////////////////////MYYYYYYYY APATERRRRRRRR///////////////////////
    interface OnItemClickListener{
        void onItemClick(int position,View view,int idview);
    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        public void setListener(OnItemClickListener listener1) {
            listener = listener1;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.candidtaes_list_row,parent,false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            User post = viewModel.getCandidates(offerId).getValue().get(position);
            holder.bind(post);

        }

        @Override
        public int getItemCount() {
            if(viewModel.getCandidates(offerId).getValue() == null){
                return 0;
            }
            return viewModel.getCandidates(offerId).getValue().size();
        }
    }



}