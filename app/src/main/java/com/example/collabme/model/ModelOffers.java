package com.example.collabme.model;

import android.content.Context;
import android.util.Log;
import android.view.Display;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.collabme.objects.MyApplication;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.User;
import com.example.collabme.objects.tokensrefresh;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelOffers {


    public static final ModelOffers instance = new ModelOffers();
    MutableLiveData<OffersListLoadingState> offersListLoadingState = new MutableLiveData<OffersListLoadingState>();
    MutableLiveData<List<Offer>> offersListHome = new MutableLiveData<List<Offer>>();
    MutableLiveData<List<Offer>> offersListMyOffer = new MutableLiveData<List<Offer>>();
    MutableLiveData<OffersListLoadingState> candidatesListLoadingState = new MutableLiveData<OffersListLoadingState>();
    MutableLiveData<List<User>> candidatesList = new MutableLiveData<List<User>>();
    public tokensrefresh tokensrefresh = new tokensrefresh();
    private List<Offer> allOffersList = new LinkedList<>();

    /**
     * interfaces
     */

    public interface addOfferListener {
        void onComplete(int code);
    }

    public enum OffersListLoadingState {
        loading,
        loaded
    }

    public interface GetOfferListener {
        void onComplete(Offer offer);
    }

    public interface EditOfferListener {
        void onComplete(int code);
    }

    public interface deleteoffer {
        void onComplete();
    }

    public ModelOffers() {
        offersListLoadingState.setValue(OffersListLoadingState.loaded);
    }

    /**
     * the section of the offers
     * add
     * edit
     * get
     * refreshomepage
     */

    public LiveData<OffersListLoadingState> getoffersListLoadingState() {
        return offersListLoadingState;
    }

    public LiveData<List<Offer>> getAllOfferFromHome() {
        if (offersListHome.getValue() == null) {
            refreshPostList();
        }
        ;
        return offersListHome;
    }

    public LiveData<List<Offer>> getAllOfferFromMyOffers() {
        if (offersListMyOffer.getValue() == null) {
            refreshPostList();
        }
        ;
        return offersListMyOffer;
    }

    public List<Offer> getAllOffersList() {
        return allOffersList;
    }

    public void refreshPostList() {
        offersListLoadingState.setValue(OffersListLoadingState.loading);

        updateUserConnected();

        tokensrefresh.retroServer();
        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");


        Call<List<Offer>> call = tokensrefresh.retrofitInterface.getoffers("Bearer " + tockenacsses);
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if (response.code() == 200) {
                    allOffersList = response.body();
                    updateOfferLists();
                } else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    String tockennew = tokensrefresh.gettockenAcsses();
                    Call<List<Offer>> call1 = tokensrefresh.retrofitInterface.getoffers("Bearer " + tockennew);
                    call1.enqueue(new Callback<List<Offer>>() {
                        @Override
                        public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response1) {
                            allOffersList = response.body();
                            if (response1.code() == 200) {
                                updateOfferLists();
                            } else {
                                offersListHome.postValue(null);
                                offersListMyOffer.postValue(null);
                                offersListLoadingState.postValue(OffersListLoadingState.loaded);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Offer>> call, Throwable t) {
                            offersListHome.postValue(null);
                            offersListMyOffer.postValue(null);
                            offersListLoadingState.postValue(OffersListLoadingState.loaded);
                        }
                    });

                } else {
                    offersListHome.postValue(null);
                    offersListMyOffer.postValue(null);
                    offersListLoadingState.postValue(OffersListLoadingState.loaded);
                }
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                offersListHome.postValue(null);
                offersListMyOffer.postValue(null);
            }
        });
    }

    private void updateOfferLists(){
        List<Offer> openOfferLst = updateOpenStatusOffersList(allOffersList);
        offersListHome.postValue(openOfferLst);
        List<Offer> myOffersLst = updateMyOfferList(allOffersList);
        offersListMyOffer.postValue(myOffersLst);
        offersListLoadingState.postValue(OffersListLoadingState.loaded);
    }

    private void updateUserConnected() {
        if (ModelUsers.instance3.getUser() == null) {
            ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
                @Override
                public void onComplete(User profile) {
                    ModelUsers.instance3.setUserConnected(profile);
                }
            });
        }
    }

    private List<Offer> updateOpenStatusOffersList(List<Offer> stList) {
        List<Offer> openOfferLst = new LinkedList<>();
        for (int i = 0; i < stList.size(); i++) {
            if (stList.get(i).getStatus().equals("Open")) {
                openOfferLst.add(stList.get(i));
            }
        }
        return openOfferLst;
    }

    public List<Offer> updateMyOfferList(List<Offer> stList) {
        List<Offer> myOfferLst = new LinkedList<>();

        updateUserConnected();
        User userConnected = ModelUsers.instance3.getUser();

        for (int i = 0; i < stList.size(); i++) {
            if (userConnected.getUsername().equals(stList.get(i).getUser())) {
                myOfferLst.add(stList.get(i));
            }
        }
        offersListMyOffer.postValue(myOfferLst);
        return myOfferLst;
    }

    public void addOffer(Offer offer, ModelOffers.addOfferListener addOffer) {
        tokensrefresh.retroServer();

        Map<String, Object> map = offer.toJson();
        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");

        Call<Offer> call = tokensrefresh.retrofitInterface.executenewOffer(map, "Bearer " + tockenacsses);

        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, Response<Offer> response) {
                if (response.code() == 200) {
                    addOffer.onComplete(200);
                } else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    String tockennew = tokensrefresh.gettockenAcsses();
                    Call<Offer> call1 = tokensrefresh.retrofitInterface.executenewOffer(map, "Bearer " + tockennew);
                    call1.enqueue(new Callback<Offer>() {
                        @Override
                        public void onResponse(Call<Offer> call, Response<Offer> response1) {
                            if (response1.code() == 200) {
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

    public void deleteoffer(Offer offer, deleteoffer deleteofferlisner) {
        tokensrefresh.retroServer();

        String tokenAccess = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");

        Call<Void> call = tokensrefresh.retrofitInterface.deleteoffer(offer.getIdOffer(), "Bearer " + tokenAccess);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    deleteofferlisner.onComplete();

                } else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    String tockennew = tokensrefresh.gettockenAcsses();
                    Call<Void> call1 = tokensrefresh.retrofitInterface.deleteoffer(offer.getIdOffer(), "Bearer " + tockennew);
                    call1.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response1) {
                            if (response1.code() == 200) {
                                deleteofferlisner.onComplete();
                            } else {
                                deleteofferlisner.onComplete();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            deleteofferlisner.onComplete();
                        }
                    });
                } else {
                    deleteofferlisner.onComplete();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("TAG", "basaaaaaa  a a a " + t);
                deleteofferlisner.onComplete();
            }
        });

    }

    public void editOffer(Offer newOffer, EditOfferListener editOfferListener) {
        tokensrefresh.retroServer();

        String tokenAccess = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");

        Map<String, Object> map = newOffer.toJson();

        Call<Offer> call = tokensrefresh.retrofitInterface.editOffer(newOffer.getIdOffer(), "Bearer " + tokenAccess, map);
        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, Response<Offer> response) {
                if (response.code() == 200) {
                    editOfferListener.onComplete(200);
                } else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    String tockennew = tokensrefresh.gettockenAcsses();
                    Call<Offer> call1 = tokensrefresh.retrofitInterface.editOffer(newOffer.getIdOffer(), "Bearer " + tockennew, map);
                    call1.enqueue(new Callback<Offer>() {
                        @Override
                        public void onResponse(Call<Offer> call, Response<Offer> response1) {
                            if (response1.code() == 200) {
                                editOfferListener.onComplete(200);
                            } else {
                                editOfferListener.onComplete(400);
                            }
                        }

                        @Override
                        public void onFailure(Call<Offer> call, Throwable t) {
                            editOfferListener.onComplete(400);
                        }
                    });
                } else {
                    editOfferListener.onComplete(400);
                }
            }

            @Override
            public void onFailure(Call<Offer> call, Throwable t) {
                Log.d("TAG", "basaaaaaa  a a a " + t);
                editOfferListener.onComplete(400);
            }
        });
    }

    public void getOfferById(String offerid, GetOfferListener getOfferListener) {

        tokensrefresh.retroServer();
        String tokenAccess = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");

        Call<Offer> call = tokensrefresh.retrofitInterface.getOfferById(offerid, "Bearer " + tokenAccess);
        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, Response<Offer> response) {
                if (response.code() == 200) {
                    getOfferListener.onComplete(response.body());
                } else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    String tockennew = tokensrefresh.gettockenAcsses();
                    Call<Offer> call1 = tokensrefresh.retrofitInterface.getOfferById(offerid, "Bearer " + tockennew);
                    call1.enqueue(new Callback<Offer>() {
                        @Override
                        public void onResponse(Call<Offer> call, Response<Offer> response1) {
                            if (response1.code() == 200) {
                                getOfferListener.onComplete(response.body());
                            } else {
                                getOfferListener.onComplete(null);
                            }
                        }

                        @Override
                        public void onFailure(Call<Offer> call, Throwable t) {
                            getOfferListener.onComplete(null);
                        }
                    });
                } else {
                    getOfferListener.onComplete(null);
                }
            }

            @Override
            public void onFailure(Call<Offer> call, Throwable t) {
                Log.d("TAG", "basaaaaaa  a a a " + t);

                getOfferListener.onComplete(null);
            }
        });
    }
}