package com.intacta.doctoring;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.intacta.doctoring.adapters.ViewPagerAdapter;
import com.intacta.doctoring.utils.Alerts;
import java.util.Collections;
import java.util.List;
import static com.intacta.doctoring.utils.Tools.RC_SIGN_IN;

public class BottomNavActivity extends AppCompatActivity {
    private Activity activity = this;
    private BottomNavigationView navView;
    private FrameLayout frame;
    private FloatingActionButton floatbutton;

    private FirebaseDatabase firebaseDatabase;
    private TabLayout tabs;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_bottom_nav);
        initView();

        startFirebase();





    }

    private void startFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                dialog.setTitle("Desconectado!").setMessage("Você está desconectado, se não fizer login não será possível utilizar o aplicativo!(" + data.getDataString() + ")");
                dialog.setPositiveButton("Realizar login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buildlogin();
                    }
                });
                dialog.setNegativeButton("Cancelar", null);
                dialog.show();

            }
        }
    }




    private void initView() {
         login();
        AppBarLayout appbarlayout = findViewById(R.id.appbarlayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        tabs.getTabAt(0).setText("Home");
        tabs.getTabAt(1).setText("Clientes");
        tabs.getTabAt(2).setText("Notificações");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        toolbar.setTitle("Olá " + user.getDisplayName());

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
                .build(), RC_SIGN_IN);
    }

}
