package com.example.collabme.offers;

import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment {


    /*
    //PostViewModel viewModel;
    MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;
    OnItemClickListener listener;
    ImageView imagePostFrame;
    offersviewmodel viewModel;


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
                String stId = viewModel.getData().getValue().get(position).getId();
                String stUsername = viewModel.getData().getValue().get(position).getEmail();
                String status = viewModel.getData().getValue().get(position).getStatus();
                String date = viewModel.getData().getValue().get(position).getDate();
                String url = viewModel.getData().getValue().get(position).getUrlImagePost();
                if(url==null){
                    url="0";
                }

                if(view.findViewById(R.id.row_feed_editpost).getId()==idview) {
                    Navigation.findNavController(view).navigate(fragment_homeDirections.actionHomePage2ToFragmentEditPost(stUsername, date, status, stId, url));
                }else
                if(view.findViewById(R.id.row_feed_deletepost).getId()==idview){
                    viewModel.deletePost(viewModel.getData().getValue().get(position), () -> {
                        viewModel.getData().getValue().get(position).setImagePostUrl("0");
                        Model.instance.refreshPostList();


                    });
                }

            }
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
            Editview = itemView.findViewById(R.id.fragemnt_item_edit);

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

            headline_offer=(TextView)itemView.findViewById(R.id.myoffers_listrow_headline);
            Offer_date=(TextView)itemView.findViewById(R.id.myoffers_listrow_date);
            Offer_status=(TextView)itemView.findViewById(R.id.myoffers_listrow_status);
            username=(TextView)itemView.findViewById(R.id.myoffers_listrow_username);
            image_offer =(ImageView)itemView.findViewById(R.id.myoffers_listrow_image);
            image_vi =(ImageView)itemView.findViewById(R.id.myoffers_listrow_check);
            imge_x =(ImageView)itemView.findViewById(R.id.myoffers_listrow_delete);
            Editview = itemView.findViewById(R.id.fragemnt_item_edit);



            headline_offer.setText(offer.getHeadline());
            Offer_date.setText(offer.get());
            Offer_status.setText(offer.getStatus());
            username.setText(offer.getUser());
            image_offer.setText(offer.getStatus());

            Model.instance.getprofilebyEmail(post.getEmail(), new Model.GetProfileById() {
                @Override
                public void onComplete(Profile profile) {
                    if (post.getEmail().equals(profile.getEmail())) {

                        if(profile.getUrlImage()==null){
                            profile.setUrlImage("0");
                        }

                        Picasso.get().load(profile.getUrlImage()).resize(50, 50)
                                .centerCrop().into(imgview_propic);

                        if (!profile.getUrlImage().equals("0")) {
                            Picasso.get().load(profile.getUrlImage()).into(imgview_propic);
                        }
                        else{
                            Picasso.get().load(R.drawable.profile).into(imgview_propic);
                        }
                    }
                }
            });



            imgdelete.setVisibility(View.GONE);
            imgedit.setVisibility(View.GONE);
            Editview.setVisibility(View.GONE);
            Deleteview.setVisibility(View.GONE);

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

            if(post.getUrlImagePost()==null){
                post.setImagePostUrl("0");
            }
            if (post.getUrlImagePost().equals("0")) {
                Picasso.get()
                        .load(R.drawable.defaultim).resize(250, 180)
                        .centerCrop()
                        .into(imgview_postpic);
            }else{
                Picasso.get()
                        .load(post.getUrlImagePost()).resize(250, 180)
                        .centerCrop()
                        .into(imgview_postpic);
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
            View view = getLayoutInflater().inflate(R.layout.row_feed,parent,false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Post post = viewModel.getData().getValue().get(position);
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

     */





}