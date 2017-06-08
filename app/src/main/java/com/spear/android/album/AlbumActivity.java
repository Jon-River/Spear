package com.spear.android.album;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spear.android.R;
import com.spear.android.album.detail.DetailActivity;
import com.spear.android.album.galleryoption.GalleryOptionFragment;
import com.spear.android.album.result.ResultFragment;
import com.spear.android.custom.CustomTypeFace;
import com.spear.android.login.LoginActivity;
import com.spear.android.managers.CameraManager;
import com.spear.android.map.MapActivity;
import com.spear.android.news.NewsActivity;
import com.spear.android.pojo.CardImage;
import com.spear.android.pojo.ImageInfo;
import com.spear.android.profile.ProfileFragment;
import com.spear.android.weather.WeatherActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.activeandroid.Cache.getContext;
import static com.google.android.gms.internal.zzt.TAG;

public class AlbumActivity extends AppCompatActivity implements View.OnClickListener, AlbumView {

    private VideoView mVV;

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 111;
    private AlbumPresenter albumPresenter;
    private RecyclerView recyclerView;
    private AlbumAdapter adapter;
    private List<CardImage> cardList;
    private FloatingActionButton fabOpenCamera;

    private ImageButton btnOrderByRating, btnOrderByDate;


    private ProgressDialog dialog;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private Menu menu;
    private FragmentManager fm;
    private ArrayList<ImageInfo> imageArray;
    private ActionBar actionBar;
    private ProfileFragment profileFragment;
    private GalleryOptionFragment galleryOptionFragment;
    private ResultFragment resultFragment;
    private CameraManager cameraManager;
    private boolean isGalleryOptionShown;
    final int hideFragment = 0;
    final int galleryOption = 1;
    final int profile = 2;
    final int result = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        albumPresenter = new AlbumPresenter(this);
        init();

