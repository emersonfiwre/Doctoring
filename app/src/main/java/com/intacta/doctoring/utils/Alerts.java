package com.intacta.doctoring.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.ChipGroup;
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
import com.intacta.doctoring.beans.Specialitie;
import com.intacta.doctoring.database.Clientsdb;
import com.intacta.doctoring.database.Compromissedb;
import com.intacta.doctoring.database.Servicesdb;
import com.wajahatkarim3.easymoneywidgets.EasyMoneyEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static com.intacta.doctoring.utils.Tools.RC_SIGN_IN;

public class Alerts {
    private ArrayList<Specialitie> specialities;
    private Activity activity;
    private Compromissedb compromissedb;
    private  Clientsdb clientsdb;
    private  int style = R.style.Dialog_No_Border;
    public Drawable success,error;
    public Alerts(@NotNull Activity activity) {
        this.activity = activity;
        success = activity.getDrawable(R.drawable.ic_check_black_24dp);
        error = activity.getDrawable(R.drawable.ic_clear_black_24dp);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ClientAlert() {
        Dialog dialog = new Dialog(activity,R.style.Dialog_No_Border);
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
                    if (validaCampos(clientname,phonefield,datefield)) {
                        Cliente cliente = new Cliente();
                        cliente.setNome(clientname.getEditText().getText().toString());
                        cliente.setDataNascimento(datefield.getEditText().getText().toString());
                        cliente.setTelefone(phonefield.getEditText().getText().toString());
                        cliente.setEmail(emailfield.getEditText().getText().toString());
                        saveclient(cliente);
                        savebtn.setEnabled(true);
                    }else{
                        Toast.makeText(activity,"Insira os campos correntamente",Toast.LENGTH_LONG).show();
                        savebtn.setEnabled(true);
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
    private boolean validaCampos(TextInputLayout name, TextInputLayout phone,TextInputLayout date){

        return !name.getEditText().getText().toString().isEmpty() && !phone.getEditText().getText().toString().isEmpty() && !date.getEditText().getText().toString().isEmpty();
    }




    public void AddService(){
        final Dialog dialog = new Dialog(activity,style);
        dialog.setContentView(R.layout.service_dialog);
        final TextInputLayout servicename = dialog.findViewById(R.id.clientname);
        Button savebtn = dialog.findViewById(R.id.savebtn);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Specialitie(servicename.getEditText().getText().toString());
                dialog.dismiss();

            }
        });


        dialog.show();


    }

    private void Specialitie(final String service) {

        final Dialog dialog = new Dialog(activity,style);
        dialog.setContentView(R.layout.speciality_dialog);
       ImageView icon = dialog.findViewById(R.id.icon);
       TextView title = dialog.findViewById(R.id.title);
      final TextInputLayout clientname = dialog.findViewById(R.id.clientname);
       TextInputLayout price = dialog.findViewById(R.id.price);
       final EasyMoneyEditText moneyEditText = dialog.findViewById(R.id.moneyEditText);
         Button savebtn = dialog.findViewById(R.id.savebtn);

        title.setText("Adicione uma especialidade para concluir");
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Specialitie specialitie = new Specialitie();
                specialitie.setNome(clientname.getEditText().getText().toString());
                specialitie.setPreco(Double.parseDouble(moneyEditText.getValueString()));
                Servicesdb servicesdb = new Servicesdb(activity);
                dialog.dismiss();
                servicesdb.addservice(service,specialitie);
            }
        });
        dialog.show();



    }


    private void saveclient(Cliente cliente) {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Adicionando cliente");
        clientsdb = new Clientsdb(activity);
        clientsdb.saveclient(cliente,progressDialog);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CompromissoAlert() {
        AlertDialog.Builder db = new AlertDialog.Builder(activity,  R.style.Dialog_With_Border);
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


    public void Options(final Compromisso c, final String id){
        String[] options = {"Editar compromisso","Remover compromisso"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("LOG",String.valueOf(which));
                switch (which) {
                    case 0:
                        UpdateCompromissoAlert(c, id);
                        //cd.Update(c, id);
                        break;
                    case 1:
                        Compromissedb cd = new Compromissedb(activity);
                        cd.Delete(c,id);
                        break;
                }
            }
        }).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void UpdateCompromissoAlert(final Compromisso compromisso, final String id) {
        AlertDialog.Builder db = new AlertDialog.Builder(activity, R.style.Dialog_With_Border);
        LayoutInflater inflater = activity.getLayoutInflater();
        View mView = inflater.inflate(R.layout.compromisso_dialog, null);
        db.setView(mView);

        final Spinner client = mView.findViewById(R.id.spinner_cliente);
        final TextInputLayout service = mView.findViewById(R.id.txl_service);
        final TextInputLayout data = mView.findViewById(R.id.txl_date);
        final TextInputLayout time = mView.findViewById(R.id.txl_hour);
        final Calendar calendar = Calendar.getInstance();


        clientsdb = new Clientsdb(activity);
        final List<Cliente> clienteList = new ArrayList<>();
        clientsdb.loadCliente(client,clienteList);

        data.setVisibility(View.GONE);
        service.getEditText().setText(compromisso.getCompromisso());
        time.getEditText().setText(compromisso.getTime());

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
                Compromissedb cd = new Compromissedb(activity);

                compromisso.setTime(time.getEditText().getText().toString());
                compromisso.setCompromisso(service.getEditText().getText().toString());
                compromisso.setCliente(clienteList.get(client.getSelectedItemPosition()).getId());

                cd.Update(compromisso, id);
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



    public void Message(String msg, Drawable icon){
        final Dialog dialog = new Dialog(activity,style);
        dialog.setContentView(R.layout.message_dialog);
        ImageView img = dialog.findViewById(R.id.icon);
        TextView message = dialog.findViewById(R.id.message);
        img.setImageDrawable(icon);
        message.setText(msg);
        dialog.show();
        CountDownTimer timer = new CountDownTimer(3000,100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                dialog.dismiss();
            }
        }.start();




    }


    public void selectspecialitie(String service){
        BottomSheetDialog mydialog = new BottomSheetDialog(activity,style);

    }

}

