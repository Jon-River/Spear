package Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.spear.android.R;
import com.spear.android.Views.LoginActivity;

import Interactors.RegisterInteractorImp;
import Interfaces.RegisterInteractor;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    private RegisterInteractor registerInteractorImp;
    public LoginActivity viewLogin;
    private EditText editTextUserRegister;
    private EditText editTextPasswordRegister;
    private EditText editTextEmailRegister;
    private Button buttonRegister;





    public RegisterFragment() {
        // Required empty public constructor
        registerInteractorImp = new RegisterInteractorImp(this);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registro, container, false);
        init(view);
        listeners();

        return view;
    }


    private void init(View view) {
        editTextUserRegister = (EditText) view.findViewById(R.id.editTextUserRegister);
        editTextPasswordRegister = (EditText) view.findViewById(R.id.editTextPasswordRegister);
        editTextEmailRegister = (EditText) view.findViewById(R.id.editTextEmailRegister);
        buttonRegister = (Button) view.findViewById(R.id.buttonRegister);
        editTextPasswordRegister.setText("usertest1");
        editTextUserRegister.setText("usertest1");
        editTextEmailRegister.setText("usertest1@spear.com");
    }

    private void listeners() {
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerInteractorImp.registerUser(view, editTextUserRegister, editTextEmailRegister, editTextPasswordRegister);
            }
        });
    }



    public void setActivity(LoginActivity loginActivity) {
        this.viewLogin = loginActivity;
    }

    public void openFragementLogin() {
         viewLogin.cambiarFragment(1);
    }
}
