package com.spear.android.album;

import android.content.Intent;
import android.graphics.Bitmap;

import com.spear.android.pojo.CardImage;

import java.util.List;

/**
 * Created by Pablo on 7/4/17.
 */

public interface AlbumInteractor {

    void openCamera();

    void openGallery();
    
    void pushToFirebase(int requestCode, int resultCode, String comentary);

    void OnActivityResult(int requestCode, int resultCode, Intent data);

    void askForPermissions();

    void loadImageInfo();

    void pushRatingFirebase(long timeStamp, float rating);

    interface OnCameraCapture {
        void onSuccess(Bitmap imageBitmap);
        void onError();
        void startActivityForResult(Intent takePictureIntent, int requestCameraCapture);
        void hideLoading();
    }

    interface OnRenderImages{
        void notifyAdapter(List<CardImage> cardList);
    }


}
