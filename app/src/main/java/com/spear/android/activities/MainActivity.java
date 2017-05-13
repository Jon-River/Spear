package com.spear.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.auth.FirebaseAuth;
import com.spear.android.R;

import adapters.ViewPagerAdapter;
import fragments.profile.ProfileFragment;
import fragments.album.AlbumFragment;
import fragments.weather.WeatherFragment;
import fragments.TabFragment2;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AlbumFragment AlbumFragment;
    private FirebaseAuth firebaseAuth;
    private ProfileFragment profileFragment;
    private FragmentManager fm;
    private ActionBar actionBar;
    private InputMethodManager imm;
    private int[] tabIcons = {
            R.mipmap.fish_white_tab,
            R.mipmap.fish_white_tab,
            R.mipmap.fish_white_tab
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        imm = (InputMethodManager) getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        fm = getSupportFragmentManager();
        profileFragment = (ProfileFragment) fm.findFragmentById(R.id.profileFragment);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);




        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();

        firebaseAuth = FirebaseAuth.getInstance();
        cambiarFragment(0);
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);

    }
    private void backSignOut() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        firebaseAuth.signOut();
    }



    private void setupViewPager(ViewPager viewPager) {
        AlbumFragment =new AlbumFragment();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(AlbumFragment, "AlbumFragment");
        adapter.addFragment(new WeatherFragment(), "TWO");
        adapter.addFragment(new TabFragment2(), "THREE");
        //adapter.addFragment(new WeatherFragment(), "FOUR");
        viewPager.setAdapter(adapter);
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
        if(ifrg == 1){
            transaction.show(profileFragment);
        }
        transaction.commit();
    }
}
