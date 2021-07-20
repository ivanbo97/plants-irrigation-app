package com.ivanboyukliev.plantsirrigationsystem.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseBroker;
import com.ivanboyukliev.plantsirrigationsystem.firebase.util.FirebaseDataExtractor;
import com.ivanboyukliev.plantsirrigationsystem.utils.FirebaseRetrievedDataConverter;

public class BrokerDataChangeListener implements ValueEventListener {

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        for (DataSnapshot broker : snapshot.getChildren()) {
            FirebaseBroker mqttBroker = new FirebaseBroker();
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

            FirebaseDataExtractor.retrieveTopicsAndPopulateBroker(mqttBroker, broker.child("/topics").getChildren());
            FirebaseDataExtractor.retrievePlantsAndPopulateBroker(mqttBroker, broker.child("/plants").getChildren());

            HomeActivity.getMqttBrokersList().add(mqttBroker);
            HomeActivity.getBrokersAdapter().notifyDataSetChanged();
        }
        HomeActivity.getBrokersAdapter().notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        HomeActivity.showBrokerMessage(error.getMessage());
    }

}