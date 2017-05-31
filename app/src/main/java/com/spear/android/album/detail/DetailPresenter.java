package com.spear.android.album.detail;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by Pablo on 21/5/17.
 */

public class DetailPresenter {

    DetailActivity view;
    DetailInteractor detailInteractor;



    public DetailPresenter(DetailActivity view) {
        this.view = view;
        detailInteractor = new DetailInteractorImp(onPushRating);
    }

    public void pushRatingFirebase(long timeStamp, float rating) {
        Log.v("push","firebase"+ rating + timeStamp);
        detailInteractor.pushRatingToFirebase(timeStamp, rating);
    }

    DetailView.OnPushRatingToFirebase onPushRating = new DetailView.OnPushRatingToFirebase() {
        @Override
        public void OnSucces(float currentRating) {
            Toast.makeText(view, ""+currentRating, Toast.LENGTH_SHORT).show();
            view.ratingBar.setRating(currentRating);
        }

        @Override
        public void OnError() {

        }
    };
}
