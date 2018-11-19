package com.example.hp.appelectr;

import android.support.v4.app.Fragment;

import com.google.firebase.database.DataSnapshot;

import java.util.List;

/**
 * Created by Santiago Urrego Morales on 8/25/2017.
 */

public interface Listable {

    Listable getInstance(DataSnapshot dataSnapshot);

    String[] print();

    Fragment getActivity();

    List<Listable> filter(List<Listable> data, String filter);

    String getFirebaseNodeName();

}
