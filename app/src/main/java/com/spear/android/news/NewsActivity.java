package com.spear.android.news;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.spear.android.R;
import com.spear.android.album.AlbumActivity;
import com.spear.android.custom.CustomTypeFace;
import com.spear.android.login.LoginActivity;
import com.spear.android.map.MapActivity;
import com.spear.android.pojo.NewsCard;
import com.spear.android.profile.ProfileFragment;
import com.spear.android.weather.WeatherActivity;

import java.util.ArrayList;

import static com.activeandroid.Cache.getContext;

public class NewsActivity extends AppCompatActivity implements NewsView, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {


    private FirebaseAuth firebaseAuth;

    private FragmentManager fm;
    private Menu menu;
    private TextView txtTittle;
    private VideoView mVV;
    private CollapsingToolbarLayout collapsingToolbar;
    private ActionBar actionBar;
    private ProfileFragment profileFragment;
    private NewsPresenter newsPresenter;
    private ArrayList<NewsCard> newsList;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    final int hideFragment = 0;
    final int profile = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        init();
        settingsVideo();
        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }


    }

    private void settingsVideo() {
        int fileRes = 0;
        String resourceName = "meros_edit";
        if (resourceName != null) {
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
        menuInflater.inflate(R.menu.menu_news, menu);
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
                openProfile();
                return true;
            case android.R.id.home:
                backSignOut();
                return true;
            case R.id.weathermenu:
                startActivity(new Intent(this, WeatherActivity.class));
                return true;
            case R.id.mapmenu:
                startActivity(new Intent(this, MapActivity.class));
                return true;
            case R.id.gallerymenu:
                startActivity(new Intent(this, AlbumActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void init() {
        newsPresenter = new NewsPresenter(this);
        actionBar = getSupportActionBar();
        recyclerView = (RecyclerView) findViewById(R.id.news_recycler);
        recyclerView.setNestedScrollingEnabled(false);
        newsList = new ArrayList<>();
        newsPresenter.loadNewsFirebase();
        //newsList.add(new NewsCard("ss","https://firebasestorage.googleapis.com/v0/b/spear-e5a6a.appspot.com/o/imagenews%2Fnoticia1image.jpg?alt=media&token=0504a857-33dd-4695-9f02-9310b3fdd066","sssss","sssss","ssssss"));

        adapter = new NewsAdapter(this, getContext(), newsList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);  //displays number of cards per row
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Typeface typeLibel = Typeface.createFromAsset(getAssets(), "Libel_Suit.ttf");

        SpannableStringBuilder typeFaceAction = new SpannableStringBuilder("Spear");
        typeFaceAction.setSpan(new CustomTypeFace("", typeLibel), 0, typeFaceAction.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        actionBar.setTitle(typeFaceAction);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        txtTittle = (TextView) findViewById(R.id.txtTittleCollapsing);
        txtTittle.setTypeface(typeLibel);
        collapsingToolbar.setCollapsedTitleTypeface(typeLibel);
        collapsingToolbar.setExpandedTitleTypeface(typeLibel);
        fm = getSupportFragmentManager();

        profileFragment = (ProfileFragment) fm.findFragmentById(R.id.profileFragment);
        firebaseAuth = FirebaseAuth.getInstance();
        cambiarFragment(hideFragment);
    }


    private void backSignOut() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        firebaseAuth.signOut();
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

    public void cambiarFragment(int ifrg) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(profileFragment);

        if (ifrg == profile) {
            transaction.show(profileFragment);
        }
        transaction.commit();
    }

    private boolean playFileRes(int fileRes) {
        if (fileRes == 0) {
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


    @Override
    public void render(ArrayList<NewsCard> newsListLoaded) {
        adapter.updateReceiptsList(newsListLoaded);

    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openUrlIntent(String url) {
        Intent openUrlIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(url));
        startActivity(openUrlIntent);
    }
}

