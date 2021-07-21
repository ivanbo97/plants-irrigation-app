package com.ivanboyukliev.plantsirrigationsystem.firebase.util;

import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseBroker;
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
            // Using separator between plant's name and plant's api id
            String plantNameAndId = plant.getPlantName() + "|" + plant.getPlantApiId();
            plantsDB.child(plantNameAndId).setValue(plant.getImageURL());
            plant.setPlantName(plantNameAndId);
        }
    }

    public static void updateBrokerData(FirebaseBroker brokerForBinding, EditText brokerUrlTv) {
        String newServerUrl = brokerUrlTv.getText().toString();
        if (newServerUrl.isEmpty()) {
            HomeActivity.showBrokerMessage("Empty input for server URI");
            return;
        }
        String currentBrokerUrl = brokerForBinding.getBrokerIp() + ":" + brokerForBinding.getBrokerPort();
        String currentBrokerUrlFirebaseStandard = UserInputConverter.convertServerURIToFirebaseRules(currentBrokerUrl);
        HomeActivity.getDatabaseAuthUserBrokers().child(currentBrokerUrlFirebaseStandard).setValue(null);

        String urlToFirebaseStandards = UserInputConverter.convertServerURIToFirebaseRules(newServerUrl);
        HomeActivity.getDatabaseAuthUserBrokers().child(urlToFirebaseStandards)
                .child("brkName").setValue(brokerForBinding.getBrokerName());

        DatabaseReference newlyAddedBroker = HomeActivity.getDatabaseAuthUserBrokers().child(urlToFirebaseStandards);
        if (brokerForBinding.getPlants().size() > 0) {
            //first we create the node 'plants' and then add all plants to it
            FirebasePlantObj firstPlant = brokerForBinding.getPlants().get(0);
            newlyAddedBroker.child("plants")
                    .child(firstPlant.getPlantName())
                    .setValue(firstPlant.getImageURL());
            updatePlantsData(newlyAddedBroker.child("plants"), brokerForBinding.getPlants());
        }
        if (brokerForBinding.getTopics().size() > 0) {
            //first we create the node 'topics' and then add all topics to it
            FirebaseTopicObj firstTopic = brokerForBinding.getTopics().get(0);
            String firstTopicName = firstTopic.getTopicName();

            newlyAddedBroker.child("topics")
                    .child(UserInputConverter.convertBrokerTopicToFirebaseRules(firstTopicName))
                    .setValue(firstTopic.getQoS());
            importTopicsData(newlyAddedBroker.child("topics"), brokerForBinding.getTopics());
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

    private static void updatePlantsData(DatabaseReference plantsDB, List<FirebasePlantObj> plants) {
        for (FirebasePlantObj plant : plants) {
            // Using separator between plant's name and plant's api id
            String plantNameAndId = plant.getPlantName();
            plantsDB.child(plantNameAndId).setValue(plant.getImageURL());
            plant.setPlantName(plantNameAndId);
        }
    }
}
