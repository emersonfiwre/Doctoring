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
import com.intacta.doctoring.utils.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
            try {
                System.out.println("Client born " + cliente.getDataNascimento());
                Calendar now = Calendar.getInstance();
                SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyy");
                Date born = formatter.parse(cliente.getDataNascimento());
                Calendar borndate = Calendar.getInstance();
                borndate.setTime(born);

                System.out.println("born year " + borndate.get(Calendar.YEAR));
                System.out.println("actual year " + now.get(Calendar.YEAR));
                int age = now.get(Calendar.YEAR) - borndate.get(Calendar.YEAR);

                holder.age.setText(String.format("%s anos", String.valueOf(age)));

            } catch (ParseException e) {
                e.printStackTrace();
            }

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
