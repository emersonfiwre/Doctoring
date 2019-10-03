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
import com.intacta.doctoring.beans.Specialitie;

import java.util.List;

public class SpecialitiesAdapter extends RecyclerView.Adapter<SpecialitiesAdapter.MyViewHolder> {
    private List<Specialitie> specialities;
     private Activity activity;

    public SpecialitiesAdapter(Activity activity, List<Specialitie> specialities){
        this.specialities = specialities;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View v = layoutInflater.inflate(R.layout.speciality_card,parent,false);
         return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Specialitie specialitie = specialities.get(holder.getAdapterPosition());
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
        return specialities.size();
    }


    class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView name;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name= itemView.findViewById(R.id.name);
          }


    }
}
