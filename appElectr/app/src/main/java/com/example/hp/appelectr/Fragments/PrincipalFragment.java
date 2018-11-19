package com.example.hp.appelectr.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.appelectr.Activitys.NavigationActivity;
import com.example.hp.appelectr.Activitys.Temperatura_Activity;
import com.example.hp.appelectr.FirebaseDAO;
import com.example.hp.appelectr.R;

import java.io.IOException;

import static com.example.hp.appelectr.Activitys.NavigationActivity.managementBluetooth;
import static com.example.hp.appelectr.Activitys.NavigationActivity.inTemperatureF;
/**
 * A simple {@link Fragment} subclass.
 */
public class PrincipalFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ImageButton btnRefresHab, btnDesconectar, btnHabitacion,btnAlarms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.principal_fragment, container, false);
        NavigationActivity.toolbar.setTitle("Inicio");
        init();

        return view;
    }

    public void init(){
        inTemperatureF = false;
        btnRefresHab =  view.findViewById(R.id.btnRefreshHab);
        btnDesconectar = view.findViewById(R.id.IdDesconectar);
        btnHabitacion = view.findViewById(R.id.btnHabitaciones);
        btnAlarms = view.findViewById(R.id.btnAlarms);
        btnAlarms.setOnClickListener(this);
        btnRefresHab.setOnClickListener(this);
        btnDesconectar.setOnClickListener(this);
        btnHabitacion.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int vista = view.getId();
        switch (vista){
            case R.id.btnRefreshHab:{
                managementBluetooth.myConexionBT.write("1");
                break;
            }
            case R.id.btnAlarms:{
                AlarmsFragment alarmsFragment = new AlarmsFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrFragment,alarmsFragment,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case R.id.btnHabitaciones:{
                managementBluetooth.myConexionBT.write("2");
                TemperatureFragment temperatureFragment= new TemperatureFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrFragment,temperatureFragment,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case R.id.IdDesconectar:{
                if (managementBluetooth.btSocket!=null)
                {
                    try { managementBluetooth.btSocket.close();
                        Toast.makeText(view.getContext(), "Desconectado", Toast.LENGTH_SHORT).show();}
                    catch (IOException e)
                    { Toast.makeText(view.getContext(), "Error", Toast.LENGTH_SHORT).show();}
                }
                break;
            }

        }
    }


}
