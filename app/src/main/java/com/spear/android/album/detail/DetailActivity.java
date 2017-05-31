package com.spear.android.album.detail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.spear.android.R;

import com.spear.android.pojo.CardImage;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView name, province;
    public RatingBar ratingBar;
    public ImageView image;
    public Button btnSubmitRating;
    public int votes;
    public float rating;
    public long timeStamp;
    DetailPresenter detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        CardImage model = (CardImage) getIntent().getSerializableExtra("Editing");
        Toast.makeText(this, ""+ model.getUrlString()+ "" + model.getUsername(), Toast.LENGTH_SHORT).show();
        detailPresenter = new DetailPresenter(this);

        name = (TextView) findViewById(R.id.username);
        province = (TextView) findViewById(R.id.province);
        image = (ImageView) findViewById(R.id.image);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnSubmitRating = (Button) findViewById(R.id.btnSubmitRating);

        Typeface type = Typeface.createFromAsset(getAssets(),"OpenSans-Regular.ttf");
        name.setTypeface(type);
        province.setTypeface(type);
        btnSubmitRating.setTypeface(type);
        btnSubmitRating.setOnClickListener(this);

        name.setText(model.getUsername());
        province.setText(model.getProvince());
        Glide.with(this).load(model.getUrlString()).into(image);
        votes = model.getVotes();
        rating = model.getRating() / votes;
        ratingBar.setRating(rating);
        timeStamp = model.getTimeStamp();



    }

    @Override
    public void onClick(View view) {
        if (view.getId()== R.id.btnSubmitRating){
            //Toast.makeText(this, "rating" + name.getText().toString()+  " "+ timeStamp, Toast.LENGTH_SHORT).show();
            rating = ratingBar.getRating();
            detailPresenter.pushRatingFirebase(timeStamp,rating);
        }
    }
}
