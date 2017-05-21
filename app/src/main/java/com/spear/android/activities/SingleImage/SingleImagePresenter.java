package com.spear.android.activities.SingleImage;

import android.util.Log;
import android.widget.Toast;

import interactors.single_image.SingleImageInteractor;
import interactors.single_image.SingleImageInteractorImp;

/**
 * Created by Pablo on 21/5/17.
 */

public class SingleImagePresenter {

    SingleImageActivity view;
    SingleImageInteractor singleImageInteractor;



    public SingleImagePresenter(SingleImageActivity view) {
        this.view = view;
        singleImageInteractor= new SingleImageInteractorImp(onPushRating);
    }

    public void pushRatingFirebase(long timeStamp, float rating) {
        Log.v("push","firebase"+ rating + timeStamp);
        singleImageInteractor.pushRatingToFirebase(timeStamp, rating);
    }

    SingleImageView.OnPushRatingToFirebase onPushRating = new SingleImageView.OnPushRatingToFirebase() {
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
