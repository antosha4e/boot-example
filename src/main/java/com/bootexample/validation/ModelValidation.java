package com.bootexample.validation;

import com.bootexample.entity.User;

import java.util.regex.Pattern;

import static com.bootexample.util.BootUtils.isNOE;

/**
 * Created by antosha4e on 20.05.16.
 */
public class ModelValidation {
    private static final Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");

    public static boolean isUserValid(User user) {
        if(user == null) {
            return false;
        }

        if(isEmailValid(user.getEmail())) {
            return false;
        }

        if(isPasswordValid(user.getPassword())) {
            return false;
        }

        if(isNOE(user.getName())) {
            return false;
        }

        return true;
//        return !user.getModules().isEmpty();
    }

    public static boolean isPasswordValid(String password) {
        return !(password == null || password.length() < 3);
    }

    public static boolean isEmailValid(String email) {
        if(isNOE(email)) {
            return false;
        }

        return emailPattern.matcher(email).matches();
    }
}