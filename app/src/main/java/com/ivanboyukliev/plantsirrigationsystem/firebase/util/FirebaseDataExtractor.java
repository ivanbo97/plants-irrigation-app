package com.ivanboyukliev.plantsirrigationsystem.firebase.util;

import com.google.firebase.database.DataSnapshot;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model.BasicMqttBrokerClient;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseTopicObj;
import com.ivanboyukliev.plantsirrigationsystem.utils.FirebaseRetrievedDataConverter;

public class FirebaseDataExtractor {

    public static void retrieveTopicsAndPopulateBroker(BasicMqttBrokerClient mqttBroker, Iterable<DataSnapshot> topics) {
        for (DataSnapshot topic : topics) {
            Integer QoS = topic.getValue(Integer.class);
            String firebaseTopicName = topic.getKey();
            String standardTopicName = FirebaseRetrievedDataConverter.convertBrokerTopicToStandard(firebaseTopicName);
            mqttBroker.getTopics().add(new FirebaseTopicObj(standardTopicName, QoS));
        }
    }
}