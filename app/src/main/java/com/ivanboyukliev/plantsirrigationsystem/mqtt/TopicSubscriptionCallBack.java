package com.ivanboyukliev.plantsirrigationsystem.mqtt;

import android.util.Log;

import com.ivanboyukliev.plantsirrigationsystem.navmenu.home.HomeFragment;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

public class TopicSubscriptionCallBack implements IMqttActionListener {

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.i("Mqtt", "Subscribed!");
        HomeFragment.showBrokerMessage("Connection to broker and topic subscription successful!");
        HomeFragment.getConnectAndSubsBtn().setEnabled(false);
        HomeFragment.getBrokerDisconnectBtn().setEnabled(true);
        HomeFragment.setConnectedToBroker(true);
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.w("Mqttt", "Subscription failed!");
        HomeFragment.showBrokerMessage("Successful broker connection! Failure during topic subscription!");
        HomeFragment.getMqttClient().disconnectClient();
        HomeFragment.getConnectAndSubsBtn().setEnabled(true);
        HomeFragment.getBrokerDisconnectBtn().setEnabled(false);
        HomeFragment.setConnectedToBroker(false);
    }
}
