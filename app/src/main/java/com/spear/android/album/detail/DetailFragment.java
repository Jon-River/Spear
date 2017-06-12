package com.spear.android.album.detail;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.spear.android.R;
import com.spear.android.album.AlbumActivity;
import com.spear.android.custom.CustomTypeFace;
import com.spear.android.pojo.GalleryCard;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements View.OnClickListener {

    public RatingBar ratingBar;
    public ImageView image;
    public Button btnSubmitRating, btnSkip;
    public int votes;
    public float rating;
    public long timeStamp;
    private static final int hideFragment = 0;
    private static final int appImageCard = 11111111;

    private AlbumActivity view;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        init(v);
        return v;
    }

    private void init(View v) {
        view = (AlbumActivity)getActivity();
        image = (ImageView)  v.findViewById(R.id.image);
        ratingBar = (RatingBar)  v.findViewById(R.id.ratingBar);
        btnSubmitRating = (Button)  v.findViewById(R.id.btnSubmitRating);
        btnSkip = (Button) v.findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(this);


        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"Libel_Suit.ttf");

        SpannableStringBuilder typeFaceAction = new SpannableStringBuilder("Detail");
        typeFaceAction.setSpan (new CustomTypeFace("", type), 0, typeFaceAction.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        btnSubmitRating.setTypeface(type);
        btnSkip.setTypeface(type);
        btnSubmitRating.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.btnSubmitRating){
            //Toast.makeText(this, "rating" + name.getText().toString()+  " "+ timeStamp, Toast.LENGTH_SHORT).show();
            rating = ratingBar.getRating();

            view.pushRatingToFirebase(timeStamp, rating);

        }else if (v.getId()== R.id.btnSkip){
           view.cambiarFragment(hideFragment);


        }
    }


    public void setModel(GalleryCard model){
        Glide.with(this).load(model.getUrlString()).into(image);
        votes = model.getVotes();
        rating = model.getRating() / votes;
        ratingBar.setRating(rating);
        timeStamp = model.getTimeStamp();
        if (timeStamp == appImageCard){
            btnSubmitRating.setEnabled(false);
            ratingBar.setEnabled(false);
        }else{
            btnSubmitRating.setEnabled(true);
            ratingBar.setEnabled(true);
        }
    }

    public void setDetailRating(float currentRating) {
        ratingBar.setRating(currentRating);
    }
}
