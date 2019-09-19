package com.intacta.doctoring.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.intacta.doctoring.R;
import com.intacta.doctoring.beans.Agenda;
import com.intacta.doctoring.beans.Compromisso;
import com.intacta.doctoring.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.MyViewHolder>{
    private List<Agenda> mListCompromissos;
     private Activity activity;

    public AgendaAdapter(Activity activity, List<Agenda> compromissos){
        this.mListCompromissos = compromissos;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("LOG","onCreateViewHolder()");
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View v = layoutInflater.inflate(R.layout.card_compromisso,parent,false);
         return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ArrayList<Compromisso> compromissos = new ArrayList<>();
        Log.i("LOG","onBindViewHolder()");
        Agenda a = mListCompromissos.get(position);
        holder.txtday.setText(mListCompromissos.get(position).getData());
        DatabaseReference compromissoreference = FirebaseDatabase.getInstance().getReference(Tools.agenda).child(a.getId());
        compromissoreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot d: dataSnapshot.getChildren()) {
                        Compromisso c = d.getValue(Compromisso.class);
                        compromissos.add(c);
                    }

                    CompromisesAdapter compromisesAdapter = new CompromisesAdapter(activity,compromissos);
                    holder.compromissosrecycler.setAdapter(compromisesAdapter);
                    GridLayoutManager llm = new GridLayoutManager(activity,1,RecyclerView.VERTICAL,false);
                    holder.compromissosrecycler.setLayoutManager(llm);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
     }






    @Override
    public int getItemCount() {
        return mListCompromissos.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView txtday;
        RecyclerView compromissosrecycler;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtday = itemView.findViewById(R.id.txt_day);
            compromissosrecycler = itemView.findViewById(R.id.compromisserecycler);
         }


    }
}
