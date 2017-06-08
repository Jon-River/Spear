package com.spear.android.album;

import android.graphics.Bitmap;

import com.spear.android.pojo.CardImage;


public interface AlbumView {
    void openResultFragment(Bitmap imageBitmap, int requestCode, int resultCode);
    void showLoading();
    void notifyAdapter();

    void openCamera();

    void openGallery();

    void pushTofirebase(int requestCode, int resultCode, String s);

    interface OnImageClick {
        void onSuccess(CardImage card);
        void onError();

    }

    interface OnCameraCapture {
        void onSuccess(Bitmap imageBitmap, int requestCode, int resultCode);
        void onError();
        void hideLoading();
    }
}
