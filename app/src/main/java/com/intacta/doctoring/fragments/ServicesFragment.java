package com.intacta.doctoring.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.intacta.doctoring.R;
import com.intacta.doctoring.adapters.ServiceAdapter;
import com.intacta.doctoring.utils.Alerts;
import com.intacta.doctoring.utils.Tools;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServicesFragment extends Fragment {


    private TextView addcompromisse;
    private RecyclerView compromisserecycler;

    public ServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        addcompromisse = v.findViewById(R.id.addcompromisse);
        addcompromisse.setText("Adicionar serviços");
        addcompromisse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alerts a = new Alerts(getActivity());
                a.AddService();
            }
        });
        carregar();
        compromisserecycler = v.findViewById(R.id.compromisserecycler);
    }


    private void carregar(){
        DatabaseReference reference = Tools.services;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<String> services = new ArrayList<>();
                for (DataSnapshot d:dataSnapshot.getChildren()) {
                    services.add(d.getKey());
                }
                GridLayoutManager llm = new GridLayoutManager(getActivity(), 2,RecyclerView.VERTICAL,false);
                llm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (position == 1) return 1;
                        else return 2;

                    }
                });
                compromisserecycler.setAdapter(new ServiceAdapter(getActivity(),services));
                compromisserecycler.setLayoutManager(llm);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
