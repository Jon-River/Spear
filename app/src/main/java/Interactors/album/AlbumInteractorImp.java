package Interactors.album;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import managers.CameraManager;

/**
 * Created by Pablo on 7/4/17.
 */

public class AlbumInteractorImp implements AlbumInteractor {


    private CameraManager cameraManager;
    private OnCameraCapture onCameraCapturePresenter;
    FragmentActivity activity;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 111;

    public AlbumInteractorImp(OnCameraCapture onCameraCapturePresenter, FragmentActivity activity) {
        this.onCameraCapturePresenter = onCameraCapturePresenter;
        this.activity = activity;
        cameraManager = new CameraManager(this.activity,onCameraCapture);

    }

    final AlbumInteractor.OnCameraCapture onCameraCapture = new OnCameraCapture() {
        @Override
        public void onSuccess(Bitmap imageBitmap) {
            onCameraCapturePresenter.onSuccess(imageBitmap);
        }

        @Override
        public void onError() {

        }

        @Override
        public void startActivityForResult(Intent takePictureIntent, int requestCameraCapture) {
            onCameraCapturePresenter.startActivityForResult(takePictureIntent, requestCameraCapture);
        }

    };



    @Override
    public void openCamera() {
        cameraManager.takePictureIntent();
    }

    @Override
    public void openGallery() {
        cameraManager.openGalleryIntent();
    }

    @Override
    public void pushToFirebase(int requestCode, int resultCode, String comentary) {
        cameraManager.pushToFirebase(requestCode,resultCode,comentary);
    }

    @Override
    public void OnActivityResult(int requestCode, int resultCode, Intent data) {
        cameraManager.OnActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void askForPermissions() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


}
