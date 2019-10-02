package com.intacta.doctoring.utils;

import android.annotation.SuppressLint;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class Tools {
    public static String user = "Users";
    public static String agenda = "Agenda";
    public static String compromises = "Compromissos";
    public static String patients = "Pacientes";
    public static String notifications = "Notifications";

    public static DatabaseReference services = FirebaseDatabase.getInstance().getReference("Services");
    public static final int RC_SIGN_IN = 123;




    public static DatabaseReference agendapath(){
        FirebaseUser userf = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseDatabase.getInstance().getReference(user).child(userf.getUid()).child(Tools.agenda);
    }

    public static DatabaseReference patientspath(){
        FirebaseUser userf = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseDatabase.getInstance().getReference(user).child(userf.getUid()).child(Tools.patients);
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("ddMMyyy").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }


    public static Date parseTime(String time){
        try {
            return  new SimpleDateFormat("hh:mm").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }




    public static String formatday(Date date){
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("ddMMyyyy");
        return df.format(date);

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
