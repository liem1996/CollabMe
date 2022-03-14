package com.example.collabme.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.collabme.model.Model;
import com.example.collabme.model.Offer;

import java.util.List;

public class offersviewmodel  extends ViewModel {

    LiveData<List<Offer>> data;
    MutableLiveData<Offer> data1;

    public offersviewmodel(){
        data = Model.instance.getAll();
        data1 = new MutableLiveData<>();
    }
    public LiveData<List<Offer>> getData() {
        return data;
    }



    public MutableLiveData<Offer> deletePost(Offer post, Model.deleteoffer listener) {
        data1.setValue(post);
        // TODO: 3/14/2022 to do a delete to offer
       // Model.instance.deletePost(data1.getValue(),listener);
        return data1;
    }


}
