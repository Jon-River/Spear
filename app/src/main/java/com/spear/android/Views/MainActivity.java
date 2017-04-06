package com.spear.android.Views;

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

import Adapters.ViewPagerAdapter;
import Fragments.ProfileFragment;
import Fragments.tab1Fragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private tab1Fragment tab1;
    private FirebaseAuth firebaseAuth;
    private ProfileFragment profileFragment;
    private FragmentManager fm;
    private ActionBar actionBar;
    private InputMethodManager imm;


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

        firebaseAuth = FirebaseAuth.getInstance();
        cambiarFragment(0);
    }

    private void backSignOut() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        firebaseAuth.signOut();
    }



    private void setupViewPager(ViewPager viewPager) {
        tab1=new tab1Fragment();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(tab1, "ONE");
        adapter.addFragment(new tab1Fragment(), "TWO");
        adapter.addFragment(new tab1Fragment(), "THREE");
        adapter.addFragment(new tab1Fragment(), "FOUR");
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
