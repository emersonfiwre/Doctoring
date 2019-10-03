package com.intacta.doctoring.database;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.intacta.doctoring.adapters.SpecialitiesAdapter;
import com.intacta.doctoring.beans.Specialitie;
import com.intacta.doctoring.utils.Tools;

import java.util.ArrayList;

public class Specialitiesdb {

    Activity activity;


    public Specialitiesdb(Activity activity) {
        this.activity = activity;
    }

    public void carregar(final RecyclerView items, final String service){
        DatabaseReference reference = Tools.services;
        reference.child(service).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Specialitie> specialities = new ArrayList<>();
                if (dataSnapshot.exists()){
                    for (DataSnapshot d:dataSnapshot.getChildren()) {
                       Specialitie s = d.getValue(Specialitie.class);
                       s.setKey(d.getKey());
                       specialities.add(s);
                    }
                    specialities.add(new Specialitie("add"));
                    SpecialitiesAdapter adapter = new SpecialitiesAdapter(specialities,activity,service);
                    items.setAdapter(adapter);
                    GridLayoutManager llm = new GridLayoutManager(activity,1 ,LinearLayoutManager.VERTICAL,false);
                    items.setLayoutManager(llm);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
