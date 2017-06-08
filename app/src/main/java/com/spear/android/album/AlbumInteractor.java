package com.spear.android.album;

/**
 * Created by Pablo on 7/4/17.
 */

public interface AlbumInteractor {

    void askForPermissions();


    void pushRatingFirebase(long timeStamp, float rating);




}
