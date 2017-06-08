package com.spear.android.managers;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.spear.android.album.AlbumView;

import java.io.IOException;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Pablo on 16/4/17.
 */

public class CameraManager {

    public static final int REQUEST_CAMERA_CAPTURE = 1111;
    public static final int REQUEST_GALLERY_CAPTURE = 2222;

    private AlbumView.OnCameraCapture onCameraCapture;
    private Activity activity;
    private Uri imageUri;


    public CameraManager(Activity activity, AlbumView.OnCameraCapture onCameraCapture) {
        this.activity = activity;
        this.onCameraCapture = onCameraCapture;
    }


    public void openCameraIntent() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "image");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = activity.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        if (openCameraIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(openCameraIntent, REQUEST_CAMERA_CAPTURE);
        }

    }

    public void openGalleryIntent() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CAPTURE);

    }

    public void proccessImage(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == REQUEST_CAMERA_CAPTURE && resultCode == RESULT_OK) {
                try {
                    Bitmap image = MediaStore.Images.Media.getBitmap(
                            activity.getContentResolver(), imageUri);
                    onCameraCapture.onSuccess(image, requestCode, resultCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (requestCode == REQUEST_GALLERY_CAPTURE
                    && resultCode == RESULT_OK
                    && null != data) {
                try {
                    onCameraCapture.onSuccess(MediaStore.Images.Media.getBitmap(activity.getContentResolver(), data.getData()), requestCode, resultCode);

                } catch (IOException ex) {
                    Log.v("IOException", "" + ex.getMessage());
                }
            }
        }
    }



}

