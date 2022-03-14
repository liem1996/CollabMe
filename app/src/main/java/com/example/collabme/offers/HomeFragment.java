package com.example.collabme.offers;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.collabme.R;
import com.example.collabme.model.Model;
import com.example.collabme.model.Offer;
import com.example.collabme.model.User;
import com.example.collabme.viewmodel.offersviewmodel;

//


public class HomeFragment extends Fragment {




    MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;
    OnItemClickListener listener;
    ImageView imagePostFrame;
    offersviewmodel viewModel;
    String stUsername;
    String password ;
    boolean influncer ;
    boolean company;
    String age;
    String email;
    String gender;
    String[] palatform ;
    String[] proffesions;
    String followers;
    String postuploads;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(offersviewmodel.class);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        swipeRefresh = view.findViewById(R.id.offers_swiperefresh);
        swipeRefresh.setOnRefreshListener(Model.instance::refreshPostList);

        RecyclerView list = view.findViewById(R.id.offers_rv);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyAdapter();
        list.setAdapter(adapter);

        setHasOptionsMenu(true);
        viewModel.getData().observe(getViewLifecycleOwner(), list1 -> refresh());
        swipeRefresh.setRefreshing(Model.instance.getoffersListLoadingState().getValue() == Model.OffersListLoadingState.loading);
        Model.instance.getoffersListLoadingState().observe(getViewLifecycleOwner(), PostsListLoadingState -> {
            if (PostsListLoadingState == Model.OffersListLoadingState.loading){
                swipeRefresh.setRefreshing(true);
            }else{
                swipeRefresh.setRefreshing(false);
            }

        });


        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view,int idview) {
                String stheadline = viewModel.getData().getValue().get(position).getHeadline();
                String status = viewModel.getData().getValue().get(position).getStatus();
                String date = viewModel.getData().getValue().get(position).getFinishDate();

                Model.instance.getUserById(viewModel.getData().getValue().get(position).getUser(), new Model.GetUserByIdListener() {
                    @Override
                    public void onComplete(User profile) {
                         stUsername = profile.getUsername();
                         password = profile.getPassword();
                         influncer = profile.getInfluencer();
                         company = profile.getCompany();
                         age = profile.getAge();
                         email = profile.getEmail();
                         gender = profile.getSex();
                         palatform = profile.getPlatforms();
                         proffesions = profile.getProfessions();
                         followers =  profile.getFollowers();
                         postuploads =  profile.getNumOfPosts();


                    }
                });
                if(view.findViewById(R.id.fragemnt_item_edit).getId()==idview) {
                   Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToEditProfile(stUsername, password, company, influncer, age,email,gender,palatform,proffesions,followers,postuploads));
                }
                /*
                else if(view.findViewById(R.id.myoffers_listrow_check).getId()==idview){
                    viewModel.deletePost(viewModel.getData().getValue().get(position), () -> {
                       // viewModel.getData().getValue().get(position).setImagePostUrl("0");
                        Model.instance.refreshPostList();


                    });

                 */
                }
                /*
                else if(view.findViewById(R.id.myoffers_listrow_delete).getId()==idview){
                    viewModel.deletePost(viewModel.getData().getValue().get(position), () -> {
                       // viewModel.getData().getValue().get(position).setImagePostUrl("0");
                        Model.instance.refreshPostList();


                    });
                }

                 */

            });

        adapter.notifyDataSetChanged();

        return view;
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }
    //////////////////////////VIEWHOLDER////////////////////////////////////

    class MyViewHolder extends RecyclerView.ViewHolder{
         TextView Offer_date,Offer_status;
        TextView headline_offer,username;
        ImageView imge_x, image_vi,image_offer;
        Button Editview;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            headline_offer=(TextView)itemView.findViewById(R.id.myoffers_listrow_headline);
            Offer_date=(TextView)itemView.findViewById(R.id.myoffers_listrow_date);
            Offer_status=(TextView)itemView.findViewById(R.id.myoffers_listrow_status);
            username=(TextView)itemView.findViewById(R.id.myoffers_listrow_username);
            image_offer =(ImageView)itemView.findViewById(R.id.myoffers_listrow_image);
            image_vi =(ImageView)itemView.findViewById(R.id.myoffers_listrow_check);
            imge_x =(ImageView)itemView.findViewById(R.id.myoffers_listrow_delete);
            Editview = (Button) itemView.findViewById(R.id.fragemnt_item_edit);

            Editview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    int viewid = v.getId();
                    listener.onItemClick(position,itemView,viewid);
                }
            });


            imge_x.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int viewid = v.getId();
                    int position = getAdapterPosition();
                    listener.onItemClick(position,itemView,viewid);
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
            Offer_status.setText(offer.getStatus());


            Model.instance.getUserById(offer.getUser(), new Model.GetUserByIdListener() {
                @Override
                public void onComplete(User profile) {
//                    stUsername = profile.getUsername();
   //                 username.setText(stUsername);

                }
            });
            Editview.setVisibility(View.GONE);


            /*

            Model.instance.getUserConnect(new Model.connect() {
                @Override
                public void onComplete(Profile profile) {
                    if(profile.getEmail().equals(tv_name.getText().toString())){
                        Model.instance.mainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                imgdelete.setVisibility(View.VISIBLE);
                                imgedit.setVisibility(View.VISIBLE);
                                Editview.setVisibility(View.VISIBLE);
                                Deleteview.setVisibility(View.VISIBLE);

                            }
                        });

                    }
                }
            });

             */


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
            View view = getLayoutInflater().inflate(R.layout.offers_list_row,parent,false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Offer post = viewModel.getData().getValue().get(position);
            holder.bind(post);

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