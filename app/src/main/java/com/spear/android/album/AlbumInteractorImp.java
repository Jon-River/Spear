package com.spear.android.album;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spear.android.pojo.CardImage;
import com.spear.android.pojo.ImageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pablo on 30/5/17.
 */

public  class AlbumInteractorImp implements AlbumInteractor {

    private Activity activity;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 111;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private List<CardImage> cardList;

    public AlbumInteractorImp(Activity activity) {

        this.activity = activity;
        init();

    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        cardList = new ArrayList<>();
    }





    @Override public void askForPermissions() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity,
                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }



    @Override public void pushRatingFirebase(final long timeStamp, final float rating) {
        //Toast.makeText(getContext(), "" + timeStamp, Toast.LENGTH_SHORT).show();
        databaseReference.getRoot().child("images").child(String.valueOf(timeStamp)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ImageInfo image = dataSnapshot.getValue(ImageInfo.class);
                //long time = image.getTimeStamp();
                float currentRating = image.getRating();
                int votes = image.getVoted();
                votes= votes+1;
                currentRating =currentRating + rating;
                image.setRating(currentRating);
                image.setVoted(votes);
                Map<String, Object> map = new HashMap<>();
                map.put(String.valueOf(timeStamp),image);
                databaseReference.child("images").updateChildren(map);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}