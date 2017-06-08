package com.spear.android.album;

import android.content.Intent;
import android.widget.ImageView;

/**
 * Created by Pablo on 7/4/17.
 */

public interface AlbumInteractor {

    void askForPermissions();


    void pushRatingFirebase(long timeStamp, float rating);

    void pushToFirebase(Intent galleryIntent, ImageView image, int requestCode, int resultCode, final String comentary);


    interface OnPushImage {
        void OnSuccess();
        void OnError(String s);
    }

}
