package com.example.hp.appelectr.Models;

import android.support.v4.app.Fragment;

import com.example.hp.appelectr.Listable;
import com.example.hp.appelectr.NodoFirebase;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

public class Habitacion extends NodoFirebase {

    private float temperatura;
    private Long lastTimeListenM;
    private int estadoLuz;

    public Habitacion(float temperatura, Long lastTimeListenM, int estadoLuz) {
        this.temperatura = temperatura;
        this.lastTimeListenM = lastTimeListenM;
        this.estadoLuz = estadoLuz;
    }

    public Habitacion() { }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public Long getLastTimeListenM() {
        return lastTimeListenM;
    }

    public void setLastTimeListenM(Long lastTimeListenM) {
        this.lastTimeListenM = lastTimeListenM;
    }

    public int getEstadoLuz() {
        return estadoLuz;
    }

    public void setEstadoLuz(int estadoLuz) {
        this.estadoLuz = estadoLuz;
    }

    @Override
    public void delete(String[] fatherKey) {

    }

    @Override
    public Listable getInstance(DataSnapshot dataSnapshot) {
        return null;
    }

    @Override
    public String[] print() {
        return new String[0];
    }

    @Override
    public Fragment getActivity() {
        return null;
    }

    @Override
    public List<Listable> filter(List<Listable> data, String filter) {
        return null;
    }

    @Override
    public String getFirebaseNodeName() {
       return "Habitaciones";
    }
}
