package com.spear.android.album;

/**
 * Created by Pablo on 7/5/17.
 */

public class AlbumPresenter {

    private AlbumActivity view;
    private AlbumInteractor albumInteractor;


    public AlbumPresenter(AlbumActivity view) {
        this.view = view;
        albumInteractor = new AlbumInteractorImp(view) ;
    }





    public void askForPermissions() {
        albumInteractor.askForPermissions();
    }




    public void pushRatingFirebase(long timeStamp, float rating) {
        albumInteractor.pushRatingFirebase(timeStamp, rating);
    }

}
