package com.intacta.doctoring.adapters;

import android.app.Activity;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.intacta.doctoring.R;
import com.intacta.doctoring.beans.Cliente;
import com.intacta.doctoring.beans.Compromisso;
import com.intacta.doctoring.database.Compromissedb;
import com.intacta.doctoring.utils.Alerts;
import com.intacta.doctoring.utils.Tools;
import com.leondzn.simpleanalogclock.SimpleAnalogClock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CompromisesAdapter extends RecyclerView.Adapter<CompromisesAdapter.MyViewHolder> {


    private Activity activity;
    private ArrayList<Compromisso> compromissos;
    private String id;

    public CompromisesAdapter(Activity activity, ArrayList<Compromisso> compromissos, String id) {
        this.activity = activity;
        this.compromissos = compromissos;
        this.id = id;
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
        final Compromisso compromisso = compromissos.get(holder.getAdapterPosition());
        Log.println(Log.INFO,"Compromisso","Compromisso é " + compromisso.getCompromisso());
        holder.compromisse.setText(compromisso.getCompromisso());
        holder.time.setText(compromisso.getTime());
        Date time = Tools.parseTime(compromisso.getTime());
        Calendar rtime = Calendar.getInstance();
        rtime.setTime(time);
        StringBuilder times = new StringBuilder();
        times.append(rtime.get(Calendar.HOUR_OF_DAY)).append(":");
        if (rtime.get(Calendar.MINUTE) == 0){
            times.append("00");
        }else{
            times.append(rtime.get(Calendar.MINUTE));
        }
        holder.time.setText(times);
         holder.clock.setTime(rtime.get(Calendar.HOUR_OF_DAY),rtime.get(Calendar.MINUTE),0);
        if (compromisso.isDone()){
            holder.compromisse.setPaintFlags(holder.compromisse.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.done.setChecked(true);
        }
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

        holder.done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                compromisso.setDone(isChecked);
                Compromissedb compromissedb = new Compromissedb(activity);
                compromissedb.Done(compromisso,id);
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alerts alerts = new Alerts(activity);
                alerts.Options(compromisso);
            }
        });
        Animation in  = AnimationUtils.loadAnimation(activity,R.anim.fade_in);
        holder.layout.startAnimation(in);
    }

    @Override
    public int getItemCount() {
        return compromissos.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout layout;
        private TextView compromisse,time,client;
        private CheckBox done;
        private SimpleAnalogClock clock;
        MyViewHolder(View view) {
            super(view);
            done = view.findViewById(R.id.done);
            clock = view.findViewById(R.id.clock);
            compromisse = view.findViewById(R.id.compromisse);
            time = view.findViewById(R.id.time);
            client = view.findViewById(R.id.client);
            layout = view.findViewById(R.id.layout);




        }
    }


}
