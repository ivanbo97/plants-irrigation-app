package com.ivanboyukliev.plantsirrigationsystem.firebase.model;

import java.io.Serializable;

public class FirebaseTopicObj implements Serializable {

    private String topicName;
    private int QoS;

    public FirebaseTopicObj() {

    }

    public FirebaseTopicObj(String topicName, int QoS) {
        this.topicName = topicName;
        this.QoS = QoS;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getQoS() {
        return QoS;
    }

    public void setQoS(int QoS) {
        this.QoS = QoS;
    }
}