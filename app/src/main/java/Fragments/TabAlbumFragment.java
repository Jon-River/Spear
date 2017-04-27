package Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.spear.android.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.AlbumAdapter;
import Interactors.AlbumInteractorImp;
import Interfaces.AlbumInteractor;
import Managers.CameraManager;
import Objects.CardImage;

/**
 * A simple {@link Fragment} subclass.
 */


public class TabAlbumFragment extends Fragment implements View.OnClickListener {

    private AlbumInteractor albumInteractor;

    private RecyclerView recyclerView;
    private AlbumAdapter adapter;
    private List<CardImage> albumList;
    private CameraManager cameraManager;
    private FloatingActionButton fabOpenCamera;
    private ImageView mImageView;
    private EditText editTextComentary;
    private Button btnOpenCamera, btnOpenGallery, btnUploadImage;
    private Dialog dialogButtons, dialogCameraView;
    private static final int CAMERA_REQUEST = 1888;
    private static int TAKE_PICTURE = 1;


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
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Intent intent = data;
        dialogCameraView = new Dialog(getContext());
        dialogCameraView.setContentView(R.layout.result_camera_dialog);
        mImageView = (ImageView) dialogCameraView.findViewById(R.id.imageResultDialog);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = ((display.getWidth() * 8) / 10);
        int height = ((display.getHeight() * 10) / 10);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
        mImageView.setLayoutParams(parms);
        btnUploadImage = (Button) dialogCameraView.findViewById(R.id.btnUploadPhoto);

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraManager.pushToFirebase(intent);
            }
        });

        if (resultCode == Activity.RESULT_OK) {
            cameraManager.OnActivityResult(requestCode, resultCode, data, mImageView);
            dialogCameraView.show();
        }
    }





    private void prepareAlbums() {
        int[] covers = new int[]{
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher
        };

        CardImage a;
        for (int cover : covers) {
            a = new CardImage("usuario1", 3, cover, "madrid");
            albumList.add(a);
        }

        adapter.notifyDataSetChanged();
    }

    private void init(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumAdapter(getContext(), albumList);

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
        btnOpenGallery = (Button) dialogButtons.findViewById(R.id.btnOpenCamera);
        editTextComentary = (EditText) dialogButtons.findViewById(R.id.editTextComentary);
        btnOpenCamera.setOnClickListener(this);
        btnOpenGallery.setOnClickListener(this);



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

