package com.spear.android.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.spear.android.OnClearFromRecentService;
import com.spear.android.R;
import com.spear.android.news.NewsActivity;
import com.spear.android.register.RegisterActivity;


public class LoginActivity extends AppCompatActivity implements LoginView , MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener{

    private FirebaseAuth firebaseAuth;
    private LoginPresenter loginPresenter;
    private EditText editTextUser;
    private EditText editTextPassword;
    private Button btnLogin, btnRegister;
    private ProgressDialog dialog;
    private ActionBar actionBar;
    private TextView txtTittle;

    private VideoView mVV;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        settingsVideo();
        firebaseAuth = FirebaseAuth.getInstance();
        loginPresenter = new LoginPresenter(this);


        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(this, NewsActivity.class);
            startActivity(intent);
            finish();
        }
        //playVideo("login_spear_video1");

    }

    private void settingsVideo() {
        int fileRes=0;
       String resourceName = "login_spear_mero";
        if (resourceName!=null) {
            fileRes = this.getResources().getIdentifier(resourceName, "raw", getPackageName());
        }

        mVV = (VideoView) findViewById(R.id.vwLogin);
        mVV.setOnCompletionListener(this);
        mVV.setOnPreparedListener(this);

        if (!playFileRes(fileRes)) return;

        mVV.start();
    }

    private void init() {
        startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        actionBar = getSupportActionBar();
        actionBar.hide();
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUser = (EditText) findViewById(R.id.editTextUser);
        editTextUser.setText("usertest1@spear.com");
        editTextPassword.setText("usertest1");
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtTittle = (TextView) findViewById(R.id.txtTittle);

        Typeface typeLibel = Typeface.createFromAsset(getAssets(), "Libel_Suit.ttf");

        editTextPassword.setTypeface(typeLibel);
        editTextUser.setTypeface(typeLibel);
        btnLogin.setTypeface(typeLibel);
        btnRegister.setTypeface(typeLibel);
        txtTittle.setTypeface(typeLibel);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Iniciando sesión");


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAuthError() {
        Toast.makeText(this, "Error de autenticación", Toast.LENGTH_SHORT).show();
    }



    private boolean playFileRes(int fileRes) {
        if (fileRes==0) {
            stopPlaying();
            return false;
        } else {
            mVV.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + fileRes));
            return true;
        }
    }

    public void stopPlaying() {
        mVV.stopPlayback();
        this.finish();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mVV.stopPlayback();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVV.start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.setLooping(true);
    }
}
