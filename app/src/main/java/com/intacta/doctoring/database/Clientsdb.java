package com.intacta.doctoring.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.intacta.doctoring.adapters.SpinnerAdapter;
import com.intacta.doctoring.beans.Cliente;
import com.intacta.doctoring.utils.Tools;

import java.util.List;

public class Clientsdb {
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Context context;
    public Clientsdb(Context context) {
        this.context = context;
    }

    public void saveclient(Cliente cliente, final ProgressDialog progressDialog){
        progressDialog.show();
        DatabaseReference clientdb = FirebaseDatabase.getInstance().getReference(Tools.user).child(user.getUid()).child(Tools.patients);
        clientdb.push().setValue(cliente).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.setMessage("Paciente adicionado com sucesso!");
                }else{
                    progressDialog.setMessage("Erro ao adicionar paciente " + task.getException().getMessage());
                }

                CountDownTimer timer = new CountDownTimer(1000,100) {
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

    public void loadCliente(final Spinner spinner, final List<Cliente> clientes ){
        Query databaseReference = FirebaseDatabase.getInstance().getReference(Tools.user).child(user.getUid()).child(Tools.patients);
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
