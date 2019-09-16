package com.intacta.doctoring.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.intacta.doctoring.R;
import com.intacta.doctoring.beans.Cliente;
import com.intacta.doctoring.utils.Tools;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientFragment extends Fragment {


    private Toolbar toolbar;
    private RecyclerView clients;

    public ClientFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        clients = (RecyclerView) view.findViewById(R.id.clients);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

    }


    private void Carregar(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        clientes.clear();
        DatabaseReference patientref = (DatabaseReference) FirebaseDatabase.getInstance().getReference(Tools.patients)
                .equalTo("key","doctor").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()){
                            Cliente c = new Cliente();
                            c.setId("No clients");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
