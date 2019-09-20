package com.intacta.doctoring;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.intacta.doctoring.fragments.NotificationsFragment;
import com.intacta.doctoring.interfaces.RecyclerViewOnClickListenerHack;
import com.intacta.doctoring.utils.Alerts;
import com.intacta.doctoring.fragments.ClientFragment;
import com.intacta.doctoring.fragments.HomeFragment;

import java.util.Collections;
import java.util.List;


import static com.intacta.doctoring.utils.Tools.RC_SIGN_IN;

public class BottomNavActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener , RecyclerViewOnClickListenerHack {
    private Activity activity = this;
    private RelativeLayout container;
    private BottomNavigationView navView;
    private FrameLayout frame;
    private FloatingActionButton floatbutton;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_bottom_nav);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        initView();

        startFirebase();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, new HomeFragment())
                .commit();

        floatbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Alerts alerts = new Alerts(activity);
                alerts.CompromissoAlert();
            }
        });


    }

    private void startFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                floatbutton.setImageDrawable(getDrawable(R.drawable.ic_add_black_24dp));
                floatbutton.show();

                if (floatbutton.isOrWillBeShown()) {
                    floatbutton.setImageDrawable(getDrawable(R.drawable.ic_add_black_24dp));
                }
                floatbutton.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        Alerts alerts = new Alerts(activity);
                        alerts.CompromissoAlert();
                    }
                });
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, new HomeFragment())
                        .commit();


                return true;
            case R.id.navigation_dashboard:

                floatbutton.show();
                floatbutton.setImageDrawable(getDrawable(R.drawable.ic_person_add_black_24dp));
                if (floatbutton.isOrWillBeShown()) {
                    floatbutton.setImageDrawable(getDrawable(R.drawable.ic_person_add_black_24dp));
               }

                floatbutton.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
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

            case R.id.navigation_notifications:
                floatbutton.hide();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame,new NotificationsFragment())
                        .commit();
                return true;
            default: return false;

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if (resultCode != RESULT_OK){
                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                dialog.setTitle("Desconectado!").setMessage("Você está desconectado, se não fizer login não será possível utilizar o aplicativo!("+data.getDataString()+")");
                dialog.setPositiveButton("Realizar login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buildlogin();
                    }
                });
                dialog.setNegativeButton("Cancelar",null);
                dialog.show();

            }
        }
    }
    @Override
    public void onClickListener(View view, int position) {
        Toast.makeText(this, "onClickListener(): "+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongPressClickListener(View view, int position) {
        Toast.makeText(this, "onLongPressClickListener(): "+position, Toast.LENGTH_SHORT).show();
    }


    private void initView() {
        container = findViewById(R.id.container);
        navView = findViewById(R.id.nav_view);
        frame = findViewById(R.id.frame);
        floatbutton = findViewById(R.id.floatbutton);
        login();
    }

    public void login() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            buildlogin();

        }


    }

    protected void buildlogin() {
        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.GoogleBuilder().build());
        activity.startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setLogo(R.mipmap.ic_launcher)
                .setAvailableProviders(providers)
                .setTheme(R.style.AppTheme)
                .build(),RC_SIGN_IN);
    }

}
