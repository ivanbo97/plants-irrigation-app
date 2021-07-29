package com.ivanboyukliev.plantsirrigationsystem.navmenu.realtimedata.utils;

public class TempRange {
    private int minTemperature;
    private int maxTemperature;
    private String hint;

    public TempRange(int minTemperature, int maxTemperature, String hint) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.hint = hint;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public String getHint() {
        return hint;
    }
}
