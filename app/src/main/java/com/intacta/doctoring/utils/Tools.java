package com.intacta.doctoring.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class Tools {
    public static String compromises = "Compromissos";
    public static String patients = "Pacientes";
    public static String notifications = "Notifications";

    public static final int RC_SIGN_IN = 123;


    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("dd/mm/yyy").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }


    public static String formattomyday(Date date){
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);

    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

}
