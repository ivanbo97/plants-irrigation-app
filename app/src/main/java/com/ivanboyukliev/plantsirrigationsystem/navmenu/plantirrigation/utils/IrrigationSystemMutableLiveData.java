package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Observable;
import androidx.lifecycle.MutableLiveData;

public class IrrigationSystemMutableLiveData<T extends BaseObservable>
        extends MutableLiveData<T> {

    @Override
    public void setValue(T value) {
        super.setValue(value);
        //listen to property changes
        value.addOnPropertyChangedCallback(callback);
    }

    Observable.OnPropertyChangedCallback callback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            //Trigger LiveData observer on change of any property in object
            setValue(getValue());
            Log.i("Change in FIELD",sender.toString());

        }
    };
}
