package com.ivanboyukliev.plantsirrigationsystem.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model.BasicMqttBrokerClient;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseTopicObj;
import com.ivanboyukliev.plantsirrigationsystem.utils.FirebaseRetrievedDataConverter;

public class BrokerDataChangeListener implements ValueEventListener {

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        HomeActivity.getMqttBrokersList().clear();
        for (DataSnapshot broker : snapshot.getChildren()) {
            BasicMqttBrokerClient mqttBroker = new BasicMqttBrokerClient();
            mqttBroker.setBrokerID(broker.getKey());

            String brokerName = broker.child("/brkName").getValue(String.class);
            mqttBroker.setBrokerName(brokerName);

            String firebaseBrokerUrl = broker.getKey();
            String standardBrokerUrl = FirebaseRetrievedDataConverter.convertServerURIToStandard(firebaseBrokerUrl);

            int ipStartIdx = standardBrokerUrl.indexOf(":", standardBrokerUrl.indexOf(":") + 1);
            String brokerPort = standardBrokerUrl.substring(ipStartIdx + 1);
            String brokerIp = standardBrokerUrl.substring(0, ipStartIdx);

            mqttBroker.setBrokerIp(brokerIp);
            mqttBroker.setBrokerPort(brokerPort);

            mqttBroker.initClientData();

            populateTopics(mqttBroker, broker.child("/topics").getChildren());

            HomeActivity.getMqttBrokersList().add(mqttBroker);
            HomeActivity.getBrokersAdapter().notifyDataSetChanged();
        }
        HomeActivity.getBrokersAdapter().notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Log.i("DB ERROR", error.toString());
    }

    private void populateTopics(BasicMqttBrokerClient mqttBroker, Iterable<DataSnapshot> topics) {
        for (DataSnapshot topic : topics) {
            Integer QoS = topic.getValue(Integer.class);
            String firebaseTopicName = topic.getKey();
            String standardTopicName = FirebaseRetrievedDataConverter.convertBrokerTopicToStandard(firebaseTopicName);
            mqttBroker.getTopics().add(new FirebaseTopicObj(standardTopicName, QoS));
        }
    }
}