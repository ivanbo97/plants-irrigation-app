package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils;

public class IrrigationSystemState {

    private boolean isPumpActive = false;
    private boolean isDelayedStartTaskOn = false;
    private boolean isMoistureMaintainTaskOn = false;

    public IrrigationSystemState(){

    }

    public boolean isPumpActive() {
        return isPumpActive;
    }

    public boolean isDelayedStartTaskOn() {
        return isDelayedStartTaskOn;
    }

    public boolean isMoistureMaintainTaskOn() {
        return isMoistureMaintainTaskOn;
    }

    public void setPumpActive(boolean pumpActive) {
        isPumpActive = pumpActive;
    }

    public void setDelayedStartTaskOn(boolean delayedStartTaskOn) {
        isDelayedStartTaskOn = delayedStartTaskOn;
    }

    public void setMoistureMaintainTaskOn(boolean moistureMaintainTaskOn) {
        isMoistureMaintainTaskOn = moistureMaintainTaskOn;
    }
}
