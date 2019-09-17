package com.intacta.doctoring.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.intacta.doctoring.R;
import com.intacta.doctoring.beans.Cliente;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.mateware.snacky.Snacky;

import static com.intacta.doctoring.utils.Tools.RC_SIGN_IN;

public class Alerts {
    private Activity activity;


    public Alerts( @NotNull Activity activity) {
        this.activity = activity;
    }

    public void ClientAlert(){
        Dialog dialog = new Dialog(activity,R.style.AppTheme);
        dialog.setContentView(R.layout.client_dialog);

        Button savebtn = dialog.findViewById(R.id.savebtn) ;
       final TextInputLayout clientname = dialog.findViewById(R.id.clientname);
       final TextInputLayout phonefield = dialog.findViewById(R.id.phonefield);
        final TextInputLayout emailfield = dialog.findViewById(R.id.emailfield);
        final TextView datatext = dialog.findViewById(R.id.datatext);



        CalendarView calendarView = dialog.findViewById(R.id.calendar);

        calendarView.setMaxDate(new Date().getTime());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                datatext.setText(String.format("%d/%d/%d", dayOfMonth, month, year));
            }
        });


       savebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

               if (user != null) {
                   Cliente cliente = new Cliente();
                   cliente.setNome(clientname.getEditText().getText().toString());
                   cliente.setDataNascimento(datatext.getText().toString());
                   cliente.setTelefone(phonefield.getEditText().getText().toString());
                   cliente.setEmail(emailfield.getEditText().getText().toString());
                   cliente.setDoctor(user.getUid());
                   saveclient(cliente);
               }else{
                   AlertDialog.Builder builder = new AlertDialog.Builder(activity).setTitle("Desconectado!");
                   builder.setMessage("Não é possível fazer isso se estiver desconectado");
                   builder.setPositiveButton("Realizar login", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           List<AuthUI.IdpConfig> providers = Collections.singletonList(
                                   new AuthUI.IdpConfig.GoogleBuilder().build());
                           activity.startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                                   .setLogo(R.mipmap.ic_launcher)
                                   .setAvailableProviders(providers)
                                   .setTheme(R.style.AppTheme)
                                   .build(),RC_SIGN_IN);
                       }
                   });
                   builder.setNegativeButton("Cancelar",null);
                   builder.show();
               }
           }
       });
       dialog.show();
    }


    private void saveclient(Cliente cliente){
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Adicionando cliente");
        DatabaseReference clientdb = FirebaseDatabase.getInstance().getReference(Tools.patients);
        clientdb.push().setValue(cliente).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.setMessage("Cliente adicionado com sucesso!");
                }else{
                    progressDialog.setMessage("Erro ao salvar " + task.getException().getMessage());
                }

                CountDownTimer timer = new CountDownTimer(3500,100) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        progressDialog.dismiss();
                    }
                }.start();
            }
        });

    }


    private void datedialog(final TextInputLayout datefield){





    }

}

