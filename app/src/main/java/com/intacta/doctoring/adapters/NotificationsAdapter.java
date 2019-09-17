package com.intacta.doctoring.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intacta.doctoring.R;
import com.intacta.doctoring.beans.Notification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.MyViewHolder> {


    private Activity activity;
    private ArrayList<Notification> notifications;

    public NotificationsAdapter(Activity activity, ArrayList<Notification> notifications) {
        this.activity = activity;
        this.notifications = notifications;
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
        Notification notification = notifications.get(holder.getAdapterPosition());
        if (!notification.getMessage().equals("Você não possui notificações")) {
            holder.name.setText(notification.getMessage());
            holder.email.setVisibility(View.GONE);
            try {
                System.out.println("notification sended " + notification.getData());
                Calendar now = Calendar.getInstance();
                SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyy");
                Date born = formatter.parse(notification.getData());
                Calendar borndate = Calendar.getInstance();
                borndate.setTime(born);

                System.out.println("born year " + borndate.get(Calendar.YEAR));
                System.out.println("actual year " + now.get(Calendar.YEAR));
                int days = now.get(Calendar.YEAR) - borndate.get(Calendar.YEAR);

                holder.age.setText(String.format("%s anos", String.valueOf(days)));

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else{
            holder.name.setText("Você não possui nenhuma notificação...");
        }
    }

    @Override
    public int getItemCount() {
        return notifications.size();
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
