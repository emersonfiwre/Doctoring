package com.intacta.doctoring.fragments;


import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
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
import com.intacta.doctoring.adapters.ViewPagerAdapter;
import com.intacta.doctoring.beans.Compromisso;
import com.intacta.doctoring.utils.Tools;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private AppBarLayout appbar;
    private Toolbar toolbar;
    private TabLayout tabs;
    private ViewPager compromissespager;

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
        appbar = v.findViewById(R.id.appbar);
        toolbar = v.findViewById(R.id.toolbar);
        tabs = v.findViewById(R.id.tabs);
        compromissespager = v.findViewById(R.id.compromissespager);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        toolbar.setTitle("Olá " + user.getDisplayName());
        toolbar.setSubtitle("Nenhuma consulta para hoje");
        Carregar();



    }


    private void Carregar(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final Query compromisses = FirebaseDatabase.getInstance()
                .getReference(Tools.compromises).orderByChild("doctor").equalTo(user.getUid());
        compromisses.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    ArrayList<Compromisso> compromissos = new ArrayList<>();

                    for (DataSnapshot d:dataSnapshot.getChildren()) {
                         Compromisso c = d.getValue(Compromisso.class);
                         c.setId(dataSnapshot.getKey());
                         compromissos.add(c);
                        Log.v("Compromisses", String.format("there are %d compromissos", compromissos.size()));
                    }

                    ViewPagerAdapter adapter = new ViewPagerAdapter(compromissos,getActivity());
                    tabs.setupWithViewPager(compromissespager);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
