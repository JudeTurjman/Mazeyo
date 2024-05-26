package com.jude.mazeyo.objects;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Stack;

public class FireBaseServices {

    private  static FireBaseServices instance;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private FirebaseStorage storage;
    private User user;
    private String difficulty;
    private Uri selectedImageURL;

    // -------------------------------
    private static String seeRank;
    private static String CurrentPage;

    public static String getCurrentPage() {
        return CurrentPage;
    }

    public static void setCurrentPage(String currentPage) {
        CurrentPage = currentPage;
    }

    public static String getSeeRank() {
        return seeRank;
    }

    public static void setSeeRank(String seeRank) {
        FireBaseServices.seeRank = seeRank;
    }

    public Uri getSelectedImageURL() {
        return selectedImageURL;
    }

    public void setSelectedImageURL(Uri selectedImageURL) {
        this.selectedImageURL = selectedImageURL;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
