package com.ivanboyukliev.plantsirrigationsystem.mqtt;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

public class TopicSubscriptionCallBack implements IMqttActionListener {

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.i("Mqtt","Subscribed!");
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.w("Mqttt", "Subscription failed!");
    }
}
