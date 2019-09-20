package com.intacta.doctoring.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.intacta.doctoring.R;
import com.intacta.doctoring.beans.Agenda;
import com.intacta.doctoring.beans.Cliente;
import com.intacta.doctoring.beans.Compromisso;
import com.intacta.doctoring.database.Clientsdb;
import com.intacta.doctoring.utils.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CompromisesAdapter extends RecyclerView.Adapter<CompromisesAdapter.MyViewHolder> {


    private Activity activity;
    private ArrayList<Compromisso> compromissos;

    public CompromisesAdapter(Activity activity, ArrayList<Compromisso> compromissos) {
        this.activity = activity;
        this.compromissos = compromissos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(activity);
        view = mInflater.inflate(R.layout.schedule_card,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Compromisso compromisso = compromissos.get(holder.getAdapterPosition());
        holder.compromisse.setText(compromisso.getCompromisso());
        holder.time.setText(compromisso.getTime());
        holder.client.setText(compromisso.getCliente());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Tools.patients).child(compromisso.getCliente());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    DataSnapshot d = dataSnapshot;
                    Cliente c = d.getValue(Cliente.class);
                    holder.client.setText(c.getNome());
                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return compromissos.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView compromisse,time,client;
        MyViewHolder(View view) {
            super(view);

            compromisse = view.findViewById(R.id.compromisse);
            time = view.findViewById(R.id.time);
            client = view.findViewById(R.id.client);




        }
    }


}
