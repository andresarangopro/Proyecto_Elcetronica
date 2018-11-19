package com.example.hp.appelectr.Fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.appelectr.Activitys.NavigationActivity;
import com.example.hp.appelectr.FirebaseDAO;
import com.example.hp.appelectr.GridSpacingItemDecoration;
import com.example.hp.appelectr.Models.Habitacion;
import com.example.hp.appelectr.R;
import com.example.hp.appelectr.Recyclers.RAHabitaciones;
import com.example.hp.appelectr.Util;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.HORIZONTAL;
import static com.example.hp.appelectr.Activitys.NavigationActivity.inTemperatureF;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemperatureFragment extends Fragment  {

    public View view;
    private FirebaseDAO firebaseDAO;
    private Resources r;
    public static List<Habitacion> listHabitaciones = new ArrayList();
    private RecyclerView mRecyclerDates;
    private RAHabitaciones mHabitaciones;
    private LinearLayoutManager mLinearLayoutManager;
    public static ValueEventListener valueEventListen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_temperature, container, false);
        NavigationActivity.toolbar.setTitle("Habitaciones");
        init();
        getNodes(new Habitacion());
        return view;
    }


    public void init(){
        firebaseDAO = new FirebaseDAO();
        inTemperatureF = true;
    }

    private void setRecycler() throws IOException{
        r = getResources();

        mLinearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerDates = view.findViewById(R.id.rv_habitaciones_in_fragmrnt) ;
        mRecyclerDates.setHasFixedSize(true);
        mLinearLayoutManager =  new GridLayoutManager(view.getContext(), 2);
        mRecyclerDates.setLayoutManager(mLinearLayoutManager);
        mRecyclerDates.addItemDecoration(new GridSpacingItemDecoration(2, Util.dpToPx(10,r), true));
        mRecyclerDates.setLayoutManager(mLinearLayoutManager);
       // mRecyclerDates.setItemAnimator(new DefaultItemAnimator());

        mHabitaciones = new RAHabitaciones(view.getContext(),listHabitaciones);
        mRecyclerDates.setAdapter(mHabitaciones);

    }

    public void getNodes(final Habitacion habitacion){
        listHabitaciones = new ArrayList<>();
        //final FirebaseUser usa = firebaseDAO.firebaseAuth.getCurrentUser();
        DatabaseReference dbReference= firebaseDAO.getDatabaseReference().child("casa_1").child(habitacion.getFirebaseNodeName());
        valueEventListen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listHabitaciones = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Habitacion object = snapshot.getValue(habitacion.getClass());
                    Log.e("ERRN", object.getLastTimeListenM() + "");
                    listHabitaciones.add(object);
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
        dbReference.addListenerForSingleValueEvent(valueEventListen);

    }




}
