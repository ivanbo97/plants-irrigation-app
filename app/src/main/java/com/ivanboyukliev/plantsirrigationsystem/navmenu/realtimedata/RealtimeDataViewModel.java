package com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RealtimeDataViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RealtimeDataViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is realtime data fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}