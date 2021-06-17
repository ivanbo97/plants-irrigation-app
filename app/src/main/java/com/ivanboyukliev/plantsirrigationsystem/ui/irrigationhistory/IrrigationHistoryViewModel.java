package com.ivanboyukliev.plantsirrigationsystem.ui.irrigationhistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IrrigationHistoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public IrrigationHistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is irrigation history fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
