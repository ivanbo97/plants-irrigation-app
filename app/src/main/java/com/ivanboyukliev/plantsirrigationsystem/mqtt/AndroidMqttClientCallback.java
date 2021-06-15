package com.ivanboyukliev.plantsirrigationsystem.mqtt;

import android.util.Log;

import com.ivanboyukliev.plantsirrigationsystem.HomeActivity;
import com.ivanboyukliev.plantsirrigationsystem.mqtt.api.MqttClientActions;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class AndroidMqttClientCallback implements MqttCallbackExtended {

    private MqttClientActions disconnectAction;

    public AndroidMqttClientCallback(MqttClientActions disconnectAction) {
        this.disconnectAction = disconnectAction;
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        Log.i("MQTT CLIENT STATUS:  ", "CONNECTED");
    }

    @Override
    public void connectionLost(Throwable cause) {
        Log.i("MQTT CLIENT STATUS:  ", "DISCONNECTED");
        HomeActivity.getBrokersAdapter().notifyDataSetChanged();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.i("DATA FROM BROKER", message.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
