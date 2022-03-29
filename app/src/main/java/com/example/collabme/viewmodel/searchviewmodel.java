package com.example.collabme.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.collabme.model.ModelSearch;
import com.example.collabme.objects.Offer;

import java.util.List;


public class searchviewmodel extends ViewModel {

    MutableLiveData<String> data1;
    MutableLiveData<List<Offer>> data;

    public searchviewmodel(){
        data1 = new MutableLiveData<>();
        data = new MutableLiveData<>();
    }
    public MutableLiveData<List<Offer>> getOfferFromFreeSearch(String freesearch) {
        data1.setValue(freesearch);
        ModelSearch.instance.getOfferFromFreeSearch(data1.getValue(), new ModelSearch.getOfferFromFreeSearchListener() {
            @Override
            public void onComplete(List<Offer> offers) {
                data.setValue(offers);
            }
        });

        return data;
    }

}
