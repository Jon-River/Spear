package com.spear.android.map;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spear.android.pojo.PoiInfo;

import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.internal.zzt.TAG;

/**
 * Created by Pablo on 2/6/17.
 */

public class MapInteractorImp implements MapInteractor {

    private OnPushPoiFirebase onPushPoiFirebase;
    private OnLoadPoiFirebase onLoadPoiFirebase;
    private OnPoiDeletedFirebase onPoiDeletedFirebase;
    private Map<String, Object> map;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public MapInteractorImp(OnPushPoiFirebase onPushPoiFirebase, OnLoadPoiFirebase onLoadPoiFirebase, OnPoiDeletedFirebase onPoiDeletedFirebase) {
        this.onPushPoiFirebase = onPushPoiFirebase;
        this.onLoadPoiFirebase = onLoadPoiFirebase;
        this.onPoiDeletedFirebase = onPoiDeletedFirebase;
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void loadGeoPointsFirebase() {
        map = new HashMap<>();
        databaseReference.getRoot().child("users").child(firebaseAuth.getCurrentUser().getUid()).child("pois").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                map.clear();
                for (DataSnapshot imageSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: " + imageSnapshot.getKey());
                    PoiInfo poi = imageSnapshot.getValue(PoiInfo.class);
                    map.put(String.valueOf(poi.getTimestamp()), (PoiInfo) poi);

                }

                onLoadPoiFirebase.OnSuccess(map);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //onLoadNews.OnError(databaseError.toString());
            }
        });

    }

    @Override
    public void deletePoiFirebase(final String timestamp) {
        databaseReference.getRoot().child("users").child(firebaseAuth.getCurrentUser().getUid()).child("pois").child(timestamp).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                onPoiDeletedFirebase.OnSucces(timestamp);
            }
        });
    }

    @Override
    public void pushGeoPointFirebase(final String latitude, final String longitude, final String description) {
        map = new HashMap<>();
        databaseReference.getRoot().child("users").child(firebaseAuth.getCurrentUser().getUid()).child("pois").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                map.clear();
                long timestamp = System.currentTimeMillis();
                PoiInfo poi = new PoiInfo(latitude, longitude, description, timestamp);
                map.put(String.valueOf(timestamp), poi);
                databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("pois").updateChildren(map);
                onPushPoiFirebase.OnSuccess(map);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
