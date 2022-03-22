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

//


public class HomeFragment extends Fragment {

    MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;
    OnItemClickListener listener;
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
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        swipeRefresh = view.findViewById(R.id.offers_swiperefresh);
        swipeRefresh.setOnRefreshListener(ModelOffers.instance::refreshPostList);

        RecyclerView list = view.findViewById(R.id.offers_rv);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyAdapter();
        list.setAdapter(adapter);


        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view,int idview) {
                idoffer = viewModel.getData().getValue().get(position).getIdOffer();

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

                }else if(view.findViewById(R.id.fragemnt_item_edit).getId()==idview) {
                    Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToEditOfferFragment(idoffer));
                } else{
                    Offer offer =viewModel.getData().getValue().get(position);
                    String offerId = offer.getIdOffer();
                    Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToOfferDetailsFragment(offerId));
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

        setHasOptionsMenu(true);
        viewModel.getData().observe(getViewLifecycleOwner(), list1 -> refresh());
        swipeRefresh.setRefreshing(ModelOffers.instance.getoffersListLoadingState().getValue() == ModelOffers.OffersListLoadingState.loading);
        ModelOffers.instance.getoffersListLoadingState().observe(getViewLifecycleOwner(), PostsListLoadingState -> {
            if (PostsListLoadingState == ModelOffers.OffersListLoadingState.loading){
                swipeRefresh.setRefreshing(true);
            }else{
                swipeRefresh.setRefreshing(false);
            }

        });



        //adapter.notifyDataSetChanged();

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
            Offer_status.setText(offer.getStatus());
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
            Offer offer = viewModel.getData().getValue().get(position);
            holder.bind(offer);

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