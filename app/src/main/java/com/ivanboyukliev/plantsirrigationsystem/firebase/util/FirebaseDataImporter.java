package com.ivanboyukliev.plantsirrigationsystem.firebase.util;

import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model.BasicMqttBrokerClient;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebasePlantObj;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseTopicObj;
import com.ivanboyukliev.plantsirrigationsystem.utils.UserInputConverter;

import java.util.List;

public class FirebaseDataImporter {

    public static void importTopicsData(DatabaseReference topicsDB, List<FirebaseTopicObj> topics) {
        for (FirebaseTopicObj topic : topics) {
            String topicName = topic.getTopicName();
            String topicNameFbStandard = UserInputConverter.convertBrokerTopicToFirebaseRules(topicName);
            topicsDB.child(topicNameFbStandard).setValue(topic.getQoS());
        }
    }

    public static void importPlantsData(DatabaseReference plantsDB, List<FirebasePlantObj> plants) {
        for (FirebasePlantObj plant : plants) {
            String plantName = plant.getPlantName();
            plantsDB.child(plantName).setValue(plant.getImageURL());
        }
    }

    public static void updateBrokerData(BasicMqttBrokerClient brokerForBinding, EditText brokerUrlTv) {
        String newServerUrl = brokerUrlTv.getText().toString();
        if (newServerUrl.isEmpty()) {
            HomeActivity.showBrokerMessage("Empty input for server URI");
            return;
        }
        String currentBrokerUrl = brokerForBinding.getBrokerIp() + ":" + brokerForBinding.getBrokerPort();
        String currentBrokerUrlFirebaseStandard = UserInputConverter.convertServerURIToFirebaseRules(currentBrokerUrl);
        HomeActivity.getmDatabaseAuthUserBrokers().child(currentBrokerUrlFirebaseStandard).setValue(null);

        String urlToFirebaseStandards = UserInputConverter.convertServerURIToFirebaseRules(newServerUrl);
        HomeActivity.getmDatabaseAuthUserBrokers().child(urlToFirebaseStandards)
                .child("brkName").setValue(brokerForBinding.getBrokerName());

        DatabaseReference newlyAddedBroker = HomeActivity.getmDatabaseAuthUserBrokers().child(urlToFirebaseStandards);
        if (brokerForBinding.getPlants().size() > 0) {
            //first we create the node 'plants' and then add all plants to it
            FirebasePlantObj firstPlant = brokerForBinding.getPlants().get(0);
            newlyAddedBroker.child("plants")
                    .child(firstPlant.getPlantName())
                    .setValue(firstPlant.getImageURL());
            FirebaseDataImporter.importPlantsData(newlyAddedBroker.child("plants"), brokerForBinding.getPlants());
        }
        if (brokerForBinding.getTopics().size() > 0) {
            //first we create the node 'topics' and then add all topics to it
            FirebaseTopicObj firstTopic = brokerForBinding.getTopics().get(0);
            String firstTopicName = firstTopic.getTopicName();

            newlyAddedBroker.child("topics")
                    .child(UserInputConverter.convertBrokerTopicToFirebaseRules(firstTopicName))
                    .setValue(Integer.valueOf(firstTopic.getQoS()));
            FirebaseDataImporter.importTopicsData(newlyAddedBroker.child("topics"), brokerForBinding.getTopics());
        }


        String newBrokerIp;
        String newBrokerPort;
        try {
            newBrokerIp = newServerUrl.substring(0, newServerUrl.
                    indexOf(":", newServerUrl.indexOf(":") + 1));
            int portIdx = newServerUrl.indexOf(":", newServerUrl.indexOf(":") + 1);
            newBrokerPort = newServerUrl.substring(portIdx + 1);
        } catch (StringIndexOutOfBoundsException e) {
            HomeActivity.showBrokerMessage("Incorrect Broker URL");
            e.printStackTrace();
            return;
        }

        brokerForBinding.setBrokerIp(newBrokerIp);
        brokerForBinding.setBrokerPort(newBrokerPort);
        brokerForBinding.setBrokerID(urlToFirebaseStandards);
    }

}
