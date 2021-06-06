package com.ivanboyukliev.plantsirrigationsystem.mqtt;


import android.util.Log;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.api.MqttClientActions;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.impl.MqttClientActionsImpl;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;

public class ConnectionTokenCallback implements IMqttActionListener {

    private MqttClientActions connectAction;
    private MqttAndroidClient mqttAndroidClient;

    public ConnectionTokenCallback(MqttClientActions connectAction, MqttAndroidClient androidClient) {
        this.connectAction = connectAction;
        this.mqttAndroidClient = androidClient;
    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.i("BROKER INFO", "Connection success!!");
        ((MqttClientActionsImpl) connectAction).setConnected(true);
        HomeActivity.getBrokersAdapter().notifyDataSetChanged();
        TopicSubscriptionCallBack topicSubscriptionCallBack = new TopicSubscriptionCallBack();
        try {
            mqttAndroidClient.subscribe("house/bulb1", 0, null, topicSubscriptionCallBack);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.i("BROKER INFO", "Connection failed!!");
        Log.i("REASON", exception.getMessage());
        exception.printStackTrace();
        ((MqttClientActionsImpl) connectAction).setConnected(false);
        HomeActivity.showBrokerConnectionError();
        HomeActivity.getBrokersAdapter().notifyDataSetChanged();
    }
}