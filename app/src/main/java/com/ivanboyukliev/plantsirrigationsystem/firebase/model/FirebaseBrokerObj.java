package com.ivanboyukliev.plantsirrigationsystem.firebase.model;

import java.util.List;

public class FirebaseBrokerObj {

    private String brkName;
    private String brkURI;
    private List<String> topics;

    public FirebaseBrokerObj() {

    }

    public FirebaseBrokerObj(String brkName, String brkURI, List<String> topics) {
        this.brkName = brkName;
        this.brkURI = brkURI;
        this.topics = topics;
    }

    public String getBrkName() {
        return brkName;
    }

    public void setBrkName(String brkName) {
        this.brkName = brkName;
    }

    public String getBrkURI() {
        return brkURI;
    }

    public void setBrkURI(String brkURI) {
        this.brkURI = brkURI;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}