package com.ivanboyukliev.plantsirrigationsystem.mqtt;

import android.util.Log;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

public class ConnectionTokenCallback implements IMqttActionListener {

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.i("BROKER INFO", "Connection success!!");
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.i("BROKER INFO", "Connection failed!!");
        HomeActivity.showBrokerConnectionError();
    }
}
