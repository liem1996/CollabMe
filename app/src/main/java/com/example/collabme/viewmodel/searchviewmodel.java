package com.example.collabme.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.collabme.model.ModelSearch;
import com.example.collabme.objects.Offer;

import java.util.LinkedList;
import java.util.List;


public class searchviewmodel extends ViewModel {

    MutableLiveData<String> data1;
    MutableLiveData<List<Offer>> data;

    public searchviewmodel(){
        data1 = new MutableLiveData<>();
        data = new MutableLiveData<>();
    }


    public MutableLiveData<List<Offer>> getOfferFromFreeSearch(String freesearch, ModelSearch.getOfferFromFreeSearchListener listener) {
        data1.setValue(freesearch);
        ModelSearch.instance.getOfferFromFreeSearch(data1.getValue(),listener);
        List<Offer> offers = new LinkedList<>();
        listener.onComplete(offers);
        data.setValue(offers);
        return data;
    }

}
