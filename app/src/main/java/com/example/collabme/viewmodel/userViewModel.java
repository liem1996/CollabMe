package com.example.collabme.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.collabme.model.ModelUsers;
import com.example.collabme.objects.User;

import java.util.List;

public class userViewModel extends ViewModel {

    LiveData<List<User>> data;
    MutableLiveData<User> data1;

    public userViewModel(){
        data = ModelUsers.instance3.getAllusers();
        data1 = new MutableLiveData<>();
    }
    public LiveData<List<User>> getData() {
        return data;
    }




}
