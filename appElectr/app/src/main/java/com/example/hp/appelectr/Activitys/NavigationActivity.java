package com.example.hp.appelectr.Activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.hp.appelectr.Bluetooth.ManagementBluetooth;
import com.example.hp.appelectr.Fragments.AlarmsFragment;
import com.example.hp.appelectr.Fragments.PersonasFragment;
import com.example.hp.appelectr.Fragments.PrincipalFragment;
import com.example.hp.appelectr.Fragments.TemperatureFragment;
import com.example.hp.appelectr.R;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Toolbar toolbar;
    public static ManagementBluetooth managementBluetooth;
    public static boolean inTemperatureF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Inicio");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        managementBluetooth = new ManagementBluetooth(this,NavigationActivity.this);
        managementBluetooth.verificarEstadoBT();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        FrameLayout fl = findViewById(R.id.FrFragment);
        fl.removeAllViews();
        manager.popBackStack();
        getFragmentManager().popBackStack();
        Fragment principalfragment = new PrincipalFragment();
        transaction.replace(R.id.FrFragment, principalfragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
             managementBluetooth.myConexionBT.write("A");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        // Handle navigation view item clicks here.
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        FrameLayout fl = findViewById(R.id.FrFragment);
        fl.removeAllViews();
        manager.popBackStack();
        getFragmentManager().popBackStack();
        Fragment fragment = null;

        int id = item.getItemId();
        switch (id){
            case R.id.nav_main:{
                fragment = new PrincipalFragment();
                break;
            }
            case R.id.nav_persons:{
                fragment = new PersonasFragment();
                break;
            }
            case R.id.nav_alarms:{
                fragment = new AlarmsFragment();
                break;
            }
            case R.id.nav_habitaciones:{
                fragment = new TemperatureFragment();
                break;
            }
            case R.id.nav_logout:{

                break;
            }

        }
        if(fragment != null) {
            transaction.replace(R.id.FrFragment, fragment);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public  void onResume(){
        super.onResume();
        managementBluetooth.resume();
    }


    @Override
    public void onPause(){
        super.onPause();
        managementBluetooth.pause();
    }



}
