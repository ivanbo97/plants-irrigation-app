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

}
