package com.spear.android.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import com.google.firebase.auth.FirebaseAuth;
import com.spear.android.R;
import fragments.login.LoginFragment;
import fragments.register.RegisterFragment;

public class LoginActivity extends AppCompatActivity {

  private LoginFragment loginFragment;
  private RegisterFragment registerFragment;
  private FragmentManager fm;
  private FirebaseAuth firebaseAuth;
  private ActionBar actionBar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    init();
    firebaseAuth = FirebaseAuth.getInstance();

    if (firebaseAuth.getCurrentUser() != null) {
      firebaseAuth.signOut();/*
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);*/
    }
  }

  private void init() {
    actionBar = getSupportActionBar();
    actionBar.hide();
    fm = getSupportFragmentManager();
    registerFragment = (RegisterFragment) fm.findFragmentById(R.id.fragmentRegister);
    loginFragment = (LoginFragment) fm.findFragmentById(R.id.fragmentLogin);
    registerFragment.setActivity(this);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    cambiarFragment(1);
  }

  public void openFragmentRegister(View view) {
    cambiarFragment(2);
  }

  public void cambiarFragment(int ifrg) {
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction transaction = fm.beginTransaction();
    transaction.hide(loginFragment);
    transaction.hide(registerFragment);

    if (ifrg == 1) {
      transaction.show(loginFragment);
    } else if (ifrg == 2) {
      transaction.show(registerFragment);
    } else if (ifrg == 3) {

    }

    transaction.commit();
  }
}
