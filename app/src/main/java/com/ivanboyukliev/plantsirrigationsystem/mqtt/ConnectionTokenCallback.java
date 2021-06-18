package com.ivanboyukliev.plantsirrigationsystem.mqtt;

import android.util.Log;

import com.ivanboyukliev.plantsirrigationsystem.mqtt.api.MqttClientActions;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.home.HomeFragment;
import com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.AUTHORIZATION_ERROR;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.BROKER_CONNECTION_ERROR_MSG;

public class ConnectionTokenCallback implements IMqttActionListener {

    private MqttClientActions clientActions;
    private MqttAndroidClient mqttAndroidClient;

    public ConnectionTokenCallback(MqttClientActions clientActions, MqttAndroidClient androidClient) {
        this.clientActions = clientActions;
        this.mqttAndroidClient = androidClient;
    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.i("BROKER INFO", "Connection success!!");
        clientActions.subscribeToTopics();
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.i("BROKER INFO", "Connection failed!!");
        Log.i("REASON", exception.getMessage());
        HomeFragment.getConnectAndSubsBtn().setEnabled(true);
        HomeFragment.getBrokerDisconnectBtn().setEnabled(false);
        if (exception.getMessage().equals(AUTHORIZATION_ERROR)) {
            HomeFragment.showBrokerMessage(AUTHORIZATION_ERROR);
        }
        if (exception.getMessage().equals(ApplicationConstants.BROKER_CONN_ERR)) {
            HomeFragment.showBrokerMessage(BROKER_CONNECTION_ERROR_MSG);
        }
        exception.printStackTrace();
    }
}
