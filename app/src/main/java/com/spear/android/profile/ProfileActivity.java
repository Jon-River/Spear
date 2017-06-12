package com.spear.android.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.spear.android.R;
import com.spear.android.news.NewsActivity;

public class ProfileActivity extends AppCompatActivity implements ProfileView, View.OnClickListener {

    private ProfilePresenter profilePresenter;
    private EditText etEmail, etUsername, etOldPassword, etNewPassword;
    private Button btnBack, btnChangeNameEmail, btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();

        profilePresenter = new ProfilePresenter(this);
    }

    private void init() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etUsername = (EditText) findViewById(R.id.etUser);
        etOldPassword = (EditText) findViewById(R.id.etOldPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnChangeNameEmail = (Button) findViewById(R.id.btnChangeNameEmail);
        btnChangePassword = (Button) findViewById(R.id.btnChangePassword);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnBack){
            startActivity(new Intent(ProfileActivity.this, NewsActivity.class));
            finish();
        }
    }
}
