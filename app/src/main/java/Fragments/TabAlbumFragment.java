package Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spear.android.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapters.AlbumAdapter;
import Interactors.AlbumInteractorImp;
import Interfaces.AlbumInteractor;
import Managers.CameraManager;
import Objects.CardImage;
import Objects.ImageInfo;
import Objects.UserInfo;

import static com.google.android.gms.internal.zzt.TAG;

/**
 * A simple {@link Fragment} subclass.
 */

public class TabAlbumFragment extends Fragment implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 111;
    private AlbumInteractor albumInteractor;

    private RecyclerView recyclerView;
    private AlbumAdapter adapter;
    private List<CardImage> cardList;
    private CameraManager cameraManager;
    private FloatingActionButton fabOpenCamera;
    private ImageView mImageView;
    private EditText editTextComentary;
    private Button btnOpenCamera, btnOpenGallery, btnUploadImage;
    private Dialog dialogButtons, dialogCameraView;
    private static final int CAMERA_REQUEST = 1888;
    private static int TAKE_PICTURE = 1;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private Uri url;
    private RatingBar ratingBar;

    public TabAlbumFragment() {
        // Required empty public constructor
        albumInteractor = new AlbumInteractorImp(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_album, container, false);
        init(v);
        prepareAlbums();

        return v;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.fabOpenCamera) {
            dialogButtons.show();
        } else if (view.getId() == R.id.btnOpenCamera) {
            cameraManager.takePictureIntent();
            dialogButtons.dismiss();
        } else if (view.getId() == R.id.btnOpenGallery) {
            cameraManager.openGalleryIntent();
            dialogButtons.dismiss();
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        askPermissions();
        super.onActivityResult(requestCode, resultCode, data);
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comentary = editTextComentary.getText().toString();
                cameraManager.pushToFirebase(requestCode, resultCode, comentary);
                dialogCameraView.dismiss();
            }
        });

        if (resultCode == Activity.RESULT_OK) {
            cameraManager.OnActivityResult(requestCode, resultCode, data, mImageView);
            dialogCameraView.show();
        }
    }

    public void askPermissions() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void prepareAlbums() {

        // storageReference = FirebaseStorage.getInstance().getReference().child("Images").child(FirebaseAuth.getInstance().getCurrentUser().toString());

        System.out.print("user " + firebaseAuth.getCurrentUser().getUid());
        final ArrayList<ImageInfo> imageArray = new ArrayList<>();


        databaseReference.getRoot().child("images")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        imageArray.clear();
                        cardList.clear();
                        for (DataSnapshot imageSnapshot : dataSnapshot.getChildren()) {
                            Log.d(TAG, "onDataChange: " + imageSnapshot.getKey());
                            ImageInfo image = imageSnapshot.getValue(ImageInfo.class);
                            imageArray.add(image);

                        }
                        render(imageArray);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });



  /*  int[] covers = new int[] {
        R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
        R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
        R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher
    };

    CardImage a;
    for (int cover : covers) {
      a = new CardImage("usuario1", 3, cover, "madrid");
      cardList.add(a);
    }*/

    }

    private void render(ArrayList<ImageInfo> imgInfo) {
        final ArrayList<UserInfo> userArray = new ArrayList<>();


        CardImage card;
        for (ImageInfo imageInfo : imgInfo) {
            card = new CardImage(imageInfo.getName(), imageInfo.getRating(), imageInfo.getUrl(), imageInfo.getProvince(), imageInfo.getTimeStamp(), imageInfo.getVoted());
            cardList.add(card);
        }


        adapter.notifyDataSetChanged();

    }


    private void init(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        cardList = new ArrayList<>();
        adapter = new AlbumAdapter(this, getContext(), cardList);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        cameraManager = new CameraManager(getActivity(), this);
        fabOpenCamera = (FloatingActionButton) v.findViewById(R.id.fabOpenCamera);
        fabOpenCamera.setOnClickListener(this);

        dialogButtons = new Dialog(getContext());
        dialogButtons.setContentView(R.layout.open_camera_dialog);
        btnOpenCamera = (Button) dialogButtons.findViewById(R.id.btnOpenCamera);
        btnOpenGallery = (Button) dialogButtons.findViewById(R.id.btnOpenGallery);
        btnOpenCamera.setOnClickListener(this);
        btnOpenGallery.setOnClickListener(this);
        dialogCameraView = new Dialog(getContext());
        dialogCameraView.setContentView(R.layout.result_camera_dialog);
        mImageView = (ImageView) dialogCameraView.findViewById(R.id.imageResultDialog);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = ((display.getWidth() * 8) / 10);
        int height = ((display.getHeight() * 10) / 10);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
        mImageView.setLayoutParams(parms);
        btnUploadImage = (Button) dialogCameraView.findViewById(R.id.btnUploadPhoto);
        editTextComentary = (EditText) dialogCameraView.findViewById(R.id.editTextComentary);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        

    }

    public void pushRatingFirebase(final long timeStamp, final float rating) {
        //Toast.makeText(getContext(), "" + timeStamp, Toast.LENGTH_SHORT).show();
        databaseReference.getRoot().child("images").child(String.valueOf(timeStamp)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ImageInfo image = (ImageInfo) dataSnapshot.getValue(ImageInfo.class);
                long time = image.getTimeStamp();
                float currentRating = image.getRating();
                int votes = image.getVoted();
                votes= votes+1;
                currentRating =currentRating + rating;
                float newRating = currentRating / votes;
                image.setRating(currentRating);
                image.setVoted(votes);
                Map <String, Object> map = new HashMap<String, Object>();
                map.put(String.valueOf(timeStamp),image);
                databaseReference.child("images").updateChildren(map);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        Toast.makeText(getContext(), "timestamp"+ timeStamp, Toast.LENGTH_SHORT).show();
    }


    //region "Card decoration"

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing
                        - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right =
                        (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing
                        - (column + 1) * spacing
                        / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    //endregion
}

