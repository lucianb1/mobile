package com.example.botos.appointment.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Botos on 12/7/2016.
 */
public abstract class DialogUtils {

    public static ProgressDialog createProgressDialog(Context context, boolean cancelable, String title, String message) {
        ProgressDialog dialog = null;
        try {
            dialog = new ProgressDialog(context);
            if (title != null) {
                dialog.setTitle(title);
            }
            if (message != null) {
                dialog.setMessage(message);
            }
            dialog.setCancelable(cancelable);
        } catch (Exception e) {

        }

        return dialog;
    }
}
