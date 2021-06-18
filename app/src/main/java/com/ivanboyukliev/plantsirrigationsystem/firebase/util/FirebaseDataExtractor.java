package com.ivanboyukliev.plantsirrigationsystem.firebase.util;

import com.google.firebase.database.DataSnapshot;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseBrokerObj;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebasePlantObj;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseTopicObj;
import com.ivanboyukliev.plantsirrigationsystem.utils.FirebaseRetrievedDataConverter;

public class FirebaseDataExtractor {

    public static void retrieveTopicsAndPopulateBroker(FirebaseBrokerObj mqttBroker, Iterable<DataSnapshot> topics) {
        for (DataSnapshot topic : topics) {
            Integer QoS = topic.getValue(Integer.class);
            String firebaseTopicName = topic.getKey();
            String standardTopicName = FirebaseRetrievedDataConverter.convertBrokerTopicToStandard(firebaseTopicName);
            mqttBroker.getTopics().add(new FirebaseTopicObj(standardTopicName, QoS));
        }
    }

    public static void retrievePlantsAndPopulateBroker(FirebaseBrokerObj mqttBroker, Iterable<DataSnapshot> plants) {
        for (DataSnapshot plant : plants) {
            String plantName = plant.getKey();
            String imageURL = plant.getValue(String.class);
            mqttBroker.getPlants().add(new FirebasePlantObj(plantName, imageURL));
        }
    }
}