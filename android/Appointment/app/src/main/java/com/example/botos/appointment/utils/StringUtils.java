package com.example.botos.appointment.utils;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.botos.appointment.R;

import java.util.Locale;

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

    public static boolean validateEditText(TextInputLayout textInputLayout, Context context) {
        if (textInputLayout.getEditText().getText().toString().isEmpty()) {
            textInputLayout.getEditText().setError(String.format(Locale.getDefault(), "%s %s %s", context.getString(R.string.field), textInputLayout.getHint().toString().toLowerCase(Locale.getDefault()),
                    context.getString(R.string.is_mandatory)));
            textInputLayout.getEditText().requestFocus();
            return false;
        }
        textInputLayout.setErrorEnabled(false);
        return true;
    }
}
