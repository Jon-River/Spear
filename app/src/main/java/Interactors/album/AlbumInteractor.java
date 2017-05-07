package Interactors.album;

import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by Pablo on 7/4/17.
 */

public interface AlbumInteractor {

    void openCamera();

    void openGallery();
    
    void pushToFirebase(int requestCode, int resultCode, String comentary);

    void OnActivityResult(int requestCode, int resultCode, Intent data);

    void askForPermissions();

    interface OnCameraCapture {
        void onSuccess(Bitmap imageBitmap);
        void onError();
        void startActivityForResult(Intent takePictureIntent, int requestCameraCapture);

    }

}
