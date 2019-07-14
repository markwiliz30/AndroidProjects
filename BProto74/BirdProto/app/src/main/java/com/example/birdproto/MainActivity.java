package com.example.birdproto;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.birdproto.common.DeviceProtocol;
import com.example.birdproto.common.Protocol;
import com.example.birdproto.programitem.PgmAdapter;
import com.example.birdproto.sqldatabase.DatabaseHelper;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLocker {
    public DrawerLayout drawer;
    private boolean isBackDisabled = false;
    //DatabaseHelper blDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //blDb = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProgramFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TestFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_test);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //deviceProtocol.startChannel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //deviceProtocol.startChannel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //deviceProtocol.stopChannel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //deviceProtocol.stopChannel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_test:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TestFragment()).commit();
                break;
            case R.id.nav_program:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProgramFragment()).commit();
                break;
            case R.id.nav_schedule:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScheduleFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void setDrawerEnabled(boolean enabled) {
        if(!enabled){
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }
}
