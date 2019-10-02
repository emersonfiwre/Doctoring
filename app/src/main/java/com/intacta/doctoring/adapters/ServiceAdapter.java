package com.intacta.doctoring.adapters;

import android.app.Activity;
import android.graphics.Color;
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

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {
    private List<String> servicelist;
     private Activity activity;

    public ServiceAdapter(Activity activity, List<String> servicelist){
        this.servicelist = servicelist;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View v = layoutInflater.inflate(R.layout.services_adapter,parent,false);
         return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.name.setText(servicelist.get(position));
        if (position == 0){
            holder.name.setTextColor(Color.WHITE);
            holder.name.setBackgroundResource(R.drawable.ripple_button);
        }else{
            holder.name.setTextColor(Color.WHITE);
            holder.name.setBackgroundColor(activity.getColor(R.color.md_blue_300));
        }
     }



    @Override
    public int getItemCount() {
        return servicelist.size();
    }


    class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView name;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name= itemView.findViewById(R.id.name);
          }


    }
}
