package com.example.hp.appelectr.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hp.appelectr.Bluetooth.ManagementBluetooth;
import com.example.hp.appelectr.R;


public class Temperatura_Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTemHabUno, tvTemHabDos, tvTemHabTres, tvTemHabCuatro;
    private Button btnLuzUno, btnLuzDos, btnLuzTres, btnLuzCuatro;
    private ManagementBluetooth managementBluetoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatura);
        init();
    }

    public void init(){
        managementBluetoot = new ManagementBluetooth(this,Temperatura_Activity.this);
        managementBluetoot.verificarEstadoBT();
        tvTemHabUno = findViewById(R.id.tvHabUno);
        tvTemHabDos = findViewById(R.id.tvHabDos);
        tvTemHabTres = findViewById(R.id.tvHabTres);
        tvTemHabCuatro = findViewById(R.id.tvHabCuatro);
        btnLuzUno = findViewById(R.id.btnLuzUno);
        btnLuzDos = findViewById(R.id.btnLuzDos);
        btnLuzTres = findViewById(R.id.btnLuzTres);
        btnLuzCuatro = findViewById(R.id.btnLuzCuatro);
        btnLuzUno.setOnClickListener(this);
        btnLuzDos.setOnClickListener(this);
        btnLuzTres.setOnClickListener(this);
        btnLuzCuatro.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int vista = view.getId();
        switch (vista){
            case R.id.btnLuzUno:{
                managementBluetoot.myConexionBT.write("1");
                break;
            }
            case R.id.btnLuzDos:{
                managementBluetoot.myConexionBT.write("2");
                break;
            }
            case R.id.btnLuzTres:{
                managementBluetoot.myConexionBT.write("A");
                break;
            }
            case R.id.btnLuzCuatro:{
                break;
            }

        }
    }

    @Override
    public  void onResume(){
        super.onResume();
        managementBluetoot.resume();
    }


    @Override
    public void onPause(){
        super.onPause();
        managementBluetoot.pause();
    }
}
