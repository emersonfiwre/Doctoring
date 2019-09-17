package com.intacta.doctoring.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.intacta.doctoring.R;
import com.intacta.doctoring.adapters.ClientsAdapter;
import com.intacta.doctoring.adapters.NotificationsAdapter;
import com.intacta.doctoring.beans.Cliente;
import com.intacta.doctoring.beans.Notification;
import com.intacta.doctoring.utils.Tools;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {


    private Toolbar toolbar;
    private RecyclerView notificationrecycler;

    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        toolbar = v.findViewById(R.id.toolbar);
        notificationrecycler = v.findViewById(R.id.notificationrecycler);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        Notifications();
    }

    private void Notifications(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final ArrayList<Notification> notifications = new ArrayList<>();
        notificationrecycler.removeAllViews();
        Query databasereference = FirebaseDatabase.getInstance().getReference().child(Tools.notifications).orderByChild("doctor")
                .startAt(user.getUid()).endAt(user.getUid() +"\uf8ff");
        databasereference.keepSynced(true);

        databasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notifications.clear();
                System.out.println("there are notifications? " + dataSnapshot.exists());
                if (!dataSnapshot.exists()){
                    Notification n = new Notification();
                    n.setMessage("Você não possui notificações");
                    notifications.add(n);
                }else{
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Notification nt = d.getValue(Notification.class);
                        nt.setKey(d.getKey());

                        //System.out.println(diary.getData() + diary.getFiscal() + diary.getLocal());
                        notifications.add(nt);
                    }
                }
                System.out.println("There are " +notifications.size());
               NotificationsAdapter notificationsAdapter = new NotificationsAdapter(getActivity(),notifications);
                GridLayoutManager llm = new GridLayoutManager(getActivity(),1,RecyclerView.VERTICAL,false);
                notificationrecycler.setAdapter(notificationsAdapter);
                notificationrecycler.setLayoutManager(llm);
                toolbar.setSubtitle(notifications.size() + " notificações");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
