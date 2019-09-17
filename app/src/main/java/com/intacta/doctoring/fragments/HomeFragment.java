package com.intacta.doctoring.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.intacta.doctoring.R;

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
        appbar = (AppBarLayout) v.findViewById(R.id.appbar);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        tabs = (TabLayout) v.findViewById(R.id.tabs);
        compromissespager = (ViewPager) v.findViewById(R.id.compromissespager);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        toolbar.setTitle("Olá " + user.getDisplayName());
        toolbar.setSubtitle("Nenhuma consulta para hoje");

    }
}
