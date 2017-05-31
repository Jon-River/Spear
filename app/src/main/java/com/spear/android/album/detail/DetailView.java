package com.spear.android.album.detail;

/**
 * Created by Pablo on 21/5/17.
 */

public interface DetailView {


    interface OnPushRatingToFirebase{
        void OnSucces(float currentRating);
        void OnError();
    }
}
