package com.example.collabme.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.collabme.model.ModelOffers;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.User;

import java.util.List;

public class offersviewmodel  extends ViewModel {

    LiveData<List<Offer>> dataHome, dataMyOffer;
    MutableLiveData<Offer> data1;
    LiveData<List<User>> data2;

    public offersviewmodel(){
        dataHome = ModelOffers.instance.getAllOfferFromHome();
        dataMyOffer = ModelOffers.instance.getAllOfferFromMyOffers();

        data1 = new MutableLiveData<>();

    }
    public LiveData<List<Offer>> getDataHome() {
        return dataHome;
    }

    public LiveData<List<Offer>> getDataMyOffer() {
        return dataMyOffer;
    }

    public MutableLiveData<Offer> deletePost(Offer post, ModelOffers.deleteoffer listener) {
        data1.setValue(post);
        // TODO: 3/14/2022 to do a delete to offer
       // Model.instance.deletePost(data1.getValue(),listener);
        return data1;
    }




}
