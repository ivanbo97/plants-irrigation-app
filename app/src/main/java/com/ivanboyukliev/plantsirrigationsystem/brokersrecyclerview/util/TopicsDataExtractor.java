package com.ivanboyukliev.plantsirrigationsystem.brokersrecyclerview.util;

import com.ivanboyukliev.plantsirrigationsystem.firebase.model.FirebaseTopicObj;

import java.util.List;

public class TopicsDataExtractor {

    public static String[] extractCurrentTopicNames(List<FirebaseTopicObj> topics) {

        String[] topicNames = new String[topics.size()];
        int i = 0;
        for (FirebaseTopicObj topic : topics) {
            topicNames[i] = topic.getTopicName();
            i++;
        }
        return topicNames;
    }

    public static int[] extractCurrentTopicQoS(List<FirebaseTopicObj> topics) {
        int[] topicsQoS = new int[topics.size()];
        int i = 0;
        for (FirebaseTopicObj topic : topics) {
            topicsQoS[i] = topic.getQoS();
            i++;
        }
        return topicsQoS;
    }
}
