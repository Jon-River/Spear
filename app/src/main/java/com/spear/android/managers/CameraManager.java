package com.spear.android.managers;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.spear.android.album.AlbumInteractor;
import com.spear.android.pojo.ImageInfo;
import com.spear.android.pojo.UserInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Pablo on 16/4/17.
 */

public class CameraManager {

    public static final int REQUEST_CAMERA_CAPTURE = 1111;
    public static final int REQUEST_GALLERY_CAPTURE = 2222;

    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private Intent dataIntent;
    private ImageInfo imageInfo;
    private String url;
    private String province;
    private String name;
    private AlbumInteractor.OnCameraCapture onCameraCapture;
    private Activity activity;
    private Uri imageUri;


    public CameraManager(Activity activity, AlbumInteractor.OnCameraCapture onCameraCapture) {
        this.activity = activity;
        this.onCameraCapture = onCameraCapture;
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        getDataUser();
    }

    public void takePictureIntent() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "image");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = activity.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        if (intent.resolveActivity(activity.getPackageManager())!= null){
            onCameraCapture.startActivityForResult(intent, REQUEST_CAMERA_CAPTURE);
        }

    }

    public void openGalleryIntent() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        onCameraCapture.startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CAPTURE);

    }

    public void OnActivityResult(int requestCode, int resultCode, Intent data) {

        dataIntent = data;
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == REQUEST_CAMERA_CAPTURE && resultCode == RESULT_OK) {
                try {
                    Bitmap image = MediaStore.Images.Media.getBitmap(
                            activity.getContentResolver(), imageUri);
                    onCameraCapture.onSuccess(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }

               /* Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                onCameraCapture.onSuccess(imageBitmap);*/
            } else if (requestCode == REQUEST_GALLERY_CAPTURE
                    && resultCode == RESULT_OK
                    && null != data) {
                try {
                    onCameraCapture.onSuccess(MediaStore.Images.Media.getBitmap(activity.getContentResolver(), data.getData()));

                } catch (IOException ex) {
                    Log.v("IOException", "" + ex.getMessage());
                }
            }
        }
    }

    private void getDataUser() {
        databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                UserInfo user = dataSnapshot.getValue(UserInfo.class);
                province = user.getProvince();
                name = user.getName();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void pushToFirebase(int requestCode, int resultCode, final String comentary) {

        if (resultCode != RESULT_CANCELED) {

            if (requestCode == REQUEST_CAMERA_CAPTURE && resultCode == RESULT_OK) {

                try {
                    Bitmap  imageBitmap= MediaStore.Images.Media.getBitmap(
                            activity.getContentResolver(), imageUri);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    final long milis = System.currentTimeMillis();
                    String path =
                            MediaStore.Images.Media.insertImage(activity.getContentResolver(), imageBitmap, "Title",
                                    null);
                    final Uri uri = Uri.parse(path);
                    StorageReference storageRef = storageReference.child("Images").child(String.valueOf(milis));

                    storageRef.putFile(uri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @SuppressWarnings("VisibleForTests")
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    url = taskSnapshot.getDownloadUrl().toString();


                                    imageInfo = new ImageInfo(firebaseAuth.getCurrentUser().getEmail(), 0, milis, comentary, url, 0, province, name);
                                    DatabaseReference dataref = databaseReference.child("/images/").child(String.valueOf(milis));
                                    String key = dataref.getKey();
                                    dataref.setValue(imageInfo);
                                    //databaseReference.child("/images/").push().setValue(imageInfo);


                                    databaseReference.child("users")
                                            .child(firebaseAuth.getCurrentUser().getUid()).child("images").child(key)
                                            .setValue(url);
                                    ArrayList<String> urlArray = new ArrayList<String>();
                                    urlArray.add(url);
                                    onCameraCapture.hideLoading();
                                }
                            });


                } catch (Exception e) {
                    e.printStackTrace();
                }



            } else if (requestCode == REQUEST_GALLERY_CAPTURE && resultCode == RESULT_OK) {
                final Uri uri = dataIntent.getData();
                final long milis = System.currentTimeMillis();
                StorageReference storageRef = storageReference.child("Images").child(String.valueOf(milis));


                storageRef.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @SuppressWarnings("VisibleForTests")
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                url = taskSnapshot.getDownloadUrl().toString();
                                imageInfo = new ImageInfo(firebaseAuth.getCurrentUser().getEmail(), 0, milis, comentary, url, 0, province, name);
                                DatabaseReference dataref = databaseReference.child("/images/").child(String.valueOf(milis));
                                String key = dataref.getKey();
                                dataref.setValue(imageInfo);
                                //databaseReference.child("/images/").push().setValue(imageInfo);


                                databaseReference.child("users")
                                        .child(firebaseAuth.getCurrentUser().getUid()).child("images").child(key)
                                        .setValue(url);
                                ArrayList<String> urlArray = new ArrayList<String>();
                                urlArray.add(url);
                                onCameraCapture.hideLoading();
                            }
                        });
            }
        }
    }

}

