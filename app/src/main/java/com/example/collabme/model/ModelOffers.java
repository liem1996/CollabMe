package com.example.collabme.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.collabme.objects.MyApplication;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.RetrofitInterface;
import com.example.collabme.objects.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModelOffers {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:4000";
    public static final ModelOffers instance = new ModelOffers();
    MutableLiveData<OffersListLoadingState> offersListLoadingState = new MutableLiveData<OffersListLoadingState>();
    MutableLiveData<List<Offer>> offersList = new MutableLiveData<List<Offer>>();
    MutableLiveData<OffersListLoadingState> candidatesListLoadingState = new MutableLiveData<OffersListLoadingState>();
    MutableLiveData<List<User>> candidatesList = new MutableLiveData<List<User>>();


    /**
     *
     *
     * interfaces
     */

    public interface addOfferListener{
        void onComplete(int code);
    }


    public enum OffersListLoadingState{
        loading,
        loaded
    }

    public interface GetOfferListener{
        void onComplete(Offer offer);

    }
    public interface EditOfferListener{
        void onComplete(int code);

    }


    public interface deleteoffer{
        void onComplete();

    }

    public ModelOffers() {
        offersListLoadingState.setValue(OffersListLoadingState.loaded);;
    }


    /**
     *
     * the section of the offers
     * add
     * edit
     * get
     * refreshomepage
     */

    public LiveData<OffersListLoadingState> getoffersListLoadingState() {
        return offersListLoadingState;
    }

    public LiveData<List<Offer>> getAll(){
        if (offersList.getValue() == null) { refreshPostList(); };
        return  offersList;
    }
    public void refreshPostList(){
        offersListLoadingState.setValue(OffersListLoadingState.loading);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");


        Call<List<Offer>> call = retrofitInterface.getoffers("Bearer " + tockenacsses);
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if (response.code() == 200) {
                    List<Offer> stList = response.body();
                    offersList.postValue(stList);
                    offersListLoadingState.postValue(OffersListLoadingState.loaded);

                }
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                offersList.postValue(null);
            }
        });


    }


    public void addOffer(Offer offer, ModelOffers.addOfferListener addOffer) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("Description", offer.getDescription());
        map.put("HeadLine", offer.getHeadline());
        map.put("Price", offer.getPrice());
        map.put("Coupon", offer.getCoupon());
        map.put("IdOffer", offer.getIdOffer());
        map.put("Status", offer.getStatus());
        map.put("Profession", offer.getProfession());
        map.put("User", offer.getUser());
        map.put("IntrestedVerify", offer.getIntrestedVerify());


        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");

        Call<Offer> call = retrofitInterface.executenewOffer(map,"Bearer " + tockenacsses);
        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, Response<Offer> response) {

                if (response.code() == 200) {

                    addOffer.onComplete(200);

                } else {
                    addOffer.onComplete(400);

                }
            }

            @Override
            public void onFailure(Call<Offer> call, Throwable t) {
                addOffer.onComplete(400);
            }
        });
    }



    public void deleteoffer(Offer offer,deleteoffer deleteofferlisner){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String tokenAccess = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");

        retrofitInterface = retrofit.create(RetrofitInterface.class);


        Call<Void> call = retrofitInterface.deleteoffer(offer.getIdOffer(),"Bearer "+tokenAccess);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                deleteofferlisner.onComplete();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("TAG","basaaaaaa  a a a "+t);
                deleteofferlisner.onComplete();
            }
        });

    }

    public void editOffer(Offer newOffer, EditOfferListener editOfferListener){
         retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String tokenAccess = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Map<String, Object> map = newOffer.toJson();

        Call<Offer> call = retrofitInterface.editOffer(newOffer.getIdOffer(),"Bearer "+tokenAccess,map);
        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, Response<Offer> response) {
                editOfferListener.onComplete(200);
            }

            @Override
            public void onFailure(Call<Offer> call, Throwable t) {
                Log.d("TAG","basaaaaaa  a a a "+t);
                editOfferListener.onComplete(400);
            }
        });

    }

    public void getOfferById(String offerid,GetOfferListener getOfferListener) {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String tokenAccess = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");



        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<Offer> call = retrofitInterface.getOfferById(offerid,"Bearer "+tokenAccess);
        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, Response<Offer> response) {
                getOfferListener.onComplete(response.body());
            }
            @Override
            public void onFailure(Call<Offer> call, Throwable t) {
                Log.d("TAG","basaaaaaa  a a a "+t);

                getOfferListener.onComplete(null);

            }
        });
    }

    public LiveData<OffersListLoadingState> candidatesListLoadingState() {
        return candidatesListLoadingState;
    }

    public LiveData<List<User>> getAllCandidates(Offer offer){
        if (candidatesList.getValue() == null) { refreshCandidatesList(offer); };
        return candidatesList;
    }
    public void refreshCandidatesList(Offer offer){
        candidatesListLoadingState.setValue(OffersListLoadingState.loading);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");


        Call<List<User>> call = retrofitInterface.getCandidates(offer.getIdOffer(),"Bearer " + tockenacsses);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.code() == 200) {
                    List<User> stList = response.body();
                    candidatesList.postValue(stList);
                    candidatesListLoadingState.postValue(OffersListLoadingState.loaded);

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                candidatesList.postValue(null);
            }
        });


    }

}