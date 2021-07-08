package com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata.utils;

public class TempRange {
    int minTemperature;
    int maxTemperature;

    public TempRange(int minTemperature, int maxTemperature) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }
}
