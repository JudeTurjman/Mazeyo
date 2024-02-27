package com.jude.mazeyo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    FireBaseServices fbs;
    EditText etMail, etPass, etUser;
    Button btnSignup, btnLogin;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        fbs = FireBaseServices.getInstance();
        etMail = getView().findViewById(R.id.etEmailSignup);
        etPass = getView().findViewById(R.id.etPasswordSignup);
        etUser = getView().findViewById(R.id.etUsernameSinup);
        btnSignup = getView().findViewById(R.id.btnSignup);
        btnLogin = getView().findViewById(R.id.btnGotoLogin);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etMail.getText().toString();
                String pass = etPass.getText().toString();
                String username = etUser.getText().toString();
                if(email.trim().isEmpty() || pass.trim().isEmpty() || username.trim().isEmpty()){
                    Toast.makeText(getActivity(), "Some Fields are Missing", Toast.LENGTH_SHORT).show();
                    return;
                }

                fbs.getAuth().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(getActivity(), "Signup Successful!", Toast.LENGTH_SHORT).show();
                            CreateUser(username , email, pass);

                        }
                        else {

                            Toast.makeText(getActivity(), "Signup Failed!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToLogin();
            }
        });

    }

    // this thing for creating a new user
    private void CreateUser(String Uname ,String mail, String pass){
        User user = new User(Uname);

        fbs.getFirestore().collection("Users").document(mail).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                SignInNewUser(mail, pass);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getActivity(), "Creating User Profile Failed!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void SignInNewUser(String email, String pass) {

        fbs.getAuth().signInWithEmailAndPassword(email , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Toast.makeText(getActivity(), "Welcome!", Toast.LENGTH_SHORT).show();
                    SetHomeNav();

                }
                else{

                    Toast.makeText(getActivity(), "Login Failed!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void SetHomeNav() {

        fbs.setUser(null);

        // Getting the Navigation Bar From The Main Activity and Showing It!
        BottomNavigationView bnv = ((MainActivity) getActivity()).getBottomNavigationView();
        bnv.setSelectedItemId(R.id.nav_home);
        bnv.setVisibility(View.VISIBLE);

        // Go To Home Screen!
        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new HomeFragment());
        ft.commit();

    }

    private void GoToLogin() {

        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new LogInFragment());
        ft.commit();
    }
}