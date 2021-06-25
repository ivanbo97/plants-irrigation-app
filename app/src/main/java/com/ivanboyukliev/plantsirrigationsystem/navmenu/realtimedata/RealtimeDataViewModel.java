package com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.AndroidMqttClientCallback;

public class RealtimeDataViewModel extends ViewModel {


    private MutableLiveData<Boolean> brokerConnState;

    private MutableLiveData<String> moistureValue;

    public RealtimeDataViewModel() {

        brokerConnState = PlantManagerActivity.getMqttClient().getBrokerConnState();

        moistureValue = ((AndroidMqttClientCallback) PlantManagerActivity
                .getMqttClient()
                .getMqttCallback())
                .getMoistureValue();
    }

    public MutableLiveData<Boolean> getBrokerConnState() {
        return brokerConnState;
    }

    public MutableLiveData<String> getMoistureValue() {
        return moistureValue;
    }
}