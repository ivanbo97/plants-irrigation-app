package com.ivanboyukliev.plantsirrigationsystem.navmenu.plantirrigation.utils;

public class IrrigationSystemState {

    private boolean pumpTaskRunning;
    private boolean moistureMaintainTaskRunning;
    private boolean delayedStartTaskRunning;

    public IrrigationSystemState() {
        pumpTaskRunning = false;
        moistureMaintainTaskRunning = false;
        delayedStartTaskRunning = false;
    }

    public boolean isPumpTaskRunning() {
        return pumpTaskRunning;
    }

    public void setPumpTaskRunning(boolean pumpTaskRunning) {
        this.pumpTaskRunning = pumpTaskRunning;
    }

    public boolean isMoistureMaintainTaskRunning() {
        return moistureMaintainTaskRunning;
    }

    public void setMoistureMaintainTaskRunning(boolean moistureMaintainTaskRunning) {
        this.moistureMaintainTaskRunning = moistureMaintainTaskRunning;
    }

    public boolean isDelayedStartTaskRunning() {
        return delayedStartTaskRunning;
    }

    public void setDelayedStartTaskRunning(boolean delayedStartTaskRunning) {
        this.delayedStartTaskRunning = delayedStartTaskRunning;
    }
}
