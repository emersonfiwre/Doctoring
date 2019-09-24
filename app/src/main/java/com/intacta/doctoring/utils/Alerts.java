package com.intacta.doctoring.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.intacta.doctoring.R;
import com.intacta.doctoring.beans.Agenda;
import com.intacta.doctoring.beans.Cliente;
import com.intacta.doctoring.beans.Compromisso;
import com.intacta.doctoring.database.Compromissedb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.intacta.doctoring.utils.Tools.RC_SIGN_IN;

public class Alerts {
    private Activity activity;
    private FireDatabase fireDatabase;


    public Alerts(@NotNull Activity activity) {
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ClientAlert() {
        Dialog dialog = new Dialog(activity, R.style.AppTheme);
        dialog.setContentView(R.layout.client_dialog);
        final Button savebtn = dialog.findViewById(R.id.savebtn);
        final TextInputLayout clientname = dialog.findViewById(R.id.clientname);
        final TextInputLayout phonefield = dialog.findViewById(R.id.phonefield);
        final TextInputLayout emailfield = dialog.findViewById(R.id.emailfield);
        final TextView datatext = dialog.findViewById(R.id.datatext);
        final TextView title = dialog.findViewById(R.id.title);


        final DatePicker calendarView = dialog.findViewById(R.id.calendar);


        calendarView.setMaxDate(new Date().getTime());


        calendarView.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar myCalendar = Calendar.getInstance();
                Date dia;
                myCalendar.set(year, monthOfYear, dayOfMonth);
                dia = myCalendar.getTime();
                datatext.setText(Tools.formattomyday(dia));
            }
        });


        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savebtn.setEnabled(false);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Cliente cliente = new Cliente();
                    cliente.setNome(clientname.getEditText().getText().toString());
                    cliente.setDataNascimento(datatext.getText().toString());
                    cliente.setTelefone(phonefield.getEditText().getText().toString());
                    cliente.setEmail(emailfield.getEditText().getText().toString());
                    cliente.setDoctor(user.getUid());

                    saveclient(cliente);

                            clientname.setVisibility(View.GONE);
                            emailfield.setVisibility(View.GONE);
                            phonefield.setVisibility(View.GONE);
                            datatext.setVisibility(View.GONE);
                            calendarView.setVisibility(View.GONE);
                        title.setText("Cliente Adicionado com sucesso!");

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


    protected void saveclient(Cliente cliente) {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Adicionando cliente");
        DatabaseReference clientdb = FirebaseDatabase.getInstance().getReference(Tools.patients);
        clientdb.push().setValue(cliente).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
        public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.setMessage("Cliente adicionado com sucesso!");
                } else {
                    progressDialog.setMessage("Erro ao salvar " + task.getException().getMessage());
                }

                CountDownTimer timer = new CountDownTimer(3500, 100) {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CompromissoAlert() {
        AlertDialog.Builder db = new AlertDialog.Builder(activity, R.style.AppTheme);
        //db.setView(R.layout.compromisso_dialog);
        LayoutInflater inflater = activity.getLayoutInflater();
        View mView = inflater.inflate(R.layout.compromisso_dialog, null);
        db.setView(mView);

        final Spinner client = mView.findViewById(R.id.spinner_cliente);
        final TextInputLayout service = mView.findViewById(R.id.txl_service);
        final DatePicker datePicker = mView.findViewById(R.id.date_compromisso);
        final Calendar calendar = Calendar.getInstance();

        fireDatabase = new FireDatabase(activity);
        final List<Cliente> clienteList = new ArrayList<>();
        fireDatabase.loadCliente(client,clienteList);

        db.setPositiveButton("Proximo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                service.getEditText().getText().toString();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                calendar.set(year,month,day);
                //String data = String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
                Compromisso compromisso = new Compromisso(
                        null,
                        service.getEditText().getText().toString(),
                        clienteList.get(client.getSelectedItemPosition()).getId()
                );
                timePicker(compromisso,calendar);

            }
        });
        db.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        Dialog dialog = db.create();

        dialog.show();

    }
    private void timePicker(final Compromisso compromisso, final Calendar calendar){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final Agenda agenda = new Agenda(user.getUid(),Tools.formattomyday(calendar.getTime()));

        final Calendar calendartime = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                Log.i("LOG", hourOfDay + ":" + minutes);

                calendartime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendartime.set(Calendar.MINUTE,minutes);
                compromisso.setTime(String.valueOf(calendartime.get(Calendar.HOUR_OF_DAY))+ ":" + String.valueOf(calendartime.get(Calendar.MINUTE)));
                fireDatabase.sendCompromisso(compromisso,agenda,calendar);

            }
        }, currentHour, currentMinute, true);
        timePickerDialog.show();

    }

    public void Delete(final Compromisso c, final String id){
        String[] options = {"Remover compromisso"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Compromissedb cd = new Compromissedb();
                    cd.Delete(c,id);
                }
            }).show();
    }


}

