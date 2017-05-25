package album;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import managers.CameraManager;
import pojo.CardImage;
import pojo.ImageInfo;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by Pablo on 7/4/17.
 */

public interface AlbumInteractor {

    void openCamera();

    void openGallery();
    
    void pushToFirebase(int requestCode, int resultCode, String comentary);

    void OnActivityResult(int requestCode, int resultCode, Intent data);

    void askForPermissions();

    void loadImageInfo();

    void pushRatingFirebase(long timeStamp, float rating);

    interface OnCameraCapture {
        void onSuccess(Bitmap imageBitmap);
        void onError();
        void startActivityForResult(Intent takePictureIntent, int requestCameraCapture);
        void hideLoading();
    }

    interface OnRenderImages{
        void notifyAdapter(List<CardImage> cardList);
    }

    /**
     * Created by Pablo on 7/4/17.
     */

    class AlbumInteractorImp implements AlbumInteractor {

      private CameraManager cameraManager;
      private OnCameraCapture onCameraCapturePresenter;
      private OnRenderImages onRenderImagesPresenter;
      private FragmentActivity activity;
      private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 111;
      private DatabaseReference databaseReference;
      private FirebaseAuth firebaseAuth;
      private List<CardImage> cardList;

      public AlbumInteractorImp(OnCameraCapture onCameraCapturePresenter, OnRenderImages onRenderImagesPresenter, FragmentActivity activity) {
        this.onRenderImagesPresenter = onRenderImagesPresenter;
        this.onCameraCapturePresenter = onCameraCapturePresenter;
        this.activity = activity;
        init();

      }

      private void init() {
        cameraManager = new CameraManager(this.activity, onCameraCapture);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        cardList = new ArrayList<>();
      }

      final OnCameraCapture onCameraCapture = new OnCameraCapture() {
        @Override public void onSuccess(Bitmap imageBitmap) {
          onCameraCapturePresenter.onSuccess(imageBitmap);
        }

        @Override public void onError() {

        }

        @Override
        public void startActivityForResult(Intent takePictureIntent, int requestCameraCapture) {
          onCameraCapturePresenter.startActivityForResult(takePictureIntent, requestCameraCapture);
        }

        @Override public void hideLoading() {
          onCameraCapturePresenter.hideLoading();
        }
      };

      @Override public void openCamera() {
        cameraManager.takePictureIntent();
      }

      @Override public void openGallery() {
        cameraManager.openGalleryIntent();
      }

      @Override public void pushToFirebase(int requestCode, int resultCode, String comentary) {
        cameraManager.pushToFirebase(requestCode, resultCode, comentary);
      }

      @Override public void OnActivityResult(int requestCode, int resultCode, Intent data) {
        cameraManager.OnActivityResult(requestCode, resultCode, data);
      }

      @Override public void askForPermissions() {
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
                new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
          }
        }
      }

      @Override public void loadImageInfo() {
        // storageReference = FirebaseStorage.getInstance().getReference().child("Images").child(FirebaseAuth.getInstance().getCurrentUser().toString());

        System.out.print("user " + firebaseAuth.getCurrentUser().getUid());
        final ArrayList<ImageInfo> imageArray = new ArrayList<>();

        databaseReference.getRoot().child("images").addValueEventListener(new ValueEventListener() {
          @Override public void onDataChange(DataSnapshot dataSnapshot) {
            imageArray.clear();
            cardList.clear();
            for (DataSnapshot imageSnapshot : dataSnapshot.getChildren()) {
              Log.d(TAG, "onDataChange: " + imageSnapshot.getKey());
              ImageInfo image = imageSnapshot.getValue(ImageInfo.class);
              imageArray.add(image);
            }
            render(imageArray);
          }

          @Override public void onCancelled(DatabaseError databaseError) {

          }
        });
      }

      @Override public void pushRatingFirebase(final long timeStamp, final float rating) {
        //Toast.makeText(getContext(), "" + timeStamp, Toast.LENGTH_SHORT).show();
        databaseReference.getRoot().child("images").child(String.valueOf(timeStamp)).addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            ImageInfo image = dataSnapshot.getValue(ImageInfo.class);
            //long time = image.getTimeStamp();
            float currentRating = image.getRating();
            int votes = image.getVoted();
            votes= votes+1;
            currentRating =currentRating + rating;
            image.setRating(currentRating);
            image.setVoted(votes);
            Map<String, Object> map = new HashMap<>();
            map.put(String.valueOf(timeStamp),image);
            databaseReference.child("images").updateChildren(map);
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
        });

      }

      private void render(ArrayList<ImageInfo> imgInfo) {

        CardImage card;
        for (ImageInfo imageInfo : imgInfo) {
          card = new CardImage(imageInfo.getName(), imageInfo.getRating(), imageInfo.getUrl(), imageInfo.getProvince(), imageInfo.getTimeStamp(), imageInfo.getVoted());
          cardList.add(card);
        }

        onRenderImagesPresenter.notifyAdapter(cardList);


      }
    }
}
