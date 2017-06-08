package com.spear.android.album;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;

import com.spear.android.pojo.ImageInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Pablo on 7/5/17.
 */

public class AlbumPresenter {

    private AlbumActivity view;
    private AlbumInteractor albumInteractor;
    final int hideFragment = 0;


    public AlbumPresenter(AlbumActivity view) {
        this.view = view;
        albumInteractor = new AlbumInteractorImp(view, onPushRating, onPushImage);
    }


    AlbumView.OnPushRatingToFirebase onPushRating = new AlbumView.OnPushRatingToFirebase() {
        @Override
        public void OnSucces(float currentRating) {
            Toast.makeText(view, "" + currentRating, Toast.LENGTH_SHORT).show();
            view.setNewDetailRating(currentRating);
        }

        @Override
        public void OnError() {

        }
    };


    public void askForPermissions() {
        albumInteractor.askForPermissions();
    }


    public void pushRatingFirebase(long timeStamp, float rating) {
        albumInteractor.pushRatingFirebase(timeStamp, rating);
    }

    public ArrayList<ImageInfo> orderByRating(ArrayList<ImageInfo> imageArray) {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        Collections.sort(imageArray, new Comparator<ImageInfo>() {
            @Override
            public int compare(ImageInfo o2, ImageInfo o1) {

                float rating1 = o1.getRating() / o1.getVoted();
                float rating2 = o2.getRating() / o2.getVoted();
                if (Float.isNaN(rating1)) {
                    rating1 = 0;
                } else if (Float.isNaN(rating2)) {
                    rating2 = 0;
                }
                return Float.compare(rating2, rating1);
            }
        });
        Collections.reverse(imageArray);
        return imageArray;
    }

    public ArrayList<ImageInfo> orderByDate(ArrayList<ImageInfo> imageArray) {
        Collections.sort(imageArray, new Comparator<ImageInfo>() {
            @Override
            public int compare(ImageInfo o1, ImageInfo o2) {
                return Long.compare(o2.getTimeStamp(), o1.getTimeStamp());
            }
        });
        return imageArray;
    }

    public void pushToFirebase(Intent galleryIntent, ImageView image, int requestCode, int resultCode, String comentary) {
        albumInteractor.pushToFirebase(galleryIntent,image, requestCode, resultCode, comentary);
    }

    final AlbumInteractor.OnPushImage onPushImage = new AlbumInteractor.OnPushImage() {
        @Override
        public void OnSuccess() {
            view.hideLoading();
            view.cambiarFragment(hideFragment);
        }

        @Override
        public void OnError(String s) {
            view.hideLoading();
            view.showError(s);
        }
    };
}
