package com.ivanboyukliev.plantsirrigationsystem.utils;

import android.view.View;

import java.util.regex.Pattern;

public final class ApplicationConstants {
    public static final String INCORRECT_INPUT_MESSAGE = "Please make sure all fields are filled out!";

    public static final String REGISTRATION_ERROR_MESSAGE = "Unsuccessful attempt for signing up! Please try again";

    public static final String EMPTY_FILED_MESSAGE = "Field can't be empty!";

    public static final String INCORRECT_EMAIL_MESSAGE = "Please enter a valid email address!";

    public static final String INCORRECT_PORT_MESSAGE = "Please enter a valid port number!";

    public static final String INCORRECT_QOS_MESSAGE = "Please enter a valid QoS number!";

    public static final String WEAK_PASSWORD_MESSAGE = "Password is too weak! You should have at least 6 chars " +
            "including at least one upper case," +
            "lower case letter, special symbol and no white spaces!";

    public static final String BROKER_CONNECTION_ERROR_MSG = "Connection to broker failed! Please check your network connection";

    public static final String AUTHORIZATION_ERROR = "Not authorized to connect";

    public static final String BROKER_CONN_ERR = "Unable to connect to server";

    public static final String DB_URL = "https://plantsirrigationsystem-2d4ed-default-rtdb.europe-west1.firebasedatabase.app";

    public static final Pattern PORT_PATTERN = Pattern.compile("[0-9]{1,5}");

    public static final Pattern QOS_PATTERN = Pattern.compile("[0-2]{1}");

    public static final int MAX_PORT_NUMBER = 65535;

    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +             //at least 1 digit
            "(?=.*[a-z])" +            //at least 1 lower case letter
            "(?=.*[A-Z])" +           //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +       //any letter
            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +          //no white spaces
            ".{6,}" +              //at least 6 characters
            "$");


    public static final int NAV_BAR_INVISIBLE = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;


    public static final String TOPIC_REG_DIALOG_TITLE = "Enter topic and its related QoS";
    public static final String TOPIC_REG_ERROR = "Connection to Broker is lost! Cannot add new topic!";
    public static final String TOPIC_ADD_BTN_TXT = "Add new topic";
    public static final String TOPIC_SUBS_BTN_TXT = "Subscribe to above topics";
    public static final String TOPICS_LIST_TITLE = "Topics Subscription List";
    public static final String TOPICS_SUBS_ERROR = "You have to be connected to MQTT broker in ordered to subscribe!";
    public static final String NO_TOPICS_ERROR = "There aren't any specified topics for subscription.";
    public static final String TOPICS_SUBS_SUCCESS = "Successful subscription to given topics!";

    public static final String CREDENTIALS_DIALOG_TITLE = "Enter Credentials";

    public static final String BROKER_REG_DIALOG_TITLE = "MQTT Broker Registration";
    public static final String BROKER_DEL_ERROR = "Broker is disconnected cannot delete topic!";

    public static final String BROKER_CONNECTION_REMINDER = "Please, connect to broker first!";

    public static final String PLANTS_LIST_TITLE = "List of plants related to broker";
    public static final String PLANT_ADD_BTN_TXT = "Add new plant";
    public static final String PLANT_REG_ERROR = "Connection to Broker is lost! Cannot add new plant!";
    public static final String PLANT_INFO = "Tap on plant's name for more..";

    public static final String DELETE_TOPIC_DIALOG_TITLE = "Do you want to delete this item from DB?";
    public static final String DEL_TOPIC_BTN_TXT = "Delete";
    public static final String DEL_ITEM_DIALOG_TITLE = "Item Deletion";
}
