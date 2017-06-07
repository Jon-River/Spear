package com.spear.android.album;

import android.app.Activity;
import android.app.Dialog;
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
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spear.android.R;
import com.spear.android.album.detail.DetailActivity;
import com.spear.android.custom.CustomTypeFace;
import com.spear.android.map.MapFragment;
import com.spear.android.news.NewsActivity;
import com.spear.android.pojo.CardImage;
import com.spear.android.pojo.ImageInfo;
import com.spear.android.weather.WeatherActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.activeandroid.Cache.getContext;
import static com.google.android.gms.internal.zzt.TAG;

public class AlbumActivity extends AppCompatActivity implements View.OnClickListener, AlbumView{

    private VideoView mVV;

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 111;
    private AlbumPresenter albumPresenter;

    private RecyclerView recyclerView;
    private AlbumAdapter adapter;
    private List<CardImage> cardList;

    private FloatingActionButton fabOpenCamera;
    private ImageView mImageView;
    private EditText editTextComentary;
    private Button btnOpenCamera, btnOpenGallery, btnUploadImage;
    private ImageButton btnOrderByRating, btnOrderByDate;
    private Dialog dialogButtons, dialogCameraView;

    private ProgressDialog dialog;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private Menu menu;
    private MapFragment mapFragment;
    private FragmentManager fm;
    private ArrayList<ImageInfo> imageArray;
    private ActionBar actionBar;


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
                // signOut();
                return true;
            case R.id.profile:
                //settings();
                return true;
            case android.R.id.home:
                //backSignOut();
                return true;
            case R.id.weathermenu:
                Intent intent = new Intent(this, WeatherActivity.class);
                startActivity(intent);
                return true;
            case R.id.mapmenu:
                if (menu.getItem(1).getTitle().equals("map")) {
                    openMapFragment();
                } else {
                    closeMapFragment();
                }
                return true;
            case R.id.newsmenu:
                startActivity(new Intent(this, NewsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closeMapFragment() {
        setTitle("Album");
        menu.getItem(1).setIcon(R.mipmap.earth);
        menu.getItem(1).setTitle("map");
        fabOpenCamera.show();
        cambiarFragment(0);
    }

    private void openMapFragment() {
        setTitle("Map");
        menu.getItem(1).setIcon(R.mipmap.ic_collections);
        menu.getItem(1).setTitle("album");
        fabOpenCamera.hide();
        cambiarFragment(2);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.fabOpenCamera) {
            albumPresenter.askForPermissions();
            dialogButtons.show();
        } else if (view.getId() == R.id.btnOpenCamera) {
            dialogButtons.dismiss();
            albumPresenter.openCamera();
        } else if (view.getId() == R.id.btnOpenGallery) {
            dialogButtons.dismiss();
            albumPresenter.openGallery();
        } else if (view.getId() == R.id.btnOrderByDate) {
            Log.v("order","rating");
            if (imageArray != null) {
                Log.v("order","rating imagearray" );
                orderByDate(imageArray);
            }
        } else if (view.getId() == R.id.btnOrderRating) {
            Log.v("order","rating");
            if (imageArray != null) {
                Log.v("order","rating imagearray" );
                orderByRating(imageArray);
            }
        }
    }

    private void orderByRating(ArrayList<ImageInfo> imageArray) {
        Collections.sort(imageArray, new Comparator<ImageInfo>() {
            @Override
            public int compare(ImageInfo o1, ImageInfo o2) {

                float rating1 = o1.getRating() / o1.getVoted();
                float rating2 = o2.getRating() / o2.getVoted();
                if (Float.isNaN(rating1)){
                    rating1 = 0;
                }else if (Float.isNaN(rating2)){
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
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCameraView.dismiss();
                dialog.show();
                dialog.setContentView(R.layout.custom_progress_dialog);
                albumPresenter.pushTofirebase(requestCode, resultCode,
                        editTextComentary.getText().toString());
            }
        });

        if (resultCode == Activity.RESULT_OK) {
            dialogCameraView.show();
            albumPresenter.OnActivityResult(requestCode, resultCode, data);
        }
    }

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

        // storageReference = FirebaseStorage.getInstance().getReference().child("Images").child(FirebaseAuth.getInstance().getCurrentUser().toString());

        System.out.print("user " + firebaseAuth.getCurrentUser().getUid());
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
            Log.v("", "" + card.getUsername() + " " + card.getUrlString());
            Intent intent = new Intent(AlbumActivity.this, DetailActivity.class);
            intent.putExtra("Editing", card);
            startActivity(intent);
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

        dialogButtons = new Dialog(this);
        dialogButtons.setContentView(R.layout.open_camera_dialog);
        btnOpenCamera = (Button) dialogButtons.findViewById(R.id.btnOpenCamera);
        btnOpenGallery = (Button) dialogButtons.findViewById(R.id.btnOpenGallery);
        btnOrderByDate = (ImageButton) findViewById(R.id.btnOrderByDate);
        btnOrderByRating = (ImageButton) findViewById(R.id.btnOrderRating);
        btnOrderByDate.setOnClickListener(this);
        btnOrderByRating.setOnClickListener(this);
        btnOpenCamera.setOnClickListener(this);
        btnOpenGallery.setOnClickListener(this);
        dialogCameraView = new Dialog(this);
        dialogCameraView.setContentView(R.layout.result_camera_dialog);
        mImageView = (ImageView) dialogCameraView.findViewById(R.id.imageResultDialog);

        Typeface typeLibel = Typeface.createFromAsset(getAssets(), "Libel_Suit.ttf");
        SpannableStringBuilder typeFaceAction = new SpannableStringBuilder("Gallery");
        typeFaceAction.setSpan (new CustomTypeFace("", typeLibel), 0, typeFaceAction.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        actionBar.setTitle(typeFaceAction);

        Display display = getWindowManager().getDefaultDisplay();
        int width = ((display.getWidth() * 8) / 10);
        int height = ((display.getHeight() * 10) / 10);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
        mImageView.setLayoutParams(parms);
        btnUploadImage = (Button) dialogCameraView.findViewById(R.id.btnUploadPhoto);
        editTextComentary = (EditText) dialogCameraView.findViewById(R.id.editTextComentary);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading image");
        fm = getSupportFragmentManager();
        mapFragment = (MapFragment) fm.findFragmentById(R.id.mapFragment);
        cambiarFragment(0);

    }

    public void pushRatingFirebase(final long timeStamp, final float rating) {
        albumPresenter.pushRatingFirebase(timeStamp, rating);
    }

    @Override
    public void setImageBitmap(Bitmap imageBitmap) {
        mImageView.setImageBitmap(imageBitmap);
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    public void cambiarFragment(int ifrg) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(mapFragment);

        if (ifrg == 1) {

        } else if (ifrg == 2) {
            transaction.show(mapFragment);
        }
        transaction.commit();
    }

}
class MyComparator implements Comparator<ImageInfo> {

    @Override
    public int compare(ImageInfo imageInfo, ImageInfo t1) {
        return (int) imageInfo.getRating()-(int) t1.getRating();
    }
}
