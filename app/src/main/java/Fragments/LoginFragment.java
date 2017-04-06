package Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.spear.android.R;

import Interactors.LoginInteractorImp;
import Interfaces.LoginInteractor;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private LoginInteractor loginInteractorImp;
    private EditText editTextUser;
    private EditText editTextPassword;
    private Button buttonLogin;


    public LoginFragment() {
        loginInteractorImp = new LoginInteractorImp(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);
        listeners();
        return view;
    }



    private void init(View view) {
        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        editTextUser = (EditText) view.findViewById(R.id.editTextUser);
        editTextUser.setText("usertest1@spear.com");
        editTextPassword.setText("usertest1");
        buttonLogin = (Button) view.findViewById(R.id.buttonLogin);
    }

    private void listeners() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginInteractorImp.logIn(view, editTextUser, editTextPassword);

            }
        });
    }


}
