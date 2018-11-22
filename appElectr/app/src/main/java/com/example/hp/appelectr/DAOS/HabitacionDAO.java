package com.example.hp.appelectr.DAOS;

import com.example.hp.appelectr.FirebaseDAO;
import com.example.hp.appelectr.NodoFirebase;

import java.sql.Timestamp;

public class HabitacionDAO {
    private FirebaseDAO firebaseDAO = new FirebaseDAO();

    public void updateHabitacionesLigthTempFireB(String data) throws Exception {
        String[] listData = data.split(",");
        updateHabitacionesTempFireB(listData[0],listData[1],Float.parseFloat(listData[2]));
        updateHabitacionesEstadoLFireB(listData[0],listData[3],Integer.parseInt(listData[4]));
    }
    public void updateHabitacionesPersonaTempFireB(String data) throws Exception {
        Timestamp timestamp = new Timestamp((System.currentTimeMillis()/1000)-18000);
        String[] listData = data.split(",");
        updateHabitacionesPersonaFireB(listData[0],listData[1],timestamp.getTime());
        updateHabitacionesTempFireB(listData[0],listData[2],Float.parseFloat(listData[3]));
    }

    public void updateOnlyLigthFireB(String data){
        String[] listData = data.split(",");
        updateHabitacionesEstadoLFireB(listData[0],listData[1],Integer.parseInt(listData[2]));
    }

    public void updateOnlyTempFireB(String data) throws Exception {
            String[] listData = data.split(",");
            updateHabitacionesTempFireB(listData[0],listData[1], Float.parseFloat(listData[2]));
        }

    public void updateOnlyPersonaHabFireB(String data){
        Timestamp timestamp = new Timestamp((System.currentTimeMillis()/1000)-18000);
        String[] listData = data.split(",");
        updateHabitacionesPersonaFireB(listData[0],listData[1],timestamp.getTime());
    }

    public void updateHabitacionesTempFireB(String habitacion, String clave, float valor ) throws Exception{
        firebaseDAO.getDatabaseReference()
                .child("casa_1")
                .child("Habitaciones")
                .child(habitacion)
                .child(clave)
                .setValue(valor);
    }

    public void updateHabitacionesEstadoLFireB(String habitacion, String clave, int valor ){
        firebaseDAO.getDatabaseReference()
                .child("casa_1")
                .child("Habitaciones")
                .child(habitacion)
                .child(clave)
                .setValue(valor);
    }

    public void updateHabitacionesPersonaFireB(String habitacion, String clave, Long valor ){
        firebaseDAO.getDatabaseReference()
                .child("casa_1")
                .child("Habitaciones")
                .child(habitacion)
                .child(clave)
                .setValue(valor);
    }
}
