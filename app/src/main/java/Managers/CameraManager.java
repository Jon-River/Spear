package managers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.spear.android.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Fragments.album.AlbumFragment;
import objects.ImageInfo;
import objects.UserInfo;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Pablo on 16/4/17.
 */

public class CameraManager {

    public static final int REQUEST_CAMERA_CAPTURE = 1111;
    public static final int REQUEST_GALLERY_CAPTURE = 2222;

    private Activity context;
    private String mCurrentPhotoPath;
    private AlbumFragment fragment;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private Intent dataIntent;
    private ImageInfo imageInfo;
    private String url;
    private String province;
    private String name;


    public CameraManager(Activity context, AlbumFragment fragment) {
        this.context = context;
        this.fragment = fragment;
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        getDataUser();
    }

    public void takePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, "data");
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            fragment.startActivityForResult(takePictureIntent, REQUEST_CAMERA_CAPTURE);
        }
    }

    public void openGalleryIntent() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        fragment.startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CAPTURE);
    }

    public void OnActivityResult(int requestCode, int resultCode, Intent data, ImageView imageView) {

        dataIntent = data;
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == REQUEST_CAMERA_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
            } else if (requestCode == REQUEST_GALLERY_CAPTURE
                    && resultCode == RESULT_OK
                    && null != data) {
                try {
                    imageView.setImageBitmap(
                            MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData()));
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

    //Add a photo to a gallery
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public void pushToFirebase(int requestCode, int resultCode, final String comentary) {

        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Uploading image");
        progress.show();
        progress.setContentView(R.layout.custom_progress_dialog);

        if (resultCode != RESULT_CANCELED) {

            if (requestCode == REQUEST_CAMERA_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = dataIntent.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path =
                        MediaStore.Images.Media.insertImage(context.getContentResolver(), imageBitmap, "Title",
                                null);
                final Uri uri = Uri.parse(path);
                StorageReference storageRef = storageReference.child("Images")
                        .child(firebaseAuth.getCurrentUser().getUid());

                storageRef.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @SuppressWarnings("VisibleForTests")
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                url = taskSnapshot.getDownloadUrl().toString();
                                long milis = System.currentTimeMillis();

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
                                progress.dismiss();
                            }
                        });
            } else if (requestCode == REQUEST_GALLERY_CAPTURE && resultCode == RESULT_OK) {
                final Uri uri = dataIntent.getData();
                StorageReference storageRef = storageReference.child("Images")
                        .child(firebaseAuth.getCurrentUser().getUid())
                        .child(uri.getLastPathSegment());

                storageRef.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @SuppressWarnings("VisibleForTests")
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                url = taskSnapshot.getDownloadUrl().toString();
                                long milis = System.currentTimeMillis();

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
                                progress.dismiss();
                            }
                        });
            }
        }
    }

}

class MyFileContentProvider extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse
            ("content://com.spear.android/");
    private static final HashMap<String, String> MIME_TYPES =
            new HashMap<String, String>();

    static {
        MIME_TYPES.put(".jpg", "image/jpeg");
        MIME_TYPES.put(".jpeg", "image/jpeg");
    }

    @Override
    public boolean onCreate() {

        try {
            File mFile = new File(getContext().getFilesDir(), "newImage.jpg");
            if (!mFile.exists()) {
                mFile.createNewFile();
            }
            getContext().getContentResolver().notifyChange(CONTENT_URI, null);
            return (true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        String path = uri.toString();

        for (String extension : MIME_TYPES.keySet()) {
            if (path.endsWith(extension)) {
                return (MIME_TYPES.get(extension));
            }
        }
        return (null);
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode)
            throws FileNotFoundException {

        File f = new File(getContext().getFilesDir(), "newImage.jpg");
        if (f.exists()) {
            return (ParcelFileDescriptor.open(f,
                    ParcelFileDescriptor.MODE_READ_WRITE));
        }
        throw new FileNotFoundException(uri.getPath());
    }
}
