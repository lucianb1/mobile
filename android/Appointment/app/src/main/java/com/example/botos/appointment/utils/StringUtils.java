package com.example.botos.appointment.utils;

import android.text.TextUtils;

/**
 * Created by Botos on 12/7/2016.
 */
public class StringUtils {

    public static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static Boolean isNullOrEmpty(String input) {
        return !(input != null && !input.isEmpty());
    }
}
