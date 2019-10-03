package com.intacta.doctoring.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intacta.doctoring.R;
import com.intacta.doctoring.utils.Alerts;

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
        View v = layoutInflater.inflate(R.layout.services_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.name.setText(servicelist.get(position));
        switch (position) {
            case 0: holder.back.setBackgroundResource(R.drawable.gradient);
                break;
            case 1: holder.back.setBackgroundResource(R.drawable.sgradient);
                break;
            case 2: holder.back.setBackgroundResource(R.drawable.tgradient);
            break;
            default: holder.back.setBackgroundResource(R.drawable.egradient);
            holder.name.setTextColor(Color.BLACK);

        }

        holder.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alerts a = new Alerts(activity);
                a.selectspecialitie(servicelist.get(position));
            }
        });

    }



    @Override
    public int getItemCount() {
        return servicelist.size();
    }


    class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView name;
        LinearLayout back;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name= itemView.findViewById(R.id.name);
            back= itemView.findViewById(R.id.background);
        }


    }
}
