package com.jude.mazeyo.objects;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Utils {

    private static Utils instance;
    private FireBaseServices fbs;
    private String imageStr;

    public Utils() {
        fbs = FireBaseServices.getInstance();
    }

    public static Utils getInstance() {
        if (instance == null)
            instance = new Utils();

        return instance;
    }

    public void uploadImage(Context context, Uri selectedImageUri) {
        if (selectedImageUri != null) {

            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Your Image Is Being Uploaded");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();


            imageStr = "images/" + UUID.randomUUID() + ".jpg";
            StorageReference imageRef = fbs.getStorage().getReference().child("images/" + selectedImageUri.getLastPathSegment());

            UploadTask uploadTask = imageRef.putFile(selectedImageUri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            fbs.setSelectedImageURL(uri);
                            //UpdateProfilePicture(context);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Utils: uploadImage: ", e.getMessage());
                        }
                    });

                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Failed to upload image", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(context, "Please choose an image first", Toast.LENGTH_SHORT).show();
        }
    }

    //public void UpdateProfilePicture(Context context) {
    //
    //        String photo;
    //        if (fbs.getSelectedImageURL() == null) photo = "";
    //        else photo = fbs.getSelectedImageURL().toString() + ".jpg";
    //
    //        fbs.getFirestore().collection("Users").document(fbs.getAuth().getCurrentUser().getEmail()).update("photo", photo).addOnSuccessListener(new OnSuccessListener<Void>() {
    //            @Override
    //            public void onSuccess(Void unused) {
    //                Toast.makeText(context, "Profile Image Updated!", Toast.LENGTH_SHORT).show();
    //                fbs.getUser().setPhoto(photo);
    //            }
    //        }).addOnFailureListener(new OnFailureListener() {
    //            @Override
    //            public void onFailure(@NonNull Exception e) {
    //                Toast.makeText(context, "Couldn't Update Profile Image!", Toast.LENGTH_SHORT).show();
    //            }
    //        });
    //
    //    }
}
