package com.intacta.doctoring.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intacta.doctoring.R;
import com.intacta.doctoring.beans.Cliente;

import java.util.ArrayList;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.MyViewHolder> {


    private Activity activity;
    private ArrayList<Cliente> clientes;

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

    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {



        public MyViewHolder(View view) {
            super(view);





        }
    }


}
