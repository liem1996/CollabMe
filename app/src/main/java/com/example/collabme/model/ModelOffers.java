package com.example.collabme.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.collabme.objects.MyApplication;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.User;
import com.example.collabme.objects.tokensrefresh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * this model is for offers the function in this model are :
 * 1.add an offer to the application
 * 2.edit an offer that excisiting
 * 3.delete an offer that excisiting
 * 4.get offer by an offer id
 * 5.get your user by email
 * 6.takes all the offers that a user is candidate and filter it for waiting offers
 * 7.takes all the offers that a user is candidate in for media content
 * 8.refreshPostList()-function that gets all the offers that existing and refreshing the home page,myoffers page
 *   and waiting offers
 *
 */

public class ModelOffers {

    public static final ModelOffers instance = new ModelOffers();
    MutableLiveData<OffersListLoadingState> offersListLoadingState = new MutableLiveData<OffersListLoadingState>();
    MutableLiveData<List<Offer>> offersListHome = new MutableLiveData<List<Offer>>();
    MutableLiveData<List<Offer>> offersListMyOffer = new MutableLiveData<List<Offer>>();
    MutableLiveData<List<Offer>> offerWaitingList = new MutableLiveData<List<Offer>>();
    public tokensrefresh tokensrefresh = new tokensrefresh();
    private List<Offer> allOffersList = new LinkedList<>();
    private List<Offer> userOfferCandidates = new LinkedList<>();
    private User userConnected;
    private boolean userRefreshed = false;

    private String[] userConnectedProfessions = new String[16];
    private String[] userConnectedPlatforms = new String[5];

    /**
     * interfaces
     *
     *
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

    public interface getoffersfromuserinCandidates{
        void onComplete(List<Offer> offer);
    }

    public ModelOffers() {
        offersListLoadingState.setValue(OffersListLoadingState.loaded);
    }

    /**
     * the section of the offers and its functions
     * add
     * edit
     * get
     * refresh
     */

    public LiveData<OffersListLoadingState> getoffersListLoadingState() {
        return offersListLoadingState;
    }

    public LiveData<List<Offer>> getAllOfferFromHome() {
        if (offersListHome.getValue() == null) {
            refreshPostList();
        }

        return offersListHome;
    }

    public LiveData<List<Offer>> getAllOfferFromMyOffers() {
        if (offersListMyOffer.getValue() == null) {
            refreshPostList();
        }

        return offersListMyOffer;
    }

    public LiveData<List<Offer>> getAllOfferFromWaitingOffers(){
        if (offerWaitingList.getValue() == null) {
            refreshPostList();
        }

        return offerWaitingList;
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

    private void updateUserConnected() {
        ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
            @Override
            public void onComplete(User profile) {
                userConnected = profile;
                ModelUsers.instance3.setUserConnected(profile);
                userRefreshed = true;
                userConnectedProfessions = new String[16];
                userConnectedPlatforms = new String[5];
                for (int i = 0; i < profile.getProfessions().length; i++) {
                    userConnectedProfessions[i] = profile.getProfessions()[i];
                }
                for (int i = 0; i < profile.getPlatforms().length; i++) {
                    userConnectedPlatforms[i] = profile.getPlatforms()[i];
                }
            }
        });

    }

    private void updateOfferLists() {
        if (ModelUsers.instance3.getUser() == null || userRefreshed == false) {
            ModelUsers.instance3.getUserConnect(new ModelUsers.getuserconnect() {
                @Override
                public void onComplete(User profile) {
                    userConnected = profile;
                    ModelUsers.instance3.setUserConnected(profile);
                    userRefreshed = true;
                    updateOpenStatusOffersList(allOffersList);
                    updateMyOfferList(allOffersList);
                    getUserOffersByOfferCandidates(userConnected.getUsername());
                    offersListLoadingState.postValue(OffersListLoadingState.loaded);
                }
            });
        } else {
            updateOpenStatusOffersList(allOffersList);
            updateMyOfferList(allOffersList);
            getUserOffersByOfferCandidates(userConnected.getUsername());
            offersListLoadingState.postValue(OffersListLoadingState.loaded);
        }
    }

    private void updateOpenStatusOffersList(List<Offer> stList) {
        List<Offer> openOfferLst = new LinkedList<>();
        ArrayList<String> rejectedOffers = userConnected.getRejectedOffers();
        for (int i = 0; i < stList.size(); i++) {
           if (checkUserProfessions(stList.get(i))){
               if (stList.get(i).getStatus().equals("Open") && (!rejectedOffers.contains(stList.get(i).getIdOffer()))  && !Arrays.asList(stList.get(i).getUsers()).contains(userConnected.getUsername()) ) {
                   openOfferLst.add(stList.get(i));
               }
           }
        }
        offersListHome.postValue(openOfferLst);
    }

    private boolean checkUserProfessions(Offer offer){
        for(int k = 0 ; k<offer.getProfession().length; k++) {
            for (int j = 0; j < userConnectedProfessions.length; j++) {
                if (offer.getProfession()[k].equals(userConnectedProfessions[j])) {
                    return true;
                }
            }
        }
        return false;
    }

