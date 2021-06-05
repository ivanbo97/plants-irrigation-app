package com.ivanboyukliev.plantsirrigationsystem.mqtt.impl;

import android.util.Log;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model.BasicMqttBroker;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.AndroidMqttClientCallback;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.ConnectionTokenCallback;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.DisconnectTokenCallback;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.api.MqttClientActions;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

public class MqttClientActionsImpl implements MqttClientActions {

    private MqttAndroidClient mqttAndroidClient;
    private BasicMqttBroker mqttBroker;
    private MqttCallbackExtended mqttCallback;
    private boolean isConnected;

    public MqttClientActionsImpl(BasicMqttBroker mqttBroker) {
        this.mqttBroker = mqttBroker;
        initClientData();
    }

    @Override
    public void initClientData() {
        String clientId = MqttClient.generateClientId();
        String brokerURI = mqttBroker.getBrokerIp() + ":" + mqttBroker.getBrokerPort();
        Log.i("BROKER ADDRESS",brokerURI);
        mqttAndroidClient = new MqttAndroidClient(HomeActivity.getHomeActivityContext(), brokerURI, clientId);
        mqttCallback = new AndroidMqttClientCallback(this);
        mqttAndroidClient.setCallback(mqttCallback);
    }

    @Override
    public void connectClient() {

        try {
            ConnectionTokenCallback connectionTokenCallback = new ConnectionTokenCallback(this);
            IMqttToken connectionToken =  mqttAndroidClient.connect();
            connectionToken.setActionCallback(connectionTokenCallback);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnectClient() {
        try {
            DisconnectTokenCallback disconnectTokenCallback = new DisconnectTokenCallback(this);
            IMqttToken disconnectionToken = mqttAndroidClient.disconnect();
            disconnectionToken.setActionCallback(disconnectTokenCallback);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void disconnectAllClients (List<MqttClientActions> clientActions){
        for(MqttClientActions clientAction : clientActions){
            clientAction.disconnectClient();
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
