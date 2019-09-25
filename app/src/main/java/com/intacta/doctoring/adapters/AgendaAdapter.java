package com.intacta.doctoring.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.intacta.doctoring.R;
import com.intacta.doctoring.beans.Agenda;
import com.intacta.doctoring.beans.Compromisso;
import com.intacta.doctoring.utils.Tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.MyViewHolder> {
    private List<Agenda> mListCompromissos;
     private Activity activity;

    public AgendaAdapter(Activity activity, List<Agenda> compromissos){
        this.mListCompromissos = compromissos;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View v = layoutInflater.inflate(R.layout.agenda_layout,parent,false);
         return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ArrayList<Compromisso> compromissos = new ArrayList<>();
        final Agenda a = mListCompromissos.get(holder.getAdapterPosition());
        Log.println(Log.INFO,"agenda","data marcada " +  a.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d 'de' MMMM 'de' yyyy");
        String dia = sdf.format(Tools.parseDate(a.getId()));
        StringBuilder sb = new StringBuilder(dia);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        holder.txtday.setText(sb);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference compromissoreference = FirebaseDatabase.getInstance().getReference(Tools.user).child(user.getUid()).child(Tools.agenda).child(a.getId()).child(Tools.compromises);
        compromissoreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if (dataSnapshot.exists()){
                     compromissos.clear();
                     holder.compromissosrecycler.removeAllViews();
                    for (DataSnapshot d: dataSnapshot.getChildren()) {
                        Compromisso c = d.getValue(Compromisso.class);
                        c.setId(d.getKey());
                        compromissos.add(c);
                        Log.println(Log.INFO,"Compromisso",c.getCliente() + c.getCompromisso());
                    }

                    Log.println(    Log.INFO,"Compromissos","Compromissos loaded " + compromissos.size());
                    GridLayoutManager llm = new GridLayoutManager(activity,1,RecyclerView.VERTICAL,false);
                    CompromisesAdapter compromisesAdapter = new CompromisesAdapter(activity,compromissos);
                    holder.compromissosrecycler.setAdapter(compromisesAdapter);
                    holder.compromissosrecycler.setLayoutManager(llm);
                }else{
                    Log.println(    Log.INFO,"Compromissos","No compromisses here " + compromissos.size());

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
            compromissosrecycler = itemView.findViewById(R.id.schedulesrecycler);
         }


    }
}