        //albumPresenter.loadImageInfo(); adapter not working
        loadImageInfo();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_album, menu);
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

                return true;
            case R.id.weathermenu:
                Intent intent = new Intent(this, WeatherActivity.class);
                startActivity(intent);
                return true;
            case R.id.mapmenu:
                startActivity(new Intent(this, MapActivity.class));
                return true;
            case R.id.newsmenu:
                startActivity(new Intent(this, NewsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        if (!isGalleryOptionShown) {
            if (view.getId() == R.id.fabOpenCamera) {
                albumPresenter.askForPermissions();
                fabOpenCamera.hide();
                cambiarFragment(galleryOption);

            } else if (view.getId() == R.id.btnOrderByDate) {
                if (imageArray != null) {
                    orderByDate(imageArray);
                }
            } else if (view.getId() == R.id.btnOrderRating) {
                if (imageArray != null) {
                    orderByRating(imageArray);
                }
            }

        }
    }

    private void orderByRating(ArrayList<ImageInfo> imageArray) {
        Collections.sort(imageArray, new Comparator<ImageInfo>() {
            @Override
            public int compare(ImageInfo o1, ImageInfo o2) {

                float rating1 = o1.getRating() / o1.getVoted();
                float rating2 = o2.getRating() / o2.getVoted();
                if (Float.isNaN(rating1)) {
                    rating1 = 0;
                } else if (Float.isNaN(rating2)) {
                    rating2 = 0;
                }
                return Float.compare(rating2, rating1);
            }
        });
        render(imageArray);
    }

    private void orderByDate(ArrayList<ImageInfo> imageArray) {
        Collections.sort(imageArray, new Comparator<ImageInfo>() {
            @Override
            public int compare(ImageInfo o1, ImageInfo o2) {
                return Long.compare(o1.getTimeStamp(), o2.getTimeStamp());
            }
        });
        render(imageArray);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            cameraManager.proccessImage(requestCode, resultCode, data);
        }
    }

    final AlbumView.OnCameraCapture onCameraCapture = new AlbumView.OnCameraCapture() {
        @Override
        public void onSuccess(Bitmap imageBitmap, int requestCode, int resultCode) {
            openResultFragment(imageBitmap, requestCode, resultCode);
        }

        @Override
        public void onError() {

        }

        @Override
        public void hideLoading() {
           dialog.dismiss();
            cambiarFragment(hideFragment);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void loadImageInfo() {

        imageArray = new ArrayList<>();

        databaseReference.getRoot().child("images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageArray.clear();
                cardList.clear();
                for (DataSnapshot imageSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: " + imageSnapshot.getKey());
                    ImageInfo image = imageSnapshot.getValue(ImageInfo.class);
                    imageArray.add(image);

                }
                while (imageArray.size()%3 !=0){
                    ImageInfo image2 = new ImageInfo("spear",45,111111,"adasdasdasdasd","https://firebasestorage.googleapis.com/v0/b/spear-e5a6a.appspot.com/o/Images%2F1495440673015?alt=media&token=68462995-3c3a-4425-b0c4-9e9c8cb406c3",5,"madrid","test");
                    imageArray.add(image2);

                }
                render(imageArray);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    final OnImageClick onImageClick = new OnImageClick() {
        @Override
        public void onSuccess(CardImage card) {
            if (!isGalleryOptionShown) {
                Log.v("", "" + card.getUsername() + " " + card.getUrlString());
                Intent intent = new Intent(AlbumActivity.this, DetailActivity.class);
                intent.putExtra("Editing", card);
                startActivity(intent);

            }
        }

        @Override
        public void onError() {

        }
    };

    private void render(ArrayList<ImageInfo> imgInfo) {
        cardList.clear();
        CardImage card;
        for (ImageInfo imageInfo : imgInfo) {
            card = new CardImage(imageInfo.getName(), imageInfo.getRating(), imageInfo.getUrl(),
                    imageInfo.getProvince(), imageInfo.getTimeStamp(), imageInfo.getVoted());
            cardList.add(card);
        }
        while (cardList.size()%3 !=0){
            CardImage image2 = new CardImage("spear",45,"https://firebasestorage.googleapis.com/v0/b/spear-e5a6a.appspot.com/o/Images%2F1495440673015?alt=media&token=68462995-3c3a-4425-b0c4-9e9c8cb406c3","madrid",1123123123,9);
            cardList.add(image2);

        }

        adapter.notifyDataSetChanged();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        actionBar = getSupportActionBar();

        cardList = new ArrayList<>();
        adapter = new AlbumAdapter(this, getContext(), cardList, onImageClick);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);  //displays number of cards per row
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        fabOpenCamera = (FloatingActionButton) findViewById(R.id.fabOpenCamera);
        fabOpenCamera.setOnClickListener(this);

        btnOrderByDate = (ImageButton) findViewById(R.id.btnOrderByDate);
        btnOrderByRating = (ImageButton) findViewById(R.id.btnOrderRating);
        btnOrderByDate.setOnClickListener(this);
        btnOrderByRating.setOnClickListener(this);

        Typeface typeLibel = Typeface.createFromAsset(getAssets(), "Libel_Suit.ttf");
        SpannableStringBuilder typeFaceAction = new SpannableStringBuilder("Gallery");
        typeFaceAction.setSpan(new CustomTypeFace("", typeLibel), 0, typeFaceAction.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        actionBar.setTitle(typeFaceAction);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading image");
        fm = getSupportFragmentManager();
        galleryOptionFragment = (GalleryOptionFragment) fm.findFragmentById(R.id.galleryOptionFragment);
        profileFragment = (ProfileFragment) fm.findFragmentById(R.id.profileFragment);
        resultFragment = (ResultFragment) fm.findFragmentById(R.id.resultFragment);

        cambiarFragment(hideFragment);
        cameraManager = new CameraManager(this, onCameraCapture);

    }

    @Override
    public void openResultFragment(Bitmap imageBitmap, int requestCode, int resultCode) {
        cambiarFragment(result);
        setImageBitmap(imageBitmap, requestCode,  resultCode);
    }

    private void setImageBitmap(Bitmap imageBitmap,int requestCode, int resultCode) {
    cambiarFragment(result);
        resultFragment.setImageBitmap(imageBitmap, requestCode,  resultCode);

    }

    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void openCamera() {
        cameraManager.openCameraIntent();
    }

    @Override
    public void openGallery() {
        cameraManager.openGalleryIntent();
    }

    @Override
    public void pushTofirebase(int requestCode, int resultCode, String comentary) {
        cameraManager.pushToFirebase(requestCode,resultCode,comentary);

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
        transaction.hide(galleryOptionFragment);
        transaction.hide(resultFragment);

        if (ifrg == profile) {
            transaction.show(profileFragment);
        } else if (ifrg == galleryOption) {
            isGalleryOptionShown = true;
            transaction.show(galleryOptionFragment);
        } else if (ifrg == hideFragment) {
            isGalleryOptionShown = false;
            if (!fabOpenCamera.isShown()) {
                fabOpenCamera.show();
            }
        }else if (ifrg== result){
            transaction.show(resultFragment);
        }
        transaction.commit();
    }

}
