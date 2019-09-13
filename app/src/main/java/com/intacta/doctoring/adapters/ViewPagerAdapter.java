package com.intacta.doctoring.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.intacta.doctoring.R;
import com.intacta.doctoring.utils.Tools;
import com.intacta.doctoring.beans.Compromisso;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {


    private ArrayList<Compromisso> compromissos;
    private Activity activity;
    @Override
    public int getCount() {
        return compromissos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (layoutInflater != null) {
            view = layoutInflater.inflate(R.layout.pager, container, false);
        }
        assert view != null;
        RecyclerView recyclerView = view.findViewById(R.id.daylist);
        GetList(compromissos.get(position),recyclerView);

        container.addView(view);
        return view;
    }


    private void GetList(Compromisso compromisso,RecyclerView recyclerView){


        ValueEventListener listreference = FirebaseDatabase.getInstance().getReference(Tools.compromisses)
                .child(compromisso.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
