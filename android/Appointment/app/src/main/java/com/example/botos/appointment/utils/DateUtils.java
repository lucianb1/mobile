package com.example.botos.appointment.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Botos on 12/7/2016.
 */
public abstract class DateUtils {

    public static final String DAY_LONG_MONTH_DATE_FORMAT = "dd MMM";
    public static final String DAY_MONTH_DATE_FORMAT = "dd-MM";
    public static final String TIME_DATE_FORMAT = "HH:mm";
    public static final String DEFAULT_FORMATTER_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMATTER_PATTERN = "dd MMM yyyy";
    public static final String SERVER_DATE_FORMATTER_PATTERN = "dd/MM/yyyy";
//    public static final String DEFAULT_FORMATTER_PATTERN_FOR_USER = "yyyy-MM-dd'T'HH:mm:ss";

    private static DateFormat mDateFormatter;
    public static final SimpleDateFormat dayLongFormatter = new SimpleDateFormat("EEEE", Locale.getDefault());
    public static final SimpleDateFormat dayShortFormatter = new SimpleDateFormat("dd", Locale.getDefault());
    public static final SimpleDateFormat monthFullFormatter = new SimpleDateFormat("MMM", Locale.getDefault());
    public static final SimpleDateFormat hourFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }

        if (mDateFormatter == null) {
            mDateFormatter = new SimpleDateFormat(DEFAULT_FORMATTER_PATTERN, Locale.getDefault());
        }

        return mDateFormatter.format(date);
    }

    public static Date parseDate(String date) {
        if (StringUtils.isNullOrEmpty(date)) {
            return Calendar.getInstance().getTime();
        }

        if (mDateFormatter == null) {
            mDateFormatter = new SimpleDateFormat(DEFAULT_FORMATTER_PATTERN, Locale.getDefault());
        }

        try {
            return mDateFormatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Calendar.getInstance().getTime();
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(pattern, new Locale("nl")).format(date);
    }

}
