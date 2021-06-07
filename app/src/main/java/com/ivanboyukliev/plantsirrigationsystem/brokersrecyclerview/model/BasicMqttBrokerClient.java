package com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model;

import android.util.Log;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.R;
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

import javax.net.ssl.SSLSocketFactory;

public class BasicMqttBrokerClient implements MqttClientActions {

    private String brokerName;
    private String brokerIp;
    private String brokerPort;
    private MqttAndroidClient mqttAndroidClient;
    private MqttCallbackExtended mqttCallback;
    private boolean isConnected;

    public BasicMqttBrokerClient() {

    }

    @Override
    public void initClientData() {
        String clientId = MqttClient.generateClientId();
        String brokerURI = brokerIp + ":" + brokerPort;
        Log.i("BROKER ADDRESS", brokerURI);
        mqttAndroidClient = new MqttAndroidClient(HomeActivity.getHomeActivityContext(), brokerURI, clientId);
        mqttCallback = new AndroidMqttClientCallback(this);
        mqttAndroidClient.setCallback(mqttCallback);
    }

    @Override
    public void connectClient(String username, String password) {

        try {
            ConnectionTokenCallback connectionTokenCallback = new ConnectionTokenCallback(this, mqttAndroidClient);
            MqttConnectOptions options = new MqttConnectOptions();

            try {
                InputStream caCrtFile = HomeActivity.getHomeActivityContext().getResources().openRawResource(R.raw.ca);
                InputStream crtFile = HomeActivity.getHomeActivityContext().getResources().openRawResource(R.raw.cert);
                InputStream keyFile = HomeActivity.getHomeActivityContext().getResources().openRawResource(R.raw.key);
                SSLSocketFactory sslSocketFactory = SSLConfigurator.getSocketFactory(caCrtFile, crtFile, keyFile, "");
                options.setSocketFactory(sslSocketFactory);
                options.setUserName(username);
                options.setPassword(password.toCharArray());
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

    public String getBrokerName() {
        return brokerName;
    }

    public String getBrokerIp() {
        return brokerIp;
    }

    public String getBrokerPort() {
        return brokerPort;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public void setBrokerIp(String brokerIp) {
        this.brokerIp = brokerIp;
    }

    public void setBrokerPort(String brokerPort) {
        this.brokerPort = brokerPort;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
