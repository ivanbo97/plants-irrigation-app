package com.ivanboyukliev.plantsirrigationsystem.utils;

public class UserInputConverter {

    public static String convertServerURIToFirebaseRules(String serverURI) {
        return serverURI.replace(":", "!")
                .replace(".", ",")
                .replace("/", "(");
    }


    public static String convertBrokerTopicToFirebaseRules(String topicName) {
        return topicName.replace("/", "(");
    }


}
