package com.ivanboyukliev.plantsirrigationsystem.firebase.model;

public class FirebaseTopicObj {

    private String topicName;
    private String topicID;

    public FirebaseTopicObj() {

    }

    public FirebaseTopicObj(String topicName, String topicID) {
        this.topicName = topicName;
        this.topicID = topicID;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicID() {
        return topicID;
    }

    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }
}