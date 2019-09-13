package com.intacta.doctoring.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.firebase.database.annotations.NotNull;
import com.intacta.doctoring.R;

import java.util.Calendar;

public class Alerts {
    private Activity activity;
    private DatePickerDialog.OnDateSetListener  dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;


    public Alerts( @NotNull Activity activity) {
        this.activity = activity;
    }

    public void ClientAlert(){
        Dialog dialog = new Dialog(activity,R.style.AppTheme);
        dialog.setContentView(R.layout.client_dialog);
        dialog.show();
    }


    private void dialogDatePicker(){
        Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialogDate = new DatePickerDialog(
                activity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                dateSetListener,
                ano,mes,dia
        );
        //dialogDate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogDate.show();



        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("LOG","onDateSet : dd/MM/YYYY= " + dayOfMonth+ "/" +month + "/" + year);
                dialogTimePicker();

            }
        };
    }
    private void dialogTimePicker(){
        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialogTime = new TimePickerDialog(
                activity,
                //R.style.MyTimePicker,
                timeSetListener,
                hora,min,true
        );
        //dialogTime.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTime.show();

        timeSetListener= new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Log.d("LOG","onTimeSet : HH:mm= " + hourOfDay+ ":" + minute);
            }
        };


    }


}
