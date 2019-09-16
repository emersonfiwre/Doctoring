package com.intacta.doctoring.utils;

import android.app.Activity;
import android.app.Dialog;

import com.google.firebase.database.annotations.NotNull;
import com.intacta.doctoring.R;

public class Alerts {
    private Activity activity;


    public Alerts( @NotNull Activity activity) {
        this.activity = activity;
    }

    public void ClientAlert(){
        Dialog dialog = new Dialog(activity,R.style.AppTheme);
        dialog.setContentView(R.layout.client_dialog);
        dialog.show();
    }


}

