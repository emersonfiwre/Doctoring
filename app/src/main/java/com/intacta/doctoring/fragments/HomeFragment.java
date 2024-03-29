package com.intacta.doctoring.fragments;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.intacta.doctoring.R;
import com.intacta.doctoring.adapters.AgendaAdapter;
import com.intacta.doctoring.beans.Agenda;
import com.intacta.doctoring.utils.Alerts;
import com.intacta.doctoring.utils.Tools;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private AppBarLayout appbar;
    private Toolbar toolbar;
    private TabLayout tabs;
     private RecyclerView compromisserecycler;
    private TextView addcompromisse;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View v) {
        compromisserecycler = v.findViewById(R.id.compromisserecycler);
        Carregar();
        addcompromisse = (TextView) v.findViewById(R.id.addcompromisse);
        addcompromisse.setVisibility(View.GONE);
        addcompromisse.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View v) {
                Alerts alerts = new Alerts(getActivity());
                alerts.CompromissoAlert();
            }
        });

    }


    private void Carregar() {
        final ArrayList<Agenda> compromissos = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final Query compromisses = FirebaseDatabase.getInstance()
                .getReference(Tools.user).child(user.getUid()).child(Tools.agenda);
        compromisses.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                compromissos.clear();
                compromisserecycler.removeAllViews();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Agenda a = d.getValue(Agenda.class);
                        a.setId(d.getKey());
                        compromissos.add(a);

                    }
                    Log.println(Log.INFO, "Agenda", String.format("there are %d agendas", compromissos.size()));
                    AgendaAdapter agendaAdapter = new AgendaAdapter(getActivity(), compromissos);
                    compromisserecycler.setAdapter(agendaAdapter);
                    GridLayoutManager llm = new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false);
                    compromisserecycler.setAdapter(agendaAdapter);
                    compromisserecycler.setLayoutManager(llm);
                } else {
                    Log.println(Log.INFO, "Agenda", "NO AGENDAS FOUND ");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void addTab(String title) {
        tabs.addTab(tabs.newTab().setText(title));

    }
}
