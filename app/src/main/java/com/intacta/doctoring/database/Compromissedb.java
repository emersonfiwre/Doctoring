package com.intacta.doctoring.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.intacta.doctoring.beans.Compromisso;
import com.intacta.doctoring.utils.Tools;

public class Compromissedb {

    public Compromissedb() {
    }

    public void Done(Compromisso compromisso, String id){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Tools.agenda).child(id);
        reference.child(Tools.compromises).child(compromisso.getId()).setValue(compromisso);
    }

    public void Delete(Compromisso compromisso ,String id){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Tools.agenda).child(id);
        reference.child(Tools.compromises).child(compromisso.getId()).removeValue();
    }
}
