package com.spear.android.album.detail;

/**
 * Created by Pablo on 21/5/17.
 */

public interface DetailInteractor {

    void pushRatingToFirebase(long timeStamp, float rating);
}
