package com.jude.mazeyo.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jude.mazeyo.objects.FireBaseServices;
import com.jude.mazeyo.activities.MainActivity;
import com.jude.mazeyo.R;
import com.jude.mazeyo.objects.User;
import com.jude.mazeyo.objects.Utils;
import com.yalantis.ucrop.UCrop;

import org.checkerframework.common.subtyping.qual.Bottom;

import java.io.File;
import java.util.UUID;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    private FireBaseServices fbs;
    private Utils utils;
    private String photo;
    ImageView ivGoBack, ivEditImage;
    TextView tvEdit;
    EditText etUname, etNote;
    Dialog dialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        fbs = FireBaseServices.getInstance();
        utils = Utils.getInstance();
        etNote = getView().findViewById(R.id.etEditNoteEditProfile);
        etUname = getView().findViewById(R.id.etEditUserNameEditProfile);
        String Uname = "";
        tvEdit = getView().findViewById(R.id.tvEditTheProfile);
        ivGoBack = getView().findViewById(R.id.ivGoBackEditProfile);
        ivEditImage = getView().findViewById(R.id.ivImageEditProfile);

        if(fbs.getUser() != null){

            etUname.setText(fbs.getUser().getUsername());
            etNote.setText(fbs.getUser().getComment());
            Uname = etUname.getText().toString();

            if (fbs.getUser().getPhoto() == null || fbs.getUser().getPhoto().isEmpty()) {
                Glide.with(getActivity()).load(R.mipmap.profile_launcher_foreground).into(ivEditImage);
            }else{
                Glide.with(getActivity()).load(fbs.getUser().getPhoto()).into(ivEditImage);
            }

        }



        ivEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageChooser();
            }
        });

        String finalUname = Uname;

        // this Click is for edit the user profile.
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = fbs.getUser();

                if(etNote.getText().toString().trim().isEmpty()||etUname.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getActivity(), "Some fields are empty !", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (fbs.getSelectedImageURL() != null) {
                    photo = fbs.getSelectedImageURL().toString()+".jpg";
                    user.setPhoto(photo);
                }

                if (!finalUname.equals(etUname.getText().toString())){
                    if (fbs.getUser().getCoin() < 10000){

                        user.setComment(etNote.getText().toString());

                    }
                    else {

                        dialog = new Dialog(getActivity());
                        dialog.setContentView(R.layout.edit_dialog_popup);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                        dialog.show();

                        Button edit = dialog.findViewById(R.id.btnEditProfile);
                        edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                user.setCoin( user.getCoin() - 10000);
                                user.setUsername(etUname.getText().toString());
                                user.setComment(etNote.getText().toString());
                                Toast.makeText(getActivity(), " -10,000", Toast.LENGTH_SHORT).show();
                                fbs.getFirestore().collection("Users").document(fbs.getAuth().getCurrentUser().getEmail()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        fbs.setUser(user);
                                    }
                                });

                                dialog.dismiss();
                                GoToProfile();
                            }
                        });
                    }
                }
                else {

                    user.setComment(etNote.getText().toString());

                }

                if (finalUname.equals(etUname.getText().toString()) || fbs.getUser().getCoin() < 10000){
                    fbs.getFirestore().collection("Users").document(fbs.getAuth().getCurrentUser().getEmail()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            fbs.setUser(user);
                        }
                    });

                    GoToProfile();
                }
            }
        });

        ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!finalUname.equals(etUname.getText().toString()) || !fbs.getUser().getComment().equals(etNote.getText().toString())){

                    dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.exit_edit_profile_dialog_popup);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
                    dialog.show();

                    Button exit = dialog.findViewById(R.id.btnExitEditProfile);

                    exit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            GoToProfile();
                        }
                    });

                }else {
                    GoToProfile();
                }
            }
        });

    }

    private void ImageChooser() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 123);
    }

    // TODO : find the problem and make the fbs take the image
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UCrop.REQUEST_CROP) {
            Uri resultUri = null;

            if(data!=null) resultUri = UCrop.getOutput(data);

            if(resultUri!=null) {
                ivEditImage.setImageURI(resultUri);
                utils.uploadImage(getActivity(), resultUri);
            }

        } else if(resultCode == UCrop.RESULT_ERROR) {
            // Close the UCrop.
        }
        if (requestCode == 123 && resultCode == getActivity().RESULT_OK && data != null) {

            Uri selectedImageUri = data.getData();
            startCropActivity(selectedImageUri);

        }
    }

    private void startCropActivity(Uri sourceUri) {

        Uri destinationUri = Uri.fromFile(new File(getActivity().getCacheDir(), UUID.randomUUID().toString()));

        UCrop.Options options = new UCrop.Options();

        UCrop uCrop = UCrop.of(sourceUri, destinationUri)
                .withAspectRatio(1,1)
                .withMaxResultSize(2000, 2000);
        uCrop.withOptions(options);
        uCrop.start(getContext(), this, UCrop.REQUEST_CROP);

    }

    private void GoToProfile() {

        BottomNavigationView bnv = ((MainActivity) getActivity()).getBottomNavigationView();
        bnv.setSelectedItemId(R.id.nav_profile);
        bnv.setVisibility(View.VISIBLE);

        // Go To Home Screen!
        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new ProfileFragment());
        ft.commit();

    }
}