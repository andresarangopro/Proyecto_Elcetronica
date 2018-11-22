package com.example.hp.appelectr.DAOS;

import com.example.hp.appelectr.FirebaseDAO;
import com.example.hp.appelectr.NodoFirebase;

import java.sql.Timestamp;

public class AlarmaDAO {
    private FirebaseDAO firebaseDAO = new FirebaseDAO();

    public void addAlarmaToFireB() {
        Timestamp timestamp = new Timestamp((System.currentTimeMillis()/1000)-18000);
        firebaseDAO.getDatabaseReference()
                .child("casa_1")
                .child("Alarmas")
                .child("fechaActivacionAlarma")
                .child("-"+timestamp.getTime())
                .setValue(timestamp.getTime());

    }

    public void changeStateAlarm(int state){
        firebaseDAO.getDatabaseReference()
                .child("casa_1")
                .child("Alarmas")
                .child("estadoAlarma")
                .setValue(state);
    }
}