//    private boolean checkUserPlatforms(Offer offer){
//        for(int k = 0 ; k<offer.().length; k++) {
//            for (int j = 0; j < userConnectedPlatforms.length; j++) {
//                if (offer.getProfession()[k].equals(userConnectedPlatforms[j])) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    public void updateMyOfferList(List<Offer> stList) {
        List<Offer> myOfferLst = new LinkedList<>();
        String usernameConnected = userConnected.getUsername();
        //getUserOffersByOfferCandidates(usernameConnected);
        for (int i = 0; i < stList.size(); i++) {
            if (usernameConnected.equals(stList.get(i).getUser())) {
                myOfferLst.add(stList.get(i));
            }
        }
        offersListMyOffer.postValue(myOfferLst);
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

    public void getUserOffersByOfferCandidates(String username) {
        tokensrefresh.retroServer();
        String tokenAccess = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");

        Call<List<Offer>> call = tokensrefresh.retrofitInterface.getUserOffersByOffersCandidates(username, "Bearer " + tokenAccess);
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if (response.code() == 200) {
                    userOfferCandidates = response.body();
                    offerWaitingList.postValue(userOfferCandidates);
                } else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    String tockennew = tokensrefresh.gettockenAcsses();
                    Call<List<Offer>> call1 = tokensrefresh.retrofitInterface.getUserOffersByOffersCandidates(username, "Bearer " + tockennew);
                    call1.enqueue(new Callback<List<Offer>>() {
                        @Override
                        public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                            if (response.code() == 200) {
                                userOfferCandidates = response.body();
                                offerWaitingList.postValue(userOfferCandidates);
                            } else {
                                userOfferCandidates = null;
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Offer>> call, Throwable t) {
                            userOfferCandidates = null;
                        }
                    });
                } else {
                    userOfferCandidates = null;
                }
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                Log.d("TAG", "basaaaaaa  a a a " + t);
                userOfferCandidates = null;
            }
        });
    }

    public void getoffersfromuserinCandidates(String username, getoffersfromuserinCandidates getoffersfromuserinCandidatesListener) {
        tokensrefresh.retroServer();
        String tokenAccess = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");

        Call<List<Offer>> call = tokensrefresh.retrofitInterface.getoffersfromuserinCandidates(username, "Bearer " + tokenAccess);
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if (response.code() == 200) {
                    getoffersfromuserinCandidatesListener.onComplete(response.body());
                } else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    String tockennew = tokensrefresh.gettockenAcsses();
                    Call<List<Offer>> call1 = tokensrefresh.retrofitInterface.getoffersfromuserinCandidates(username, "Bearer " + tockennew);
                    call1.enqueue(new Callback<List<Offer>>() {
                        @Override
                        public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response1) {
                            if (response1.code() == 200) {
                                getoffersfromuserinCandidatesListener.onComplete(response.body());
                            } else {
                                getoffersfromuserinCandidatesListener.onComplete(null);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Offer>> call, Throwable t) {
                            getoffersfromuserinCandidatesListener.onComplete(null);
                        }
                    });
                } else {
                    getoffersfromuserinCandidatesListener.onComplete(null);
                }
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                Log.d("TAG", "getting the offers of user in candidates failed" + t);

                getoffersfromuserinCandidatesListener.onComplete(null);
            }
        });
    }
}