package com.spear.android.album.galleryoption;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.spear.android.R;
import com.spear.android.album.AlbumActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryOptionFragment extends Fragment implements View.OnClickListener {

    private Button btnSkip;
    private ImageButton btnOpenCamera, btnOpenGallery;
    private TextView txtTittle, txtCamera, txtGallery;
    private AlbumActivity view;
    final int hideFragment = 0;


    public GalleryOptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_load_option, container, false);
        init(v);
        return v;
    }

    private void init(View v) {

        Typeface typeLibel = Typeface.createFromAsset(getActivity().getAssets(), "Libel_Suit.ttf");
        view = (AlbumActivity) getActivity();
        btnOpenCamera = (ImageButton) v.findViewById(R.id.btnOpenCamera);
        btnOpenGallery = (ImageButton) v.findViewById(R.id.btnOpenGallery);
        btnSkip = (Button) v.findViewById(R.id.btnSkip);
        btnOpenCamera.setOnClickListener(this);
        btnOpenGallery.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        txtTittle = (TextView) v.findViewById(R.id.textTittle);
        txtCamera = (TextView) v.findViewById(R.id.txtCamera);
        txtGallery = (TextView) v.findViewById(R.id.txtGallery);
        txtTittle.setTypeface(typeLibel);
        txtCamera.setTypeface(typeLibel);
        txtGallery.setTypeface(typeLibel);
        btnSkip.setTypeface(typeLibel);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnOpenCamera) {
            view.openCamera();
        } else if (v.getId() == R.id.btnOpenGallery) {
            view.openGallery();
        } else if (v.getId() == R.id.btnSkip) {
            view.cambiarFragment(hideFragment);
        }
    }
}
