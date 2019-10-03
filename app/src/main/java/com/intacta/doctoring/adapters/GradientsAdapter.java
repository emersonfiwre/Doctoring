package com.intacta.doctoring.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.intacta.doctoring.R;
import com.wajahatkarim3.easymoneywidgets.EasyMoneyTextView;

public class GradientsAdapter extends RecyclerView.Adapter<GradientsAdapter.MyViewHolder> {
      private Activity activity;
      private EditText sname;

    public GradientsAdapter(Activity activity, EditText sname) {
        this.activity = activity;
        this.sname = sname;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View v = layoutInflater.inflate(R.layout.gradient_card,parent,false);
         return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        sname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                holder.service.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
         switch (position) {
             case 0: holder.back.setBackgroundResource(R.drawable.gradient);
             break;
             case 1: holder.back.setBackgroundResource(R.drawable.sgradient);
             break;
             case 2: holder.back.setBackgroundResource(R.drawable.tgradient);

         }




     }



    @Override
    public int getItemCount() {
        return 3;
    }


    class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView service;
        CardView card;
        LinearLayout back;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            service = (TextView) itemView.findViewById(R.id.service);
            card = itemView.findViewById(R.id.card);
            back = itemView.findViewById(R.id.background);
          }


    }
}
