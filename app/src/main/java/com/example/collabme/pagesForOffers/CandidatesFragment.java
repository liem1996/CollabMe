package com.example.collabme.pagesForOffers;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.collabme.R;
import com.example.collabme.model.ModelCandidates;
import com.example.collabme.objects.User;
import com.example.collabme.viewmodel.Candidatesviewmodel;

public class CandidatesFragment extends Fragment {

    ImageView logout;
    MyAdapter adapter;
    Candidatesviewmodel viewModel;
    String offerId;
    OnItemClickListener listener;
    SwipeRefreshLayout swipeRefresh;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(Candidatesviewmodel.class);

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


        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view,int idview) {
                String url = viewModel.getCandidates(offerId).getValue().get(position).getUsername();



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
        TextView username;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            username = itemView.findViewById(R.id.candidates_listrow_username);


        }
        public void bind(User user){
            username.setText(user.getUsername());

        }
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