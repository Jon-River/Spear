package com.spear.android.news;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.spear.android.R;
import com.spear.android.album.AlbumActivity;
import com.spear.android.login.LoginActivity;
import com.spear.android.map.MapFragment;
import com.spear.android.profile.ProfileFragment;
import com.spear.android.weather.WeatherActivity;

public class NewsActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener{



    private FirebaseAuth firebaseAuth;
    private ProfileFragment profileFragment;
    private MapFragment mapFragment;
    private FragmentManager fm;
    private Menu menu;

    private VideoView mVV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        init();

        settingsVideo();
        if (firebaseAuth.getCurrentUser() ==null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }



    }
    private void settingsVideo() {
        int fileRes=0;
        String resourceName = "meros_edit";
        if (resourceName!=null) {
            fileRes = this.getResources().getIdentifier(resourceName, "raw", getPackageName());
        }

        mVV = (VideoView) findViewById(R.id.vwNews);
        mVV.setOnCompletionListener(this);
        mVV.setOnPreparedListener(this);

        if (!playFileRes(fileRes)) return;

        mVV.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.signOutItem:
                signOut();
                return true;
            case R.id.profile:
                settings();
                return true;
            case android.R.id.home:
                backSignOut();
                return true;
            case R.id.weathermenu:
                startActivity(new Intent(this, WeatherActivity.class) );
                return true;
            case R.id.mapmenu:
                if (menu.getItem(1).getTitle().equals("map")){
                    openMapFragment();
                }else{
                    closeMapFragment();
                }
                return true;
            case R.id.gallerymenu:
                startActivity(new Intent(this, AlbumActivity.class) );
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closeMapFragment(){
        setTitle("Spear");
        cambiarFragment(0);
        menu.getItem(1).setIcon(R.mipmap.earth);
        menu.getItem(1).setTitle("map");
    }

    private void openMapFragment() {
        setTitle("Map");
        menu.getItem(1).setIcon(R.mipmap.news_list);
        menu.getItem(1).setTitle("newslist");
        cambiarFragment(2);
    }

    private void init() {



        fm = getSupportFragmentManager();
        profileFragment = (ProfileFragment) fm.findFragmentById(R.id.profileFragment);
        mapFragment = (MapFragment) fm.findFragmentById(R.id.mapFragment);

        firebaseAuth = FirebaseAuth.getInstance();
        cambiarFragment(0);
    }

    public void FullScreencall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private void settingsNavigationStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

    }

    /*private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);

    }*/
    private void backSignOut() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        firebaseAuth.signOut();
    }




    private void initLogin() {
        Intent  intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void signOut(){
        firebaseAuth.signOut();
        initLogin();
    }

    private void settings() {
        cambiarFragment(1);

    }
    public void cambiarFragment(int ifrg){
        FragmentManager fm  = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(profileFragment);

        transaction.hide(mapFragment);

        if(ifrg == 1){
            transaction.show(profileFragment);
        }else if (ifrg == 2){
            transaction.show(mapFragment);
        }
        transaction.commit();
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
    public void onPrepared(MediaPlayer mp) {
        mp.setLooping(true);
    }


}
