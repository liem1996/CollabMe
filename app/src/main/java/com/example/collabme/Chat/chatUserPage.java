package com.example.collabme.Chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.example.collabme.Activites.ChatActivity;
import com.example.collabme.Activites.LoginActivity;
import com.example.collabme.R;
import com.example.collabme.model.ModelPhotos;
import com.example.collabme.model.ModelUsers;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.User;
import com.example.collabme.viewmodel.userViewModel;
import com.facebook.login.LoginManager;

//

/**
 *
 * the chat of users to talk to fragment - included :
 * viewholder for the recyclerview in thechat users page
 * viewmodel for the chat to show all the user to talk to
 * refreshpost call from the model for refreshing the users
 * Adapter for the recyclerview items -users  items
 *  navigate to the chat activity which connect to socket to create a conversation
 *
 */


public class chatUserPage extends Fragment {

    MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;
    OnItemClickListener listener;
    userViewModel viewModel;
    String username;
    RecyclerView list;
    ImageView logout;
    String usernametexting;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(userViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatuser_page,container,false);

        logout = view.findViewById(R.id.fragment_users_logoutBtn);
        swipeRefresh = view.findViewById(R.id.users_swiperefresh);
        swipeRefresh.setOnRefreshListener(ModelUsers.instance3::refreshUserstList);

        RecyclerView list = view.findViewById(R.id.users_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyAdapter();
        list.setAdapter(adapter);

        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view,int idview) {
                ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
                    @Override
                    public void onComplete(User profile) {
                        username = profile.getUsername();
                        usernametexting = viewModel.getData().getValue().get(position).getUsername();
                        tochatActivity();
                    }
                });

            }

        });
        refresh();
        setHasOptionsMenu(true);
        viewModel.getData().observe(getViewLifecycleOwner(), list1 -> refresh());
        swipeRefresh.setRefreshing(ModelUsers.instance3.getusersListLoadingState().getValue() == ModelUsers.UserLoadingState.loading);
        ModelUsers.instance3.getusersListLoadingState().observe(getViewLifecycleOwner(), PostsListLoadingState -> {
            if (PostsListLoadingState == ModelUsers.UserLoadingState.loading){
                swipeRefresh.setRefreshing(true);
            }else{
                swipeRefresh.setRefreshing(false);
            }

        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modelauth.instance2.logout(new Modelauth.logout() {
                    @Override
                    public void onComplete(int code) {
                        if (code == 200) {
                            LoginManager.getInstance().logOut();
                            toLoginActivity();
                        }
                    }
                });
            }
        });


        ModelUsers.instance3.refreshUserstList();
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

    private void tochatActivity() {
        Intent intent = new Intent(getContext(), ChatActivity.class);
        intent.putExtra("name",username);
        intent.putExtra("usernametext",usernametexting);
        startActivity(intent);
        getActivity().finish();
    }
    //////////////////////////VIEWHOLDER////////////////////////////////////

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        ImageView user_pic;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username=(TextView)itemView.findViewById(R.id.users__listrow_username);
            user_pic =(ImageView)itemView.findViewById(R.id.row_users_profile);

            itemView.setOnClickListener(v -> {
                int viewId = v.getId();
                int pos = getAdapterPosition();
                listener.onItemClick(pos,v,viewId);

            });


        }
        public void bind(User user){
            username.setText(user.getUsername());

            if (user != null) {
                if (user.getImage() != null) {
                    ModelPhotos.instance3.getimages(user.getImage(), new ModelPhotos.getimagesfile() {
                        @Override
                        public void onComplete(Bitmap responseBody) {
                            if (responseBody != null) {
                                user_pic.setImageBitmap(responseBody);

                            }
                        }
                    });
                }
                else {
                    user_pic.setImageResource(R.drawable.profile);
                }
            }

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
            View view = getLayoutInflater().inflate(R.layout.user_caht_row,parent,false);

            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            User user = viewModel.getData().getValue().get(position);
            holder.bind(user);
        }

        @Override
        public int getItemCount() {
            if(viewModel.getData().getValue() == null){
                return 0;
            }
            return viewModel.getData().getValue().size();
        }
    }


}