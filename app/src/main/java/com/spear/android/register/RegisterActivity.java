package com.spear.android.register;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.spear.android.R;

import com.spear.android.login.LoginActivity;

import static com.activeandroid.Cache.getContext;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private LoginActivity viewLogin;
    private EditText editTextUserRegister;
    private EditText editTextPasswordRegister;
    private EditText editTextEmailRegister;
    private Spinner spinnerProvinces;
    private Button buttonRegister;
    private RegisterPresenter registerPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        listeners();
        registerPresenter = new RegisterPresenter(this);

    }

    private void init() {
        editTextUserRegister = (EditText) findViewById(R.id.editTextUserRegister);
        editTextPasswordRegister = (EditText) findViewById(R.id.editTextPasswordRegister);
        editTextEmailRegister = (EditText) findViewById(R.id.editTextEmailRegister);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        spinnerProvinces = (Spinner) findViewById(R.id.spinnerProvinces);
        editTextPasswordRegister.setText("usertest1");
        editTextUserRegister.setText("usertest1");
        editTextEmailRegister.setText("usertest1@spear.com");

        Typeface type = Typeface.createFromAsset(getAssets(),"OpenSans-Regular.ttf");

        editTextPasswordRegister.setTypeface(type);
        editTextUserRegister.setTypeface(type);
        editTextEmailRegister.setTypeface(type);
        buttonRegister.setTypeface(type);


    }

    private void listeners() {
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                registerPresenter.registerUser(editTextUserRegister.getText().toString(),
                        editTextEmailRegister.getText().toString(),
                        editTextPasswordRegister.getText().toString(), spinnerProvinces.getSelectedItem().toString());
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void navigateToLogin() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    @Override
    public void showError(String error) {

    }
}
