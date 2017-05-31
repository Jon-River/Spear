package com.spear.android.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.spear.android.R;
import com.spear.android.news.NewsActivity;
import com.spear.android.register.RegisterActivity;

import static com.activeandroid.Cache.getContext;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private FirebaseAuth firebaseAuth;
    private LoginPresenter loginPresenter;
    private EditText editTextUser;
    private EditText editTextPassword;
    private Button btnLogin, btnRegister;
    private ProgressDialog dialog;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        firebaseAuth = FirebaseAuth.getInstance();
        loginPresenter = new LoginPresenter(this);


        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(this, NewsActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void init() {
        actionBar = getSupportActionBar();
        actionBar.hide();
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUser = (EditText) findViewById(R.id.editTextUser);
        editTextUser.setText("usertest1@spear.com");
        editTextPassword.setText("usertest1");
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        Typeface type = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        editTextPassword.setTypeface(type);
        editTextUser.setTypeface(type);
        btnLogin.setTypeface(type);
        btnRegister.setTypeface(type);

        dialog = new ProgressDialog(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                loginPresenter.logIn(editTextUser.getText().toString(), editTextPassword.getText().toString());
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


    }


    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void navigateToMain() {
        startActivity(new Intent(this, NewsActivity.class));
    }

    @Override
    public void showErrorEmptyFields() {
        Toast.makeText(getContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAuthError() {
        Toast.makeText(getContext(), "Error de autenticación", Toast.LENGTH_SHORT).show();
    }
}
