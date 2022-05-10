package com.example.collabme.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.collabme.objects.MyApplication;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.User;
import com.example.collabme.objects.tokensrefresh;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * this model is for candidates the function in this model are :
 * 1.get list of offers by free search
 * 2.get list of offers by parameters from the user that is connect
 * 3.search of a spesific candidates in the page of the candidates
 *
 */

public class ModelCandidates {

    public static final ModelCandidates instance2 = new ModelCandidates();
    MutableLiveData<candidatelistloding> candidatesListLoadingState = new MutableLiveData<ModelCandidates.candidatelistloding>();
    MutableLiveData<List<User>> candidatesList = new MutableLiveData<List<User>>();
    public com.example.collabme.objects.tokensrefresh tokensrefresh = new tokensrefresh();


    public ModelCandidates() {
        candidatesListLoadingState.setValue(candidatelistloding.loaded);
        ;
    }

    /**
     * interfaces
     *
     *
     */
    public enum candidatelistloding {
        loading,
        loaded

    }

    public interface deletingothercandidates {
        void onComplete(Offer offer);
    }

    public interface getCandidateFromSearchListener {
        void onComplete(User user);
    }


    /**
     *
     * the section of the candidates and its functions
     * get
     * refreshomepage
     * search
     */

    public LiveData<candidatelistloding> getcandidateslistloding() {
        return candidatesListLoadingState;
    }

    public LiveData<List<User>> getAll(String offerid) {
        if (candidatesList.getValue() == null) {
            refreshPostList(offerid);
        }
        ;
        return candidatesList;
    }

    public void refreshPostList(String offerid) {
        candidatesListLoadingState.setValue(candidatelistloding.loading);

        tokensrefresh.retroServer();

        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");


        Call<List<User>> call = tokensrefresh.retrofitInterface.getCandidates(offerid,"Bearer " + tockenacsses);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) { //TODO::shows ONLY open status offers
                if (response.code() == 200) {
                    List<User> stList = response.body();
                    candidatesList.postValue(stList);
                    candidatesListLoadingState.postValue(candidatelistloding.loaded);

                } else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    String tockennew = tokensrefresh.gettockenAcsses();
                    Call<List<User>> call1 = tokensrefresh.retrofitInterface.getCandidates(offerid,"Bearer " + tockennew);
                    call1.enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response1) {
                            List<User> stList = response1.body();
                            if (response1.code() == 200) {
                                List<User> stList2 = response.body();
                                candidatesList.postValue(stList2);
                                candidatesListLoadingState.postValue(candidatelistloding.loaded);
                            } else {
                                candidatesList.postValue(null);
                                candidatesListLoadingState.postValue(candidatelistloding.loaded);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            candidatesList.postValue(null);
                            candidatesListLoadingState.postValue(candidatelistloding.loaded);
                        }
                    });

                } else {
                    candidatesList.postValue(null);
                    candidatesListLoadingState.postValue(candidatelistloding.loaded);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                candidatesList.postValue(null);
            }
        });
    }

    public void getCandidateFromSearch(String candidatesearch, getCandidateFromSearchListener getCandidateFromSearchListener) {

        tokensrefresh.retroServer();

        String tokenAccess = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");

        Call<User> call = tokensrefresh.retrofitInterface.getCandidateFromSearch(candidatesearch,"Bearer "+tokenAccess);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code()==200) {
                    getCandidateFromSearchListener.onComplete(response.body());
                }
                else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    String tockennew = tokensrefresh.gettockenAcsses();
                    Call<User> call1 = tokensrefresh.retrofitInterface.getCandidateFromSearch(candidatesearch,"Bearer "+tockennew);
                    call1.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response1) {
                            if(response1.code()==200){
                                getCandidateFromSearchListener.onComplete(response.body());
                            }else{
                                getCandidateFromSearchListener.onComplete(null);
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            getCandidateFromSearchListener.onComplete(null);
                        }
                    });
                } else {
                    getCandidateFromSearchListener.onComplete(null);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("TAG","failed in free search"+t);

                getCandidateFromSearchListener.onComplete(null);

            }
        });
    }



}
