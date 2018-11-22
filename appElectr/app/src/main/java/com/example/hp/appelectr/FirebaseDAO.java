package com.example.hp.appelectr;

import android.support.v4.app.Fragment;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class FirebaseDAO implements Listable{

    public static boolean calledAlready = false;
    private DatabaseReference databaseReference;
    public FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;
    private StorageReference mStorage;

    public FirebaseDAO() {
        inicializerFirebase();
    }

    public void inicializerFirebase(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference();
        if (!calledAlready) {
            firebaseDatabase.setPersistenceEnabled(true);
            calledAlready = true;
        }
        databaseReference = firebaseDatabase.getReference();
    }

    /**
     * Obtiene el usuario de la sesión actual
     * @return Uid del usuario actual
     */
   private String getCurrentUserUid(){
        return firebaseUser.getUid();
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public StorageReference getmStorage() {
        return mStorage;
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
