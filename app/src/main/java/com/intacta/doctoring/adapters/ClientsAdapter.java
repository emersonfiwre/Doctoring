package com.intacta.doctoring.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intacta.doctoring.R;
import com.intacta.doctoring.beans.Cliente;

import java.util.ArrayList;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.MyViewHolder> {


    private Activity activity;
    private ArrayList<Cliente> clientes;

    public ClientsAdapter(Activity activity, ArrayList<Cliente> clientes) {
        this.activity = activity;
        this.clientes = clientes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(activity);
        view = mInflater.inflate(R.layout.client_card,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cliente cliente = clientes.get(holder.getAdapterPosition());
        if (!cliente.getId().equals("No clients")) {
            holder.name.setText(cliente.getNome());
            holder.email.setText(cliente.getEmail());
        }else{
            holder.name.setText("Você não possui pacientes no momento...");
        }
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView name,email,age;
        public MyViewHolder(View view) {
            super(view);


            name = view.findViewById(R.id.name);
            email = view.findViewById(R.id.email);
            age = view.findViewById(R.id.age);




        }
    }


}
