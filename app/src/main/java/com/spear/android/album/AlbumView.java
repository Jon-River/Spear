package com.spear.android.album;

import android.content.Intent;
import android.graphics.Bitmap;

import com.spear.android.pojo.CardImage;


public interface AlbumView {
    void setImageBitmap(Bitmap imageBitmap);
    void hideLoading();
    void notifyAdapter();

    void startActivityForResult(Intent takePictureIntent, int requestCameraCapture);

    interface OnImageClick {
        void onSuccess(CardImage card);
        void onError();

    }
}
