package com.ivanboyukliev.plantsirrigationsystem.utils;

public class BasicMqttbroker {
    private String brokerName;
    private String brokerIp;
    private String brokerPort;

    public BasicMqttbroker(String brokerName, String brokerIp, String brokerPort) {
        this.brokerName = brokerName;
        this.brokerIp = brokerIp;
        this.brokerPort = brokerPort;
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
}
