package com.intacta.doctoring.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.intacta.doctoring.adapters.SpinnerAdapter;
import com.intacta.doctoring.beans.Agenda;
import com.intacta.doctoring.beans.Cliente;
import com.intacta.doctoring.beans.Compromisso;

import java.util.Calendar;
import java.util.List;

public class FireDatabase {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Context context;

    public FireDatabase(Context context){
        this.context = context;
        startFirebase();
    }
    private void startFirebase(){
        FirebaseApp.initializeApp(context);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    public void sendCompromisso(final Compromisso compromisso, final Agenda agenda, final Calendar calendar){
        final DatabaseReference agendaref = FirebaseDatabase.getInstance().getReference(Tools.agenda).child(Tools.formatday(calendar.getTime()));

        agendaref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    agendaref.setValue(agenda).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            DatabaseReference compromissoref = FirebaseDatabase.getInstance().getReference(Tools.agenda);
                            compromissoref.child(Tools.formatday(calendar.getTime())).child(Tools.compromises).push().setValue(compromisso).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                    dialog.setTitle("Concluido!");
                                    dialog.setMessage("Compromisso salvo com sucesso!");
                                    dialog.setPositiveButton("Ok",null);
                                    dialog.create();
                                    dialog.show();
                                }
                            });
                        }
                    });
                }else{
                    DatabaseReference compromissoref = FirebaseDatabase.getInstance().getReference(Tools.agenda);
                    compromissoref.child(Tools.formatday(calendar.getTime())).child(Tools.compromises).push().setValue(compromisso).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                            dialog.setTitle("Concluido!");
                            dialog.setMessage("Compromisso salvo com sucesso!");
                            dialog.setPositiveButton("Ok",null);
                            dialog.create();
                            dialog.show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void loadCliente(final Spinner spinner, final List<Cliente> clientes ){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query databaseReference = FirebaseDatabase.getInstance().getReference().child(Tools.patients).orderByChild("doctor")
                .startAt(user.getUid()).endAt(user.getUid() +"\uf8ff");
        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                clientes.clear();
                if (!dataSnapshot.exists()){
                    Cliente c = new Cliente();
                    c.setId("No clients");
                    clientes.add(c);

                }else{
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Cliente ci = d.getValue(Cliente.class);
                        ci.setId(d.getKey());
                        clientes.add(ci);
                    }
                }

                //Spinner areaSpinner = (Spinner) findViewById(R.id.spinner);
                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(context,clientes);
                spinner.setAdapter(spinnerAdapter);
            }


            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });
    }
}
