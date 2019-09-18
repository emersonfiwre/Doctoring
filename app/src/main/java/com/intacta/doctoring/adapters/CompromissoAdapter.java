package com.intacta.doctoring.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intacta.doctoring.R;
import com.intacta.doctoring.beans.Compromisso;
import com.intacta.doctoring.interfaces.RecyclerViewOnClickListenerHack;

import java.util.List;

public class CompromissoAdapter extends RecyclerView.Adapter<CompromissoAdapter.MyViewHolder>{
    private List<Compromisso> mListCompromissos;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;


    public CompromissoAdapter(Context context, List<Compromisso> compromissos){
        this.mListCompromissos = compromissos;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("LOG","onCreateViewHolder()");
        View v = layoutInflater.inflate(R.layout.card_compromisso,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.i("LOG","onBindViewHolder()");
        holder.txtCliente.setText(mListCompromissos.get(position).getCliente());
        holder.txtServico.setText(mListCompromissos.get(position).getCompromisso());
        holder.txtTime.setText(mListCompromissos.get(position).getTime());
    }


    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    public void addListItem(Compromisso c, int position){
        mListCompromissos.add(position, c);
        notifyItemInserted(position);
    }


    public void removeListItem(int position){
        mListCompromissos.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public int getItemCount() {
        return mListCompromissos.size();
    }

    public  class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtCliente;
        public TextView txtServico;
        public TextView txtTime;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCliente = itemView.findViewById(R.id.txt_cliente);
            txtServico = itemView.findViewById(R.id.txt_service);
            txtTime = itemView.findViewById(R.id.txt_time);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if( mRecyclerViewOnClickListenerHack != null ) {
                mRecyclerViewOnClickListenerHack.onClickListener(v,getAdapterPosition());
            }
        }
    }
}
