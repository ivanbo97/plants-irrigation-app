package com.ivanboyukliev.plantsirrigationsystem.ui.plantirrigation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlantIrrigationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PlantIrrigationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is irrigation fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}