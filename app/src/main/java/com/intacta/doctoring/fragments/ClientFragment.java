package com.intacta.doctoring.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.intacta.doctoring.R;
import com.intacta.doctoring.adapters.ClientsAdapter;
import com.intacta.doctoring.beans.Cliente;
import com.intacta.doctoring.database.Clientsdb;
import com.intacta.doctoring.interfaces.RecyclerViewOnClickListenerHack;
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
         Carregar();

    }


    private void Carregar(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final ArrayList<Cliente> clientes = new ArrayList<>();
        clients.removeAllViews();
        Query databasereference = FirebaseDatabase.getInstance().getReference().child(Tools.patients).orderByChild("doctor")
                .startAt(user.getUid()).endAt(user.getUid() +"\uf8ff");
        databasereference.keepSynced(true);

        databasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clientes.clear();
                if (!dataSnapshot.exists()){
                    Cliente c = new Cliente();
                    c.setId("No clients");
                    clientes.add(c);
                }else{
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Cliente ci = d.getValue(Cliente.class);
                        ci.setId(d.getKey());
                        //System.out.println(diary.getData() + diary.getFiscal() + diary.getLocal());
                        clientes.add(ci);
                    }
                }
                System.out.println("There are " +clientes.size());
                ClientsAdapter clientsAdapter = new ClientsAdapter(getActivity(),clientes);
                GridLayoutManager llm = new GridLayoutManager(getActivity(),1,RecyclerView.VERTICAL,false);
                clients.setAdapter(clientsAdapter);
                clients.setLayoutManager(llm);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
