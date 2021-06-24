package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class IrrigationSystemState extends BaseObservable {

    private boolean pumpTaskRunning;
    private boolean moistureMaintainTaskRunning;
    private boolean delayedStartTaskRunning;
    private boolean connectedToBroker;

    public IrrigationSystemState() {
        pumpTaskRunning = false;
        moistureMaintainTaskRunning = false;
        delayedStartTaskRunning = false;
        connectedToBroker = false;
    }

    @Bindable
    public boolean isPumpTaskRunning() {
        return pumpTaskRunning;
    }

    public void setPumpTaskRunning(boolean pumpTaskRunning) {
        this.pumpTaskRunning = pumpTaskRunning;
        notifyPropertyChanged(BR.pumpTaskRunning);
    }

    @Bindable
    public boolean isMoistureMaintainTaskRunning() {
        return moistureMaintainTaskRunning;
    }

    public void setMoistureMaintainTaskRunning(boolean moistureMaintainTaskRunning) {
        this.moistureMaintainTaskRunning = moistureMaintainTaskRunning;
        notifyPropertyChanged(BR.moistureMaintainTaskRunning);
    }

    @Bindable
    public boolean isDelayedStartTaskRunning() {
        return delayedStartTaskRunning;
    }

    public void setDelayedStartTaskRunning(boolean delayedStartTaskRunning) {
        this.delayedStartTaskRunning = delayedStartTaskRunning;
        notifyPropertyChanged(BR.delayedStartTaskRunning);
    }

    @Bindable
    public boolean isConnectedToBroker() {
        return connectedToBroker;
    }

    public void setConnectedToBroker(boolean connectedToBroker) {
        this.connectedToBroker = connectedToBroker;
        notifyPropertyChanged(BR.connectedToBroker);
    }
}
