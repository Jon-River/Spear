package com.spear.android.album;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

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
import com.spear.android.pojo.GalleryCard;
import com.spear.android.pojo.ImageInfo;
import com.spear.android.pojo.UserInfo;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.spear.android.managers.CameraManager.REQUEST_CAMERA_CAPTURE;
import static com.spear.android.managers.CameraManager.REQUEST_GALLERY_CAPTURE;

/**
 * Created by Pablo on 30/5/17.
 */

public class AlbumInteractorImp implements AlbumInteractor {

    private Activity activity;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 111;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private List<GalleryCard> cardList;
    private String url, name;
    AlbumView.OnPushRatingToFirebase onPushRating;
    OnPushImage onPushImage;

    public AlbumInteractorImp(Activity activity, AlbumView.OnPushRatingToFirebase onPushRating, OnPushImage onPushImage) {
        this.onPushRating = onPushRating;
        this.onPushImage = onPushImage;
        this.activity = activity;

        init();
        getDataUser();

    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        cardList = new ArrayList<>();
        storageReference = FirebaseStorage.getInstance().getReference();


    }

    private void getDataUser() {
        databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                UserInfo user = dataSnapshot.getValue(UserInfo.class);

                name = user.getName();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    @Override
    public void pushToFirebase(Intent galleryIntent, ImageView image, int requestCode, int resultCode, final String comentary) {
        if (resultCode != RESULT_CANCELED) {

            if (requestCode == REQUEST_CAMERA_CAPTURE && resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    final long milis = System.currentTimeMillis();
                    String path =
                            MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, "Title",
                                    null);
                    final Uri uri = Uri.parse(path);
                    StorageReference storageRef = storageReference.child("Images").child(String.valueOf(milis));

                    storageRef.putFile(uri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @SuppressWarnings("VisibleForTests")
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    url = taskSnapshot.getDownloadUrl().toString();

                                    ImageInfo imageInfo = new ImageInfo(firebaseAuth.getCurrentUser().getEmail(), 0, milis, comentary, url, 0, name);
                                    DatabaseReference dataref = databaseReference.child("/images/").child(String.valueOf(milis));
                                    String key = dataref.getKey();
                                    dataref.setValue(imageInfo);

                                    databaseReference.child("users")
                                            .child(firebaseAuth.getCurrentUser().getUid()).child("images").child(key)
                                            .setValue(url);
                                    onPushImage.OnSuccess();

                                }
                            });


                } catch (Exception e) {
                    e.printStackTrace();
                    onPushImage.OnError(e.toString());
                }


            } else if (requestCode == REQUEST_GALLERY_CAPTURE && resultCode == RESULT_OK) {
                final Uri uri = galleryIntent.getData();
                final long milis = System.currentTimeMillis();
                StorageReference storageRef = storageReference.child("Images").child(String.valueOf(milis));
                storageRef.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @SuppressWarnings("VisibleForTests")
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                url = taskSnapshot.getDownloadUrl().toString();
                                ImageInfo imageInfo = new ImageInfo(firebaseAuth.getCurrentUser().getEmail(), 0, milis, comentary, url, 0, name);
                                DatabaseReference dataref = databaseReference.child("/images/").child(String.valueOf(milis));
                                String key = dataref.getKey();
                                dataref.setValue(imageInfo);


                                databaseReference.child("users")
                                        .child(firebaseAuth.getCurrentUser().getUid()).child("images").child(key)
                                        .setValue(url);
                                ArrayList<String> urlArray = new ArrayList<String>();
                                urlArray.add(url);
                                onPushImage.OnSuccess();

                            }
                        });
            }
        }
    }


    @Override
    public void pushRatingFirebase(final long timeStamp, final float rating) {
        databaseReference.getRoot().child("images").child(String.valueOf(timeStamp)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ImageInfo image = dataSnapshot.getValue(ImageInfo.class);
                //long time = newsImage.getTimeStamp();
                float currentRating = image.getRating();
                int votes = image.getVoted();
                votes = votes + 1;
                currentRating = currentRating + rating;
                image.setRating(currentRating);
                image.setVoted(votes);
                Map<String, Object> map = new HashMap<>();
                map.put(String.valueOf(timeStamp), image);
                databaseReference.child("images").updateChildren(map);
                float mediumRating = currentRating / (float) votes;
                onPushRating.OnSucces(mediumRating);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}