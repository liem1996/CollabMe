package com.example.collabme.HomeOffers;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import com.example.collabme.R;
import com.example.collabme.model.ModelOffers;
import com.example.collabme.model.ModelUsers;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.User;
import com.example.collabme.viewmodel.offersviewmodel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MyOffersFragment extends Fragment {

    MyoffersAdapter adapter1;
    SwipeRefreshLayout swipeRefresh;
    OnItemClickListeneroffers listener;
    ImageView imagePostFrame;
    offersviewmodel viewModel;
    String idoffer;
    String stUsername;
    ArrayList<Offer> offers = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(offersviewmodel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_offers,container,false);

        swipeRefresh = view.findViewById(R.id.myoffers_swiperefresh);
        swipeRefresh.setOnRefreshListener(ModelOffers.instance::refreshPostList);

        RecyclerView list2 = view.findViewById(R.id.myoffers_rv);
        list2.setHasFixedSize(true);

        list2.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter1 = new MyoffersAdapter();
        list2.setAdapter(adapter1);

        setHasOptionsMenu(true);
        viewModel.getData().observe(getViewLifecycleOwner(), list4 -> refresh());
        swipeRefresh.setRefreshing(ModelOffers.instance.getoffersListLoadingState().getValue() == ModelOffers.OffersListLoadingState.loading);
        ModelOffers.instance.getoffersListLoadingState().observe(getViewLifecycleOwner(), PostsListLoadingState -> {
            if (PostsListLoadingState == ModelOffers.OffersListLoadingState.loading){
                swipeRefresh.setRefreshing(true);
            }else{
                swipeRefresh.setRefreshing(false);
            }

        });


        adapter1.setListener(new  OnItemClickListeneroffers() {
            @Override
            public void onItemClickoffer(int position, View view,int idview) {
                idoffer = viewModel.getData().getValue().get(position).getIdOffer();

                if(view.findViewById(R.id.fragemnt_item_edit).getId()==idview) {
                    Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToEditOfferFragment(idoffer));
                }

                if(view.findViewById(R.id.myoffers_listrow_check).getId()==idview) {
                    Offer offer =viewModel.getData().getValue().get(position);
                    List<String> arrayList = new LinkedList<>();
                    arrayList= offer.setusersandadd(viewModel.getData().getValue().get(position).getUsers(),viewModel.getData().getValue().get(position).getUser());
                    offer.setUsers(ChangeToArray(arrayList));
                    ModelOffers.instance.editOffer(offer, new ModelOffers.EditOfferListener() {
                        @Override
                        public void onComplete(int code) {
                            if(code==200){
                                Toast.makeText(getActivity(), "yay i did it ", Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(getActivity(), "bozzz off", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

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

        adapter1.notifyDataSetChanged();

        return view;
    }

    private void refresh() {
        adapter1.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }
    //////////////////////////VIEWHOLDER////////////////////////////////////

    class MyViewHolderoffers extends RecyclerView.ViewHolder{
        TextView Offer_date,Offer_status;
        TextView headline_offer,username;
        ImageView imge_x, image_vi,image_offer;
        Button Editview;

        public MyViewHolderoffers(@NonNull View itemView) {
            super(itemView);
            headline_offer=(TextView)itemView.findViewById(R.id.myoffers_listrow_headline);
            Offer_date=(TextView)itemView.findViewById(R.id.myoffers_listrow_date);
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
                    listener.onItemClickoffer(position,itemView,viewid);
                }
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
                    listener.onItemClickoffer(position,itemView,viewid);
                }
            });



        }
        public void bindoffer(Offer offer,int pos,View item){
            headline_offer.setText(offer.getHeadline());
            Offer_date.setText(offer.getFinishDate());
            Offer_status.setText(offer.getStatus());
            username.setText(offer.getUser());
            ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
                @Override
                public void onComplete(User profile) {
                    if(!profile.getUsername().equals(offer.getUser())) {
                        itemView.setVisibility(View.GONE);
                        //adapter1.offers.remove(offer);

                    }
                }
            });

        }
    }


    //////////////////////////MYYYYYYYY APATERRRRRRRR///////////////////////
    interface OnItemClickListeneroffers{
        void onItemClickoffer(int position,View view,int idview);
    }
    class MyoffersAdapter extends RecyclerView.Adapter<MyViewHolderoffers>{
        List<Offer> offers = new LinkedList<>();
        View view;
        public void setListener(OnItemClickListeneroffers listener1) {
            listener = listener1;
        }

        @NonNull
        @Override
        public MyViewHolderoffers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
             view = getLayoutInflater().inflate(R.layout.offers_list_row,parent,false);
            MyViewHolderoffers holder = new MyViewHolderoffers(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolderoffers holder, int position) {
            offers = viewModel.getData().getValue();
            Offer offer = offers.get(position);
            holder.bindoffer(offer,position,view);
        }

        @Override
        public int getItemCount() {
            if(viewModel.getData().getValue() == null){
                return 0;
            }
            return viewModel.getData().getValue().size();
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