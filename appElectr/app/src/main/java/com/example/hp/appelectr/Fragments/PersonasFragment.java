package com.example.hp.appelectr.Fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.appelectr.Activitys.NavigationActivity;
import com.example.hp.appelectr.DAOS.AlarmaDAO;
import com.example.hp.appelectr.DAOS.PersonaDAO;
import com.example.hp.appelectr.FirebaseDAO;
import com.example.hp.appelectr.Models.Alarma;
import com.example.hp.appelectr.Models.Persona;
import com.example.hp.appelectr.R;
import com.example.hp.appelectr.Recyclers.RAAlarmas;
import com.example.hp.appelectr.Recyclers.RAPersonas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonasFragment extends Fragment {

    private View view;
    private PersonaDAO personaDAO;
    private FirebaseDAO firebaseDAO;
    private Resources r;
    public List<Long> listTimes = new ArrayList();
    private List<Persona> listPersonas = new ArrayList();
    private RecyclerView mRecyclerDates;
    private RAPersonas mPersonas;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_personas, container, false);
        NavigationActivity.toolbar.setTitle("Personas");
        init();
        getNodes(new Persona());
        return view;
    }

    private void init(){
        firebaseDAO = new FirebaseDAO();
        personaDAO = new PersonaDAO();
    }

    private void setRecycler() throws IOException {
        r = getResources();
        mLinearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerDates = view.findViewById(R.id.rv_personas_in_fragmrnt) ;
        mRecyclerDates.setHasFixedSize(true);
        mRecyclerDates.setLayoutManager(mLinearLayoutManager);
        mPersonas = new RAPersonas(view.getContext(),listPersonas);
        mRecyclerDates.setAdapter(mPersonas);

    }


    public void getNodes(final Persona persona){
        listTimes = new ArrayList<>();
        //final FirebaseUser usa = firebaseDAO.firebaseAuth.getCurrentUser();
        DatabaseReference dbReference= firebaseDAO.getDatabaseReference().child("casa_1").child(persona.getFirebaseNodeName());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listTimes = new ArrayList<>();
                int estadoAlarma = 0;
                Persona persona;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    List<Long> listaFechas = new ArrayList<>();
                    Log.d("TAGPRS", snapshot.getKey() + "");
                    persona = new Persona();
                    for (DataSnapshot contentPersonas : snapshot.getChildren()) {
                        Log.d("TAGPRS", contentPersonas.getKey() + "");
                        String key = contentPersonas.getKey().toString();
                        if (key.equals("born")) {
                            persona.setBorn(contentPersonas.getValue().toString());
                        } else if (key.equals("key")) {
                            persona.setKeyHouse(contentPersonas.getValue().toString());
                        } else if (key.equals("nombre")) {
                            persona.setNombre(contentPersonas.getValue().toString());
                        } else if (key.equals("prom")) {
                            persona.setPromHoraEntrada(contentPersonas.getValue().toString());
                        } else {
                            String partOfLong = (contentPersonas.getValue().toString());
                            partOfLong = partOfLong.substring(partOfLong.indexOf("{") + 1, partOfLong.length() - 1);
                            String[] part = partOfLong.split(",");
                            for (String str : part) {
                                str = str.substring(str.indexOf("=") + 1, str.length());
                                long time = Long.parseLong(str);
                                listaFechas.add(time);
                                Log.d("TAGTPERS", time + "");
                            }
                            persona.setFechaEntradConHora(listaFechas);
                        }
                    }
                    listPersonas.add(persona);
                }
                try {
                    setRecycler();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        dbReference.addListenerForSingleValueEvent(valueEventListener);

    }


}
