package com.spear.android.album;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spear.android.R;
import com.spear.android.pojo.GalleryCard;

import java.util.List;

/**
 * Created by Pablo on 20/5/17.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {

    private Context mContext;
    private List<GalleryCard> albumList;
    private Activity albumActivity;
    private AlbumView.OnImageClick onImageClick;
    private static final int fakeImg = 946684800;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView image, imageRating;
        public TextView txtRating, txtUsername;
        public GalleryCard card;


        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            imageRating = (ImageView) view.findViewById(R.id.imageRating);
            txtRating = (TextView) view.findViewById(R.id.txtRating);
            txtUsername = (TextView) view.findViewById(R.id.txtUsername);
            Typeface typeLibel = Typeface.createFromAsset(albumActivity.getAssets(), "Libel_Suit.ttf");
            txtRating.setTypeface(typeLibel);
            txtUsername.setTypeface(typeLibel);
            image.setOnClickListener(this);


            //ratingBar.setOnTouchListener(this);

        }


        @Override
        public void onClick(View view) {
            if (view instanceof ImageView) {
                onImageClick.onSuccess(card);
            }

        }
    }


    public AlbumAdapter(Activity albumActivity, Context mContext, List<GalleryCard> albumList, AlbumView.OnImageClick onImageClick) {
        this.albumActivity = albumActivity;
        this.mContext = mContext;
        this.albumList = albumList;
        this.onImageClick = onImageClick;

    }


    @Override
    public AlbumAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_card, parent, false);

        return new AlbumAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GalleryCard album = albumList.get(position);
        holder.card = album;
        Glide.with(mContext).load(album.getUrlString()).into(holder.image);
        float rating = album.getRating() / album.getVotes();
        String ratingStr;
        if (Float.isNaN(rating)) {
            holder.txtUsername.setText(album.getUsername());
            holder.imageRating.setImageResource(R.mipmap.ic_fish_no_mustard);
            ratingStr = "";
            holder.txtRating.setText(ratingStr);

            if (album.getTimeStamp() == fakeImg){
                holder.txtUsername.setText("");
                holder.imageRating.setImageResource(android.R.color.transparent);
            }
        } else {
            holder.imageRating.setImageResource(R.mipmap.ic_fish_mustard);
            ratingStr = String.format("%.1f", rating);
            holder.txtRating.setText(ratingStr);
            holder.txtUsername.setText(album.getUsername());

        }

    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }
}