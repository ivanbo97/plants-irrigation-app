package com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.AndroidMqttClientCallback;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.IrrigationSystemMutableLiveData;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils.IrrigationSystemState;

public class RealtimeDataViewModel extends ViewModel {


    private MutableLiveData<Boolean> brokerConnState;

    private MutableLiveData<String> moistureValue;

    private MutableLiveData<String> temperatureValue;

    private MutableLiveData<String> runningTask;

    private IrrigationSystemMutableLiveData<IrrigationSystemState> irrigationSystemState;

    public RealtimeDataViewModel() {

        AndroidMqttClientCallback mqttClientCallback = (AndroidMqttClientCallback) PlantManagerActivity
                .getMqttClient()
                .getMqttCallback();

        brokerConnState = PlantManagerActivity.getMqttClient().getBrokerConnState();

        moistureValue = mqttClientCallback.getMoistureValue();

        temperatureValue = mqttClientCallback.getTemperatureValue();

        runningTask = mqttClientCallback.getRunningTask();

        irrigationSystemState = mqttClientCallback.getCurrentIrrigationSystemState();
    }

    public MutableLiveData<Boolean> getBrokerConnState() {
        return brokerConnState;
    }

    public MutableLiveData<String> getMoistureValue() {
        return moistureValue;
    }

    public MutableLiveData<String> getTemperatureValue() {
        return temperatureValue;
    }

    public MutableLiveData<String> getRunningTask() {
        return runningTask;
    }

    public IrrigationSystemMutableLiveData<IrrigationSystemState> getIrrigationSystemState() {
        return irrigationSystemState;
    }
}