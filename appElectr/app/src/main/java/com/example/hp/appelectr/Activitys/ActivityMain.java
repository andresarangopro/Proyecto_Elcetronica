package com.example.hp.appelectr.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.appelectr.Bluetooth.ManagementBluetooth;
import com.example.hp.appelectr.NodoFirebase;
import com.example.hp.appelectr.R;

import java.io.IOException;

import java.sql.Timestamp;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener {

    //1)
    private Button btnRefresHab, btnApagar, btnDesconectar,btnHabitacion;
    private TextView tvBufferIn;
    public ManagementBluetooth managementBluetooth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interfaz);
        init();
    }

    public void init(){
        managementBluetooth = new ManagementBluetooth(this,ActivityMain.this);
        managementBluetooth.verificarEstadoBT();

        btnRefresHab =  findViewById(R.id.btnRefreshHab);
        btnApagar =  findViewById(R.id.IdApagar);
        btnDesconectar =  findViewById(R.id.IdDesconectar);
        btnHabitacion = findViewById(R.id.btnHabitaciones);
        tvBufferIn =  findViewById(R.id.IdBufferIn);
        btnRefresHab.setOnClickListener(this);
        btnApagar.setOnClickListener(this);
        btnDesconectar.setOnClickListener(this);
        btnHabitacion.setOnClickListener(this);
    }



    public void recogniceActionFromTxt(String action, String data){
        switch(action){
            case "HABITACIONES":{
                //updateHabitacionesLigthTempFireB(data);
                break;
            }
            case "ALARMAS":{
                 addAlarma();
                break;
            }
            default:{
                break;
            }
        }
    }


    private void addAlarma() {
        Timestamp timestamp = new Timestamp((System.currentTimeMillis()/1000)-18000);
        NodoFirebase.getFirebaseDB()
                .child("casa_1")
                .child("Alarmas")
                .child("-"+timestamp.getTime())
                .setValue(timestamp.getTime());

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



    // ==================================================================
    // == indica que se realizará cuando se detecte el evento de Click
    //===================================================================

    @Override
    public void onClick(View view) {
        int vista = view.getId();
        switch (vista){
            case R.id.btnRefreshHab:{
                managementBluetooth.myConexionBT.write("1");
                break;
            }
            case R.id.IdApagar:{
                managementBluetooth.myConexionBT.write("0");
                break;
            }
            case R.id.btnHabitaciones:{
                  startActivity(new Intent(this, Temperatura_Activity.class));
                break;
            }
            case R.id.IdDesconectar:{
                if ( managementBluetooth.btSocket!=null)
                {
                    try { managementBluetooth.btSocket.close();}
                    catch (IOException e)
                    { Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();}
                }
                finish();
                break;
            }

        }
    }

}
