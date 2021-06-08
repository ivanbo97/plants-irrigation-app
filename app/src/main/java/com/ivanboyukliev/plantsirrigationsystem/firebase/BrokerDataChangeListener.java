package com.ivanboyukliev.plantsirrigationsystem.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model.BasicMqttBrokerClient;

public class BrokerDataChangeListener implements ValueEventListener {

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        HomeActivity.getMqttBrokersList().clear();
        for (DataSnapshot broker : snapshot.getChildren()) {
            BasicMqttBrokerClient mqttBroker = new BasicMqttBrokerClient();
            String brokerName = broker.child("/brkName").getValue(String.class);
            mqttBroker.setBrokerName(brokerName);
            String brokerUrl = broker.child("/brkURI").getValue(String.class);
            int ipStartIdx = brokerUrl.indexOf(":", brokerUrl.indexOf(":") + 1);
            String brokerPort = brokerUrl.substring(ipStartIdx + 1);
            String brokerIp = brokerUrl.substring(0, ipStartIdx);
            mqttBroker.setBrokerIp(brokerIp);
            mqttBroker.setBrokerPort(brokerPort);
            mqttBroker.initClientData();
            populateTopics(mqttBroker, broker.child("/topics").getChildren());
            HomeActivity.getMqttBrokersList().add(mqttBroker);
            HomeActivity.getBrokersAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Log.i("DB ERROR", error.toString());
    }

    private void populateTopics(BasicMqttBrokerClient mqttBroker, Iterable<DataSnapshot> topics) {
        for (DataSnapshot topic : topics) {
            String topicName = topic.getValue(String.class);
            mqttBroker.getTopics().add(topicName);
        }
    }
}