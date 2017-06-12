package com.spear.android.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.spear.android.R;
import com.spear.android.custom.CustomTypeFace;
import com.spear.android.login.LoginActivity;

import static com.activeandroid.Cache.getContext;

public class RegisterActivity extends AppCompatActivity implements RegisterView, View.OnClickListener {

    private LoginActivity viewLogin;
    private EditText etUser;
    private EditText etPassword;
    private EditText etPasswordRepeat;

    private EditText etEmail;

    private Button btnRegister, btnBack;
    private RegisterPresenter registerPresenter;
    private ActionBar actionBar;
    private Typeface typeLibel;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        registerPresenter = new RegisterPresenter(this);

    }

    private void init() {
        setTitle("Register");
        actionBar = getSupportActionBar();
        typeLibel = Typeface.createFromAsset(getAssets(), "Libel_Suit.ttf");

        SpannableStringBuilder typeFaceAction = new SpannableStringBuilder("Spear");
        typeFaceAction.setSpan(new CustomTypeFace("", typeLibel), 0, typeFaceAction.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        actionBar.setTitle(typeFaceAction);


        etUser = (EditText) findViewById(R.id.etUser);
        etPassword = (EditText) findViewById(R.id.etPass);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPasswordRepeat = (EditText) findViewById(R.id.etPassRe);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

/*
        etPassword.setText("usertest1");
        etUser.setText("usertest1");
        etEmail.setText("usertest1@spear.com");
        etPasswordRepeat.setText("usertest1");*/
        etPassword.setTypeface(typeLibel);
        etUser.setTypeface(typeLibel);
        etEmail.setTypeface(typeLibel);
        etPasswordRepeat.setTypeface(typeLibel);
        btnRegister.setTypeface(typeLibel);
        btnBack.setTypeface(typeLibel);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Registrando Usuario");


    }


    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.hide();
    }

    @Override
    public void navigateToLogin() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegister) {
            if (comparePassword()) {
                showLoading();
                registerPresenter.registerUser(etUser.getText().toString(),
                        etEmail.getText().toString(),
                        etPassword.getText().toString());
            } else {
                Toast.makeText(this, "La contraseña no coindide", Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.btnBack) {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }
    }

    private boolean comparePassword() {
        if (etPasswordRepeat.getText().toString().equals(etPassword.getText().toString())) {
            return true;
        } else {
            return false;
        }

    }
}
