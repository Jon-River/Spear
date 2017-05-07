package Fragments.album;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;

import Interactors.album.AlbumInteractor;
import Interactors.album.AlbumInteractorImp;

/**
 * Created by Pablo on 7/5/17.
 */

public class AlbumPresenter {

    private AlbumFragment view;
    private AlbumInteractor albumInteractor;
    private FragmentActivity activity;

    public AlbumPresenter(AlbumFragment view, FragmentActivity activity) {
        this.view = view;
        this.activity = activity;
        albumInteractor = new AlbumInteractorImp(onCameraCapture, activity) ;
    }


    final AlbumInteractor.OnCameraCapture onCameraCapture = new AlbumInteractor.OnCameraCapture() {
        @Override
        public void onSuccess(Bitmap imageBitmap) {
            view.setImageBitmap(imageBitmap);
        }

        @Override
        public void onError() {

        }

        @Override
        public void startActivityForResult(Intent takePictureIntent, int requestCameraCapture) {
            view.startActivityForResult(takePictureIntent,requestCameraCapture);
        }
    };
    public void openCamera() {
        albumInteractor.openCamera();

    }

    public void pushTofirebase(int requestCode, int resultCode, String comentary) {
        albumInteractor.pushToFirebase(requestCode, resultCode, comentary);
    }

    public void OnActivityResult(int requestCode, int resultCode, Intent data) {
        albumInteractor.OnActivityResult(requestCode,resultCode,data);
    }

    public void askForPermissions() {
        albumInteractor.askForPermissions();
    }

    public void openGallery() {
        albumInteractor.openGallery();
    }
}
