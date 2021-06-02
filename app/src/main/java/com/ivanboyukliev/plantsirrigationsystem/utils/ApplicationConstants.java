package com.ivanboyukliev.plantsirrigationsystem.utils;

import java.util.regex.Pattern;

public final class ApplicationConstants {
    public static final String INCORRECT_INPUT_MESSAGE = "Please make sure all fields are filled out!";
    public static final String REGISTRATION_ERROR_MESSAGE = "Unsuccessful attempt for signing up! Please try again";
    public static final String EMPTY_FILED_MESSAGE = "Field can't be empty!";
    public static final String INCORRECT_EMAIL_MESSAGE = "Please enter a valid email address!";
    public static final String WEAK_PASSWORD_MESSAGE = "Password too weak! You should have at least 6 chars " +
                                                       "including at least one upper case," +
                                                       "lower case letter, special symbol and no white spaces!";
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +             //at least 1 digit
            "(?=.*[a-z])" +            //at least 1 lower case letter
            "(?=.*[A-Z])" +           //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +       //any letter
            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +          //no white spaces
            ".{6,}" +              //at least 6 characters
            "$");


}
