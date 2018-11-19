package com.example.hp.appelectr.DAOS;

import com.example.hp.appelectr.FirebaseDAO;

import java.sql.Timestamp;

public class PersonaDAO {
    private FirebaseDAO firebaseDAO = new FirebaseDAO();

    public void updatePersonaHFireB(String data){
        Timestamp timestamp = new Timestamp((System.currentTimeMillis()/1000)-18000);
        String[] listData = data.split("!");
        updatePersonaHomeFireB(listData[0],listData[1],timestamp.getTime());
    }

    public void updatePersonaHomeFireB(String key, String clave, Long valor ){
        firebaseDAO.getDatabaseReference()
                .child("casa_1")
                .child("Personas")
                .child(key)
                .child(clave)
                .child("-"+valor)
                .setValue(valor);
    }
}
