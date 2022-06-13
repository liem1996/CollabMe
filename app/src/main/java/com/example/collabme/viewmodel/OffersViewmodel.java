package com.example.collabme.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.collabme.model.ModelOffers;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.User;

import java.util.List;

public class OffersViewmodel extends ViewModel {

    LiveData<List<Offer>> dataHome, dataMyOffer, dataWaitingOffer;
    MutableLiveData<Offer> data1;
    LiveData<List<User>> data2;

    public OffersViewmodel(){
        dataHome = ModelOffers.instance.getAllOfferFromHome();
        dataMyOffer = ModelOffers.instance.getAllOfferFromMyOffers();
        dataWaitingOffer = ModelOffers.instance.getAllOfferFromWaitingOffers();

        data1 = new MutableLiveData<>();

    }
    public LiveData<List<Offer>> getDataHome() {
        return dataHome;
    }

    public LiveData<List<Offer>> getDataMyOffer() {
        return dataMyOffer;
    }

    public LiveData<List<Offer>> getDataWaitingOffer() {
        return dataWaitingOffer;
    }

    public MutableLiveData<Offer> deletePost(Offer post, ModelOffers.deleteoffer listener) {
        data1.setValue(post);
        // TODO: 3/14/2022 to do a delete to offer

        return data1;
    }

    public void refreshOffersList(){
        ModelOffers.instance.refreshPostList();
    }
}
