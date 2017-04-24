package Managers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.spear.android.R;
import java.io.ByteArrayOutputStream;
import java.io.File;

import Fragments.TabAlbumFragment;
import java.io.IOException;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Pablo on 16/4/17.
 */

public class CameraManager {

  private Activity context;
  public static final int REQUEST_IMAGE_CAPTURE = 1111;
  public static final int RESULT_LOAD_IMAGE = 2222;

  String mCurrentPhotoPath;
  TabAlbumFragment fragment;

  public CameraManager(Activity context, TabAlbumFragment fragment) {
    this.context = context;
    this.fragment = fragment;
  }

  public CameraManager() {
  }

  public void takePictureIntent() {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
      fragment.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }
  }

  public void openGalleryIntent() {
    Intent openGalleryIntent = new Intent(Intent.ACTION_PICK,
        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    fragment.startActivityForResult(openGalleryIntent,RESULT_LOAD_IMAGE  );
  }

  public void OnActivityResult(int requestCode, int resultCode, Intent data, ImageView imageView) {

    if (resultCode != RESULT_CANCELED) {
      if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");

        //Load image with glide
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Glide.with(context).load(stream.toByteArray()).asBitmap().into(imageView);

      }else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data){


        try{
          imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData()));
        }catch (IOException ex){
          Log.v("IOException",""+ ex.getMessage());
        }

       /* ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Glide.with(context).load(stream.toByteArray()).asBitmap().into(imageView);*/
      }
    }

  }

  //Add a photo to a gallery
  private void galleryAddPic() {
    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
    File f = new File(mCurrentPhotoPath);
    Uri contentUri = Uri.fromFile(f);
    mediaScanIntent.setData(contentUri);
    context.sendBroadcast(mediaScanIntent);
  }

  //    Decode a Scaled Image
  /*  private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }*/
}
