package com.spear.android.album;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.spear.android.R;
import com.spear.android.pojo.CardImage;

import java.util.List;

/**
 * Created by Pablo on 20/5/17.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {

    private Context mContext;
    private List<CardImage> albumList;
    private Activity albumActivity;
    private AlbumView.OnImageClick onImageClick;




    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView image;
        public CardImage card;




        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            image.setOnClickListener(this);


            //ratingBar.setOnTouchListener(this);

        }


        @Override
        public void onClick(View view) {
            if (view instanceof ImageView){
                onImageClick.onSuccess(card);
            }

        }
    }


    public AlbumAdapter(Activity albumActivity, Context mContext, List<CardImage> albumList, AlbumView.OnImageClick onImageClick) {
        this.albumActivity = albumActivity;
        this.mContext = mContext;
        this.albumList = albumList;
        this.onImageClick = onImageClick;

    }



    @Override
    public AlbumAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new AlbumAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CardImage album = albumList.get(position);
        holder.card= album;
        Glide.with(mContext).load(album.getUrlString()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }
}