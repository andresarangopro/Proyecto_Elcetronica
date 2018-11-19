package com.example.hp.appelectr.Fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hp.appelectr.Activitys.NavigationActivity;
import com.example.hp.appelectr.DAOS.AlarmaDAO;
import com.example.hp.appelectr.FirebaseDAO;
import com.example.hp.appelectr.GridSpacingItemDecoration;
import com.example.hp.appelectr.Models.Alarma;
import com.example.hp.appelectr.Models.Habitacion;
import com.example.hp.appelectr.R;
import com.example.hp.appelectr.Recyclers.RAAlarmas;
import com.example.hp.appelectr.Recyclers.RAHabitaciones;
import com.example.hp.appelectr.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.hp.appelectr.Activitys.NavigationActivity.managementBluetooth;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmsFragment extends Fragment implements View.OnClickListener {


    private View view;
    private Button btnAlarmState;
    private FirebaseDAO firebaseDAO;
    private Resources r;
    public static List<Long> listAlarmas = new ArrayList();
    private RecyclerView mRecyclerDates;
    private RAAlarmas mAlarmas;
    private LinearLayoutManager mLinearLayoutManager;
    private Alarma alarmaF;
    private AlarmaDAO alarmaDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_alarms, container, false);
        NavigationActivity.toolbar.setTitle("Alarmas");
        init();
        getNodes(new Alarma());
        return view;

    }

    private void init(){
        firebaseDAO = new FirebaseDAO();
        alarmaDao = new AlarmaDAO();
        btnAlarmState = view.findViewById(R.id.btnStateAlarma);
        btnAlarmState.setOnClickListener(this);
    }
    private void setRecycler() throws IOException {
        r = getResources();

        mLinearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerDates = view.findViewById(R.id.rv_alarmas_in_fragmrnt) ;
        mRecyclerDates.setHasFixedSize(true);

        mRecyclerDates.setLayoutManager(mLinearLayoutManager);
        // mRecyclerDates.setItemAnimator(new DefaultItemAnimator());

        mAlarmas = new RAAlarmas(view.getContext(),listAlarmas);
        mRecyclerDates.setAdapter(mAlarmas);
        String alarmStrState = (alarmaF.getEstadoAlarma() == 0)?"Desactivada":"Activada";
        btnAlarmState.setText(alarmStrState);

    }

    public void getNodes(final Alarma alarma){
        listAlarmas = new ArrayList<>();
        //final FirebaseUser usa = firebaseDAO.firebaseAuth.getCurrentUser();
        DatabaseReference dbReference= firebaseDAO.getDatabaseReference().child("casa_1").child(alarma.getFirebaseNodeName());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listAlarmas = new ArrayList<>();
                int estadoAlarma = 0;
                List<Long> listaFechas = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   // String name = snapshot.child("fechaActivacionAlarma").getValue(String.class);
                    Log.d("TAG", snapshot.getKey()+"");
                    if(snapshot.getKey().toString().equals("estadoAlarma")){
                        estadoAlarma = Integer.parseInt(snapshot.getValue()+"");
                        Log.d("TAG", snapshot.getValue()+"");
                    }else{
                        String partOfLong = (snapshot.getValue().toString());
                        partOfLong =  partOfLong.substring(partOfLong .indexOf("{")+1,partOfLong.length()-1);
                        String[] part = partOfLong.split(",");
                        for (String str:part){
                            str = str.substring(str.indexOf("=")+1,str.length());
                            long time = Long.parseLong(str);
                            listAlarmas.add(time);
                            Log.d("TAG", time+"");
                        }
                    }
                }
                alarmaF = new Alarma(listAlarmas,estadoAlarma);
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


    @Override
    public void onClick(View view) {
        int vista = view.getId();
        switch (vista){
            case R.id.btnStateAlarma:{
                managementBluetooth.myConexionBT.write("3");
                alarmaDao.changeStateAlarm(0);
                btnAlarmState.setText("Desactivada");
                break;
            }
        }
    }
}
