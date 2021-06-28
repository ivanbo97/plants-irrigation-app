package com.ivanboyukliev.plantsirrigationsystem.firebase.model;

public class FirebaseIrrigationRecord {

    String date;
    String startTime;
    String endTime;
    String moistureLevel;

    public FirebaseIrrigationRecord(String date, String startTime, String endTime, String moistureLevel) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.moistureLevel = moistureLevel;
    }

    public FirebaseIrrigationRecord() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMoistureLevel() {
        return moistureLevel;
    }

    public void setMoistureLevel(String moistureLevel) {
        this.moistureLevel = moistureLevel;
    }
}
