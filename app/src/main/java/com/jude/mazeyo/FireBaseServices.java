package com.jude.mazeyo;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class FireBaseServices {

    private  static FireBaseServices instance;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private FirebaseStorage storage;

    public FirebaseFirestore getFirestore() {
        return firestore;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }

    public  FireBaseServices()
    {
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        storage=FirebaseStorage.getInstance();
    }

    public  static FireBaseServices getInstance(){

        if (instance==null){

            instance=new FireBaseServices();

        }
        return instance;
    }

}
