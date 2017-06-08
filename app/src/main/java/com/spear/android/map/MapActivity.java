package com.spear.android.map;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.spear.android.R;
import com.spear.android.album.AlbumActivity;
import com.spear.android.custom.CustomTypeFace;
import com.spear.android.login.LoginActivity;
import com.spear.android.news.NewsActivity;
import com.spear.android.profile.ProfileFragment;
import com.spear.android.weather.WeatherActivity;

public class MapActivity extends AppCompatActivity {


    private MapFragment mapFragment;
    private FragmentManager fm;
    private Menu menu;
    private ActionBar actionBar;
    private ProfileFragment profileFragment;
    private FirebaseAuth firebaseAuth;
    final int map = 1;
    final int profile = 2;
    final int hideFragment = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        init();
        cambiarFragment(map);
    }

    private void init() {
        fm = getSupportFragmentManager();
        mapFragment = (MapFragment) fm.findFragmentById(R.id.mapFragment);
        actionBar = getSupportActionBar();
        Typeface typeLibel = Typeface.createFromAsset(getAssets(), "Libel_Suit.ttf");
        SpannableStringBuilder typeFaceAction = new SpannableStringBuilder("Map");
        typeFaceAction.setSpan(new CustomTypeFace("", typeLibel), 0, typeFaceAction.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        actionBar.setTitle(typeFaceAction);
        profileFragment = (ProfileFragment) fm.findFragmentById(R.id.profileFragment);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.signOutItemmap:
                signOut();
                return true;
            case R.id.profilemap:
                openProfile();
                return true;
            case android.R.id.home:

                return true;
            case R.id.newsmap:
                startActivity(new Intent(this, NewsActivity
                        .class));
                return true;
            case R.id.weathermap:
                startActivity(new Intent(this, WeatherActivity
                        .class));
                return true;
            case R.id.gallerymap:
                startActivity(new Intent(this, AlbumActivity
                        .class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cambiarFragment(int ifrg) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(mapFragment);
        transaction.hide(profileFragment);
        if (ifrg == map) {
            transaction.show(mapFragment);
        } else if (ifrg == profile) {
            transaction.show(profileFragment);
        }
        transaction.commit();
    }

    private void initLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void signOut() {
        firebaseAuth.signOut();
        initLogin();
    }

    private void openProfile() {
        cambiarFragment(profile);

    }
}
