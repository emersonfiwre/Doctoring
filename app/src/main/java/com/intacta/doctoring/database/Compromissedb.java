package com.intacta.doctoring.database;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.intacta.doctoring.beans.Agenda;
import com.intacta.doctoring.beans.Compromisso;
import com.intacta.doctoring.utils.Tools;

import java.util.Calendar;

public class Compromissedb {

    private Context context;
    public Compromissedb(Context context) {
        this.context = context;
    }

    public void sendCompromisso(final Compromisso compromisso, final Agenda agenda, final Calendar calendar){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference agendaref = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid()).child(Tools.agenda).child(Tools.formatday(calendar.getTime()));
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
                                    Toast.makeText(context,"Compromisso salvo com sucesso!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }else{
                    DatabaseReference compromissoref = FirebaseDatabase.getInstance().getReference(Tools.agenda);
                    compromissoref.child(Tools.formatday(calendar.getTime())).child(Tools.compromises).push().setValue(compromisso).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context,"Compromisso salvo com sucesso!",Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void Done(Compromisso compromisso, String id){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Tools.agenda).child(id);
        reference.child(Tools.compromises).child(compromisso.getId()).setValue(compromisso);
    }

    public void Delete(Compromisso compromisso ,String id){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Tools.agenda).child(id);
        reference.child(Tools.compromises).child(compromisso.getId()).removeValue();
    }
    public void Update(Compromisso compromisso ,String id){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Tools.agenda).child(id);
        reference.child(Tools.compromises).child(compromisso.getId()).setValue(compromisso);
    }
}
