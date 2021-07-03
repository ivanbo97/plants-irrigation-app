package com.ivanboyukliev.plantsirrigationsystem.utils;

public class FirebaseRetrievedDataConverter {

    public static String convertBrokerTopicToStandard(String topicName){
        return topicName.replace("(","/");
    }

    public static String convertServerURIToStandard(String serverURI){
        return serverURI.replace("!", ":")
                .replace(",", ".")
                .replace("(", "/");
    }

    public static String extractIdFromPlantName(String plantName) {
        return plantName.substring(plantName.indexOf('|')+1);
    }
}
