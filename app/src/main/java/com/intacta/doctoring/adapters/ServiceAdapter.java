package com.intacta.doctoring.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intacta.doctoring.R;

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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.name.setText(servicelist.get(position));
        if (position == 0){
            holder.name.setTextColor(Color.WHITE);
            holder.name.setBackgroundResource(R.drawable.ripple_button);
        }else{
            holder.name.setTextColor(Color.WHITE);
            holder.name.setBackgroundColor(activity.getResources().getColor(R.color.colorAccent));
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
