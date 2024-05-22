package com.jude.mazeyo.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.jude.mazeyo.objects.FireBaseServices;
import com.jude.mazeyo.activities.MainActivity;
import com.jude.mazeyo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogInFragment extends Fragment {

    FireBaseServices fbs;
    EditText etMail, etPass;
    Button btnLogin, btnSignup;
    TextView tvForgot;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LogInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogInFragment newInstance(String param1, String param2) {
        LogInFragment fragment = new LogInFragment();
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
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        fbs = FireBaseServices.getInstance();
        etMail = getView().findViewById(R.id.etEmailLogin);
        etPass = getView().findViewById(R.id.etPasswordLogin);
        btnLogin = getView().findViewById(R.id.btnLogin);
        btnSignup = getView().findViewById(R.id.btnGotoSignup);
        tvForgot = getView().findViewById(R.id.tvGotoForgotpassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etMail.getText().toString();
                String pass = etPass.getText().toString();

                if (email.trim().isEmpty() || pass.trim().isEmpty()){
                    Toast.makeText(getActivity(), "Some Fields are Missing", Toast.LENGTH_SHORT).show();
                    return;

                }

                fbs.getAuth().signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(getActivity(),"Logging in Successful!",Toast.LENGTH_SHORT).show();
                            fbs.setUser(null);
                            SetHomeNav();

                        }
                        else {

                            Toast.makeText(getActivity(), "Logging in Failed!", Toast.LENGTH_SHORT).show();

                        }


                    }
                });

            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToSignup();
            }
        });

        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {GoToForgot();}
        });


    }

    private void SetHomeNav() {

        // Refresh MainActivty and Go to HomeFragment
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);

    }

    private void GoToSignup() {

        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new SignUpFragment());
        ft.commit();
    }

    private void GoToForgot() {

        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new ForgotPasswordFragment());
        ft.commit();
    }

}