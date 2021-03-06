package com.ivanboyukliev.plantsirrigationsystem.mqtt;

import android.util.Log;

import com.ivanboyukliev.plantsirrigationsystem.PlantManagerActivity;
import com.ivanboyukliev.plantsirrigationsystem.navmenu.home.HomeFragment;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.BROKER_TOPICS_SUBS_ERROR;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.BROKER_TOPICS_SUBS_SUCCESS;

public class TopicSubscriptionCallBack implements IMqttActionListener {

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.i("Mqtt", "Subscribed!");
        HomeFragment.showBrokerMessage(BROKER_TOPICS_SUBS_SUCCESS);
        HomeFragment.getConnectAndSubsBtn().setEnabled(false);
        HomeFragment.getBrokerDisconnectBtn().setEnabled(true);
        PlantManagerActivity.getMqttClient()
                .getBrokerConnState()
                .setValue(true);
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.w("Mqttt", "Subscription failed!");
        HomeFragment.showBrokerMessage(BROKER_TOPICS_SUBS_ERROR);
        PlantManagerActivity.getMqttClient().disconnectClient();
        HomeFragment.getConnectAndSubsBtn().setEnabled(true);
        HomeFragment.getBrokerDisconnectBtn().setEnabled(false);
        PlantManagerActivity.getMqttClient()
                .getBrokerConnState()
                .setValue(false);
    }
}
