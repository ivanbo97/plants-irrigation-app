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

    public static final String BROKER_TOPICS_SUBS_ERROR = "Successful broker connection! Failure during topic subscription!";

    public static final String BROKER_TOPICS_SUBS_SUCCESS = "Connection to broker and topic subscription successful!";


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
    public static final String IRRIGATED_PLANT_TOPIC = "irrigatedPlant";
    public static final String CURRENT_BROKER_URL_TOPIC = "brokerUrl";

    public static final String CREDENTIALS_DIALOG_TITLE = "Enter Credentials";

    public static final String BROKER_REG_DIALOG_TITLE = "MQTT Broker Registration";
    public static final String BROKER_DEL_ERROR = "Broker is disconnected cannot delete topic!";

    public static final String BROKER_CONNECTION_REMINDER = "Please, connect to broker first!";

    public static final String PLANTS_LIST_TITLE = "List of plants related to broker";
    public static final String PLANT_ADD_BTN_TXT = "Add new plant";
    public static final String PLANT_REG_ERROR = "Connection to Broker is lost! Cannot add new plant!";
    public static final String PLANT_INFO = "Tap on plant's name for more..";

    public static final String DELETE_TOPIC_DIALOG_TITLE = "Do you want to delete this item and data related to it permanently?";
    public static final String DEL_TOPIC_BTN_TXT = "Delete";
    public static final String DEL_ITEM_DIALOG_TITLE = "Item Deletion";

    public static final String ADD_PLANT_DIALOG_TILE = "Please enter plant's name you want to add.";
    public static final String ADD_PLANT_DIALOG_POS_BTN = "Add plants to list";

    public static final String PLANT_API_PLANT_SEARCH_URL = "https://api.floracodex.com/v1/plants/search";
    public static final String PLANT_API_PLANT_DATA_URL = "https://api.floracodex.com/v1/species/";
    public static final String PLANT_API_TOKEN = "BflPxC8X3nAx9bnRAT4F";
    public static final String PLANT_NO_IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/600px-No_image_available.svg.png";

    public static final String MOISTURE_LEVEL_TOPIC = "moisture";
    public static final String TEMPERATURE_TOPIC = "temperature";
    public static final String PUMP_STATE_TOPIC = "pumpstate";
    public static final String MANAGE_PUMP_TOPIC = "pump";
    public static final String DELAYED_START_STATE_TOPIC = "auto1";
    public static final String MAINTAIN_MOISTURE_TASK_STATE_TOPIC = "auto2";

    public static final String ACTIVATE_MAINTAIN_MOISTURE_TOPIC = "automode2";
    public static final String MAINTAIN_MOISTURE_VALUE_TOPIC = "automode2/moisture";

    public static final String DELAYED_START_OPERATION_TOPIC = "automode1";
    public static final String DELAYED_START_DATE_TOPIC = "automode1/date";
    public static final String DELAYED_START_TIME_TOPIC = "automode1/time";
    public static final String DELAYED_START_DURATION_TOPIC = "automode1/duration";

    public static final String DELAYED_START_TOPICS = "automode1 , automode1/date , automode1/time, automode1/time, automode1/duration";

    public static final String PUMP_ACTIVE_FLAG = "on";
    public static final String PUMP_INACTIVE_FLAG = "off";
    public static final String DELAYED_START_INIT_FLAG = "on";
    public static final String DELAYED_START_INTERRUPT = "off";
    public static final String MOISTURE_MAINTAIN_FLAG = "on";
    public static final String MOISTURE_MAINTAIN_INTERRUPT = "off";

    public static final String ERROR_PUBLISH_MESSAGE = "Error occurred while publishing a message on topic: ";
    public static final String SUCCESSFUL_MESSAGE_PUBLISH = "Successfully published a message on topics: ";

    public static final String EMPTY_MOISTURE_FIELD = "Moisture field is empty! Please enter a value.";

    public static final String INCOMPLETE_DATA_DELAYED_START = "Incomplete data for delay pump start!!!";

    public static final String SEASONS[] = {
            "Winter", "Winter", "Spring", "Spring", "Summer", "Summer",
            "Summer", "Summer", "Fall", "Fall", "Winter", "Winter"
    };
}
