package com.ivanboyukliev.plantsirrigationsystem.mqtt;

import android.util.Log;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.api.MqttClientActions;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.impl.MqttClientActionsImpl;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

public class ConnectionTokenCallback implements IMqttActionListener {

    private MqttClientActions connectAction;

    public ConnectionTokenCallback(MqttClientActions connectAction) {
        this.connectAction = connectAction;
    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.i("BROKER INFO", "Connection success!!");
        ((MqttClientActionsImpl) connectAction).setConnected(true);
        HomeActivity.getBrokersAdapter().notifyDataSetChanged();
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.i("BROKER INFO", "Connection failed!!");
        ((MqttClientActionsImpl) connectAction).setConnected(false);
        HomeActivity.showBrokerConnectionError();
        HomeActivity.getBrokersAdapter().notifyDataSetChanged();
    }
}
