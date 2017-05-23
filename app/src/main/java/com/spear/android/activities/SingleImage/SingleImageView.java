package com.spear.android.activities.SingleImage;

/**
 * Created by Pablo on 21/5/17.
 */

public interface SingleImageView {


    interface OnPushRatingToFirebase{
        void OnSucces(float currentRating);
        void OnError();
    }
}
