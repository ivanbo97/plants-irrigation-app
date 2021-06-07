package com.ivanboyukliev.plantsirrigationsystem.mqtt;

import android.util.Log;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.api.MqttClientActions;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.impl.MqttClientActionsImpl;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.BROKER_CONNECTION_ERROR_MSG;

public class DisconnectTokenCallback implements IMqttActionListener {

    private MqttClientActions disconnectAction;

    public DisconnectTokenCallback(MqttClientActions disconnectAction) {
        this.disconnectAction = disconnectAction;
    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.i("BROKER INFO", "Disconnection successful!!");
        ((MqttClientActionsImpl) disconnectAction).setConnected(false);
        HomeActivity.getBrokersAdapter().notifyDataSetChanged();
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.i("BROKER INFO", "Disconnection failed!!");
        ((MqttClientActionsImpl) disconnectAction).setConnected(true);
        HomeActivity.showBrokerError(BROKER_CONNECTION_ERROR_MSG);
        HomeActivity.getBrokersAdapter().notifyDataSetChanged();
    }
}
