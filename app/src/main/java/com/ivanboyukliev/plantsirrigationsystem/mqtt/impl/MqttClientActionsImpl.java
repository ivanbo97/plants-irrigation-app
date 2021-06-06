package com.ivanboyukliev.plantsirrigationsystem.mqtt.impl;

import android.util.Log;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
import com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model.BasicMqttBroker;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.AndroidMqttClientCallback;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.ConnectionTokenCallback;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.DisconnectTokenCallback;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.api.MqttClientActions;
import com.ivanboyukliev.plantsirrigationsystem.ssl.SSLConfigurator;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;


import java.io.InputStream;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

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
        Log.i("BROKER ADDRESS", brokerURI);
        mqttAndroidClient = new MqttAndroidClient(HomeActivity.getHomeActivityContext(), brokerURI, clientId);
        mqttCallback = new AndroidMqttClientCallback(this);
        mqttAndroidClient.setCallback(mqttCallback);
    }

    @Override
    public void connectClient() {

        try {
            ConnectionTokenCallback connectionTokenCallback = new ConnectionTokenCallback(this, mqttAndroidClient);
            MqttConnectOptions options = new MqttConnectOptions();

            try {
                InputStream caCrtFile = HomeActivity.getHomeActivityContext().getResources().openRawResource(R.raw.ca);
                InputStream crtFile = HomeActivity.getHomeActivityContext().getResources().openRawResource(R.raw.cert);
                InputStream keyFile = HomeActivity.getHomeActivityContext().getResources().openRawResource(R.raw.key);
                SSLSocketFactory sslSocketFactory = SSLConfigurator.getSocketFactory(caCrtFile, crtFile, keyFile, "");
                options.setSocketFactory(sslSocketFactory);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            IMqttToken connectionToken = mqttAndroidClient.connect(options);
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

    public static void disconnectAllClients(List<MqttClientActions> clientActions) {
        for (MqttClientActions clientAction : clientActions) {
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
