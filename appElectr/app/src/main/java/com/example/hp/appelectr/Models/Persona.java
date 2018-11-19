package com.example.hp.appelectr.Models;

import android.support.v4.app.Fragment;

import com.example.hp.appelectr.Listable;
import com.example.hp.appelectr.NodoFirebase;
import com.google.firebase.database.DataSnapshot;

import java.util.List;
import java.util.Map;

public class Persona extends NodoFirebase {

    private String nombre;
    private String born;
    private String keyHouse;
    private long promHoraEntrada;
    private List<Long> fechaEntradConHora;

    public Persona(String nombre, String born, String keyHouse, long promHoraEntrada, List<Long> fechaEntradConHora) {
        this.nombre = nombre;
        this.born = born;
        this.keyHouse = keyHouse;
        this.promHoraEntrada = promHoraEntrada;
        this.fechaEntradConHora = fechaEntradConHora;
    }

    public Persona() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getKeyHouse() {
        return keyHouse;
    }

    public void setKeyHouse(String keyHouse) {
        this.keyHouse = keyHouse;
    }

    public long getPromHoraEntrada() {
        return promHoraEntrada;
    }

    public void setPromHoraEntrada(long promHoraEntrada) {
        this.promHoraEntrada = promHoraEntrada;
    }

    public List<Long> getFechaEntradConHora() {
        return fechaEntradConHora;
    }

    public void setFechaEntradConHora(List<Long> fechaEntradConHora) {
        this.fechaEntradConHora = fechaEntradConHora;
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
        return "Personas";
    }
}
