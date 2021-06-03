package com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.model;

public class BasicMqttBroker {
    private String brokerName;
    private String brokerIp;
    private String brokerPort;

    public BasicMqttBroker() {

    }

    public BasicMqttBroker(String brokerName, String brokerIp, String brokerPort) {
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

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public void setBrokerIp(String brokerIp) {
        this.brokerIp = brokerIp;
    }

    public void setBrokerPort(String brokerPort) {
        this.brokerPort = brokerPort;
    }
}
