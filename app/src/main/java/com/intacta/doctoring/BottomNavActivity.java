package com.intacta.doctoring;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.intacta.doctoring.Utils.Alerts;
import com.intacta.doctoring.fragments.ClientFragment;
import com.intacta.doctoring.fragments.HomeFragment;

public class BottomNavActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Activity activity = this;
    private RelativeLayout container;
    private BottomNavigationView navView;
    private FrameLayout frame;
    private FloatingActionButton floatbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        initView();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, new HomeFragment())
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                 floatbutton.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, new HomeFragment())
                        .commit();


                return true;
            case R.id.navigation_dashboard:
                 floatbutton.setImageDrawable(getResources().getDrawable(R.drawable.ic_person_add_black_24dp));
                floatbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Alerts alerts = new Alerts(activity);
                        alerts.ClientAlert();
                    }
                });

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, new ClientFragment())
                        .commit();
                return true;

            default: return false;

        }


    }

    private void initView() {
        container = findViewById(R.id.container);
        navView = findViewById(R.id.nav_view);
        frame = findViewById(R.id.frame);
        floatbutton = findViewById(R.id.floatbutton);
    }
}
