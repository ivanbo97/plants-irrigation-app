package com.ivanboyukliev.plantsirrigationsystem.firebase.model;

import java.util.ArrayList;
import java.util.List;

public class FirebaseBroker {

    private String brokerName;
    private String brokerIp;
    private String brokerPort;
    private String brokerID;
    private List<FirebaseTopicObj> topics;
    private List<FirebasePlantObj> plants;

    public FirebaseBroker() {
        topics = new ArrayList<>();
        plants = new ArrayList<>();
    }

    public FirebaseBroker(String brokerName, String brokerIp, String brokerPort, String brokerID) {
        this.brokerName = brokerName;
        this.brokerIp = brokerIp;
        this.brokerPort = brokerPort;
        this.brokerID = brokerID;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getBrokerIp() {
        return brokerIp;
    }

    public void setBrokerIp(String brokerIp) {
        this.brokerIp = brokerIp;
    }

    public String getBrokerPort() {
        return brokerPort;
    }

    public void setBrokerPort(String brokerPort) {
        this.brokerPort = brokerPort;
    }

    public String getBrokerID() {
        return brokerID;
    }

    public void setBrokerID(String brokerID) {
        this.brokerID = brokerID;
    }

    public List<FirebaseTopicObj> getTopics() {
        return topics;
    }

    public void setTopics(List<FirebaseTopicObj> topics) {
        this.topics = topics;
    }

    public List<FirebasePlantObj> getPlants() {
        return plants;
    }

    public void setPlants(List<FirebasePlantObj> plants) {
        this.plants = plants;
    }
}