package com.ivanboyukliev.plantsirrigationsystem.mqtt.api;

public interface MqttClientActions {

    void initClientData();

    void connectClient();

    void disconnectClient();
}
