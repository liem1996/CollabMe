package com.example.collabme.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.collabme.model.ModelCandidates;
import com.example.collabme.objects.User;

import java.util.List;

public class Candidatesviewmodel extends ViewModel {

    LiveData<List<User>> data;

    public Candidatesviewmodel() {
        data = new MutableLiveData<>();

    }

    public LiveData<List<User>> setcandidates(String offer) {
        data =  ModelCandidates.instance2.getAll(offer);
        System.out.println(data.getValue());
        return data;
    }

    public LiveData<List<User>> getCandidates(String offer) {
        data =  ModelCandidates.instance2.getAll(offer);
        return data;
    }

}
