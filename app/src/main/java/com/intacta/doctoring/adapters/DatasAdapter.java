package com.intacta.doctoring.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intacta.doctoring.R;
import com.intacta.doctoring.beans.Compromisso;

import java.util.List;

public class DatasAdapter extends RecyclerView.Adapter<DatasAdapter.MyViewHolder>{
    private List<Compromisso> compromissos;
    private LayoutInflater layoutInflater;



    public DatasAdapter(Context context, List<Compromisso> compromissos){
        this.compromissos = compromissos;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("LOG","onCreateViewHolder()");
        View v = layoutInflater.inflate(R.layout.card_datas,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.i("LOG","onBindViewHolder()");
        holder.txtData.setText(compromissos.get(position).getData());
    }


    @Override
    public int getItemCount() {
        return compromissos.size();
    }

    public  class MyViewHolder extends  RecyclerView.ViewHolder {
        public TextView txtData;
        public ImageButton imgMore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtData =  itemView.findViewById(R.id.txt_data);
            imgMore =  itemView.findViewById(R.id.img_more);

        }
    }
}
