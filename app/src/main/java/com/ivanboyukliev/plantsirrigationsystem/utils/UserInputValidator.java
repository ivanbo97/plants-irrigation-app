package com.ivanboyukliev.plantsirrigationsystem.utils;

import android.util.Patterns;

import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.MAX_PORT_NUMBER;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PASSWORD_PATTERN;
import static com.ivanboyukliev.plantsirrigationsystem.utils.ApplicationConstants.PORT_PATTERN;


public class UserInputValidator {

    public static boolean isEmailValid(String email) {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }
        return true;
    }

    public static boolean isPasswordValid(String password) {
        if (password.isEmpty() || !PASSWORD_PATTERN.matcher(password).matches()) {
            return false;
        }
        return true;
    }

    public static boolean isPortValid(String port) {
        if (!PORT_PATTERN.matcher(port).matches() || Integer.valueOf(port) > MAX_PORT_NUMBER) {
            return false;
        }
        return true;
    }
}
