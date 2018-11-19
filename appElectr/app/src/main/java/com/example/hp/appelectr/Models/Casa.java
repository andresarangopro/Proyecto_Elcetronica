package com.example.hp.appelectr.Models;

import android.support.v4.app.Fragment;

import com.example.hp.appelectr.Listable;
import com.example.hp.appelectr.NodoFirebase;
import com.google.firebase.database.DataSnapshot;

import java.util.List;
import java.util.Map;

public class Casa extends NodoFirebase {

    private int cantHabitaciones;
    private List<Habitacion> listHabitaciones;
    private List<Persona> listPersonas;
    private String direccion;
    private Map<Long, Long> listFechaActivacionAlarma;

    public Casa(int cantHabitaciones, List<Habitacion> listHabitaciones, List<Persona> listPersonas, String direccion, Map<Long, Long> listFechaActivacionAlarma) {
        this.cantHabitaciones = cantHabitaciones;
        this.listHabitaciones = listHabitaciones;
        this.listPersonas = listPersonas;
        this.direccion = direccion;
        this.listFechaActivacionAlarma = listFechaActivacionAlarma;
    }

    public Casa(){}

    public int getCantHabitaciones() {
        return cantHabitaciones;
    }

    public void setCantHabitaciones(int cantHabitaciones) {
        this.cantHabitaciones = cantHabitaciones;
    }

    public List<Habitacion> getListHabitaciones() {
        return listHabitaciones;
    }

    public void setListHabitaciones(List<Habitacion> listHabitaciones) {
        this.listHabitaciones = listHabitaciones;
    }

    public List<Persona> getListPersonas() {
        return listPersonas;
    }

    public void setListPersonas(List<Persona> listPersonas) {
        this.listPersonas = listPersonas;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Map<Long, Long> getListFechaActivacionAlarma() {
        return listFechaActivacionAlarma;
    }

    public void setListFechaActivacionAlarma(Map<Long, Long> listFechaActivacionAlarma) {
        this.listFechaActivacionAlarma = listFechaActivacionAlarma;
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
        return null;
    }
}
