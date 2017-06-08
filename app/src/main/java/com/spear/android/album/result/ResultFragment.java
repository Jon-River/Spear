package com.spear.android.album.result;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.spear.android.R;
import com.spear.android.album.AlbumActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment implements View.OnClickListener {

    private ImageView image;
    private Button btnPushToFirebase;
    private AlbumActivity view;
    private int requestCode, resultCode;
    private EditText etComentary;


    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_result, container, false);
        ;
        init(v);
        return v;
    }

    private void init(View v) {
        view = (AlbumActivity) getActivity();
        image = (ImageView) v.findViewById(R.id.imageObtained);
        btnPushToFirebase = (Button) v.findViewById(R.id.btnPushToFirebase);
        btnPushToFirebase.setOnClickListener(this);
        etComentary = (EditText) v.findViewById(R.id.etComentary);
    }

    public void setImageBitmap(Bitmap imageBitmap, int requestCode, int resultCode) {
        image.setImageBitmap(imageBitmap);
        this.requestCode = requestCode;
        this.resultCode = resultCode;

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPushToFirebase) {
            view.showLoading();
            view.pushTofirebase(image,requestCode,resultCode,etComentary.getText().toString());
        }
    }
}
