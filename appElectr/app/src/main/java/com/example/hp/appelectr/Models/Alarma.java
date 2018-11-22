package com.example.hp.appelectr.Models;

import android.support.v4.app.Fragment;

import com.example.hp.appelectr.Listable;
import com.example.hp.appelectr.NodoFirebase;
import com.google.firebase.database.DataSnapshot;

import java.util.List;
import java.util.Map;

public class Alarma extends NodoFirebase {

    private List<Long> fechaActivacionAlarma;
    private int estadoAlarma;

    public Alarma(List<Long>fechaActivacionAlarma, int estadoAlarma) {
        this.fechaActivacionAlarma = fechaActivacionAlarma;
        this.estadoAlarma = estadoAlarma;
    }

    public Alarma(){}

    public List<Long> getFechaActivacionAlarma() {
        return fechaActivacionAlarma;
    }

    public void setFechaActivacionAlarma(List<Long> fechaActivacionAlarma) {
        this.fechaActivacionAlarma = fechaActivacionAlarma;
    }

    /* public Map<Long, Long> getFechaActivacionAlarma() {
        return fechaActivacionAlarma;
    }

    public void setFechaActivacionAlarma(Map<Long, Long> fechaActivacionAlarma) {
        this.fechaActivacionAlarma = fechaActivacionAlarma;
    }*/

    public int getEstadoAlarma() {
        return estadoAlarma;
    }

    public void setEstadoAlarma(int estadoAlarma) {
        this.estadoAlarma = estadoAlarma;
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
        return "Alarmas";
    }
}
