package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spear.android.R;

import java.util.List;

import Objects.CardImage;

/**
 * Created by Pablo on 7/4/17.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {

private Context mContext;
private List<CardImage> albumList;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView title, province;
    public RatingBar ratingBar;
    public ImageView image;

    public MyViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.username);
        province = (TextView) view.findViewById(R.id.province);
        image = (ImageView) view.findViewById(R.id.image);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
    }
}


    public AlbumAdapter(Context mContext, List<CardImage> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CardImage album = albumList.get(position);
        holder.title.setText(album.getUsername());
        holder.province.setText(album.getProvince());
        holder.ratingBar.setRating(album.getRating());

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getUrlString()).into(holder.image);


    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }
}