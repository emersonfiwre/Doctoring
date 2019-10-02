package com.intacta.doctoring.database;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.intacta.doctoring.beans.Specialitie;
import com.intacta.doctoring.utils.Alerts;
import com.intacta.doctoring.utils.Tools;

public class Servicesdb {
    private Activity activity;

    public Servicesdb(Activity activity) {
        this.activity = activity;
    }

    public void addservice(String service, Specialitie specialitie){
        DatabaseReference db = Tools.services;
        db.child(service).push().setValue(specialitie).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Alerts alerts = new Alerts(activity);
                if (task.isSuccessful()){
                    alerts.Message("Serviço adicionado com sucesso",alerts.success);
                }else {
                    alerts.Message("Erro ao adicionar serviço", alerts.error);
                }
            }
        });
    }
}
