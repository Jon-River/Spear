package com.spear.android.news;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spear.android.pojo.NewsCard;

import java.util.ArrayList;
import java.util.Collections;

import static com.google.android.gms.internal.zzt.TAG;

/**
 * Created by Pablo on 10/6/17.
 */

public class NewsInteractorImp implements  NewsInteractor{


    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private OnLoadNews onLoadNews;
    private ArrayList <NewsCard> newsList;


    public NewsInteractorImp(OnLoadNews onLoadNews) {
        this.onLoadNews = onLoadNews;
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void loadNewsFirebase() {
       newsList = new ArrayList<>();
        databaseReference.getRoot().child("news").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newsList.clear();
                for (DataSnapshot imageSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: " + imageSnapshot.getKey());
                    NewsCard news = imageSnapshot.getValue(NewsCard.class);
                    newsList.add(news);
                }
                Collections.reverse(newsList);
                onLoadNews.OnSuccess(newsList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //onLoadNews.OnError(databaseError.toString());
            }
        });

    }
}
