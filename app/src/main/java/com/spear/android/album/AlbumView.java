package com.spear.android.album;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.spear.android.pojo.GalleryCard;


public interface AlbumView {
    void openResultFragment(Bitmap imageBitmap, int requestCode, int resultCode);

    void showLoading();

    void openCamera();

    void openGallery();

    void pushTofirebase(ImageView image, int requestCode, int resultCode, String comentary);

    void pushRatingToFirebase(long timeStamp, float rating);

    void setNewDetailRating(float currentRating);

    void hideLoading();

    void showError(String s);

    void cambiarFragment(int hideFragment);

    interface OnImageClick {
        void onSuccess(GalleryCard card);
        void onError();

    }

    interface OnPushRatingToFirebase{
        void OnSucces(float currentRating);
        void OnError();
    }

    interface OnCameraCapture {
        void onSuccess(Bitmap imageBitmap, int requestCode, int resultCode);
        void onError();
        void hideLoading();
    }
}
