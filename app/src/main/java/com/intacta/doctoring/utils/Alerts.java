package com.intacta.doctoring.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import com.intacta.doctoring.database.Clientsdb;
import com.intacta.doctoring.database.Compromissedb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static com.intacta.doctoring.utils.Tools.RC_SIGN_IN;

public class Alerts {
    private Activity activity;
    private Compromissedb compromissedb;
    private  Clientsdb clientsdb;

    public Alerts(@NotNull Activity activity) {
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ClientAlert() {
        Dialog dialog = new Dialog(activity, R.style.Dialog_No_Border);
        dialog.setContentView(R.layout.client_dialog);
        final Button savebtn = dialog.findViewById(R.id.savebtn);
        final TextInputLayout clientname = dialog.findViewById(R.id.clientname);
        final TextInputLayout phonefield = dialog.findViewById(R.id.phonefield);
        final TextInputLayout emailfield = dialog.findViewById(R.id.emailfield);
        final TextInputLayout datefield = dialog.findViewById(R.id.datefield);
        final TextView title = dialog.findViewById(R.id.title);

        datefield.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    final DatePickerDialog pickerDialog = new DatePickerDialog(activity);

                    pickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Calendar myCalendar = Calendar.getInstance();
                            Date dia;
                            myCalendar.set(year, month, dayOfMonth);
                            dia = myCalendar.getTime();
                            datefield.getEditText().setText(Tools.formattomyday(dia));
                            pickerDialog.dismiss();
                        }
                    });
                    pickerDialog.show();
                }
            }
        });

        datefield.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog pickerDialog = new DatePickerDialog(activity);

                pickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar myCalendar = Calendar.getInstance();
                        Date dia;
                        myCalendar.set(year, month, dayOfMonth);
                        dia = myCalendar.getTime();
                        datefield.getEditText().setText(Tools.formattomyday(dia));
                     }
                });
                pickerDialog.show();
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
                    cliente.setDataNascimento(datefield.getEditText().getText().toString());
                    cliente.setTelefone(phonefield.getEditText().getText().toString());
                    cliente.setEmail(emailfield.getEditText().getText().toString());
                    if (!cliente.getNome().isEmpty() && cliente.getEmail().isEmpty() && cliente.getTelefone().isEmpty()) {
                        ProgressDialog loading = new ProgressDialog(activity);
                        loading.setMessage("Adicionando cliente...");
                        loading.show();
                        saveclient(cliente,loading);
                    }else{
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity,R.style.AppTheme);
                        alertDialog.setMessage("Preencha todos os campos");
                        alertDialog.setNeutralButton("Ok",null);
                        alertDialog.show();
                    }


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


    private void saveclient(Cliente cliente,ProgressDialog progressDialog) {

        clientsdb = new Clientsdb(activity);
        clientsdb.saveclient(cliente,progressDialog);

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
        final TextInputLayout date = mView.findViewById(R.id.txl_date);
        final TextInputLayout time = mView.findViewById(R.id.txl_hour);
        final Calendar calendar = Calendar.getInstance();


        clientsdb = new Clientsdb(activity);
        final List<Cliente> clienteList = new ArrayList<>();
        clientsdb.loadCliente(client,clienteList);

        date.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    final DatePickerDialog datePickerDialog = new DatePickerDialog(activity);

                    datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Date dia;
                            calendar.set(year, month, dayOfMonth);
                            dia = calendar.getTime();
                            date.getEditText().setText(Tools.formattomyday(dia));
                            datePickerDialog.dismiss();
                        }
                    });
                    datePickerDialog.show();
                }
            }
        });
        time.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    final TimePickerDialog timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendar.set(Calendar.MINUTE, minute);
                            time.getEditText().setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
                        }
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                    timePickerDialog.show();
                }
            }

        });

        db.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Compromisso compromisso = new Compromisso(
                        time.getEditText().getText().toString(),
                        service.getEditText().getText().toString(),
                        clienteList.get(client.getSelectedItemPosition()).getId()
                );
                compromissedb = new Compromissedb(activity);
                compromissedb.sendCompromisso(compromisso,calendar);
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
        final Agenda agenda = new Agenda(Tools.formattomyday(calendar.getTime()));

        final Calendar calendartime = Calendar.getInstance();
        final int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        final int currentMinute = calendar.get(Calendar.MINUTE);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                Log.i("LOG", hourOfDay + ":" + minutes);

                calendartime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendartime.set(Calendar.MINUTE,minutes);
                compromisso.setTime(String.valueOf(calendartime.get(Calendar.HOUR_OF_DAY))+ ":" + String.valueOf(calendartime.get(Calendar.MINUTE)));
                compromissedb.sendCompromisso(compromisso,calendar);
            }
        }, currentHour, currentMinute, true);
        timePickerDialog.show();

    }



    public void Options(final Compromisso c){
        String[] options = {"Editar compromisso","Remover compromisso"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("LOG",String.valueOf(which));
                    switch (which) {
                        case 0:
                            //CompromissoAlert(c,id);
                            //cd.Update(c, id);
                            break;
                        case 1:
                            Compromissedb cd = new Compromissedb(activity);
                            cd.Delete(c);
                            break;
                    }
                }
            }).show();
    }


}

