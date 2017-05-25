package album;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;

import java.util.List;

import pojo.CardImage;

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
        albumInteractor = new AlbumInteractor.AlbumInteractorImp(onCameraCapture, onRenderImages,  activity) ;
    }


    final AlbumInteractor.OnRenderImages onRenderImages = new AlbumInteractor.OnRenderImages() {
        @Override public void notifyAdapter(List<CardImage> cardList) {
           view.notifyAdapter();
        }
    };

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

        @Override public void hideLoading() {
            view.hideLoading();
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

    public void loadImageInfo() {
        albumInteractor.loadImageInfo();
    }

    public void pushRatingFirebase(long timeStamp, float rating) {
        albumInteractor.pushRatingFirebase(timeStamp, rating);
    }
}
