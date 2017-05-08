package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.spear.android.R;

import java.util.List;

import fragments.album.AlbumFragment;
import objects.CardImage;

/**
 * Created by Pablo on 7/4/17.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {

    private Context mContext;
    private List<CardImage> albumList;
    private AlbumFragment albumFragment;
    public long timeStamp;




    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        public TextView name, province;
        public RatingBar ratingBar;
        public ImageView image;
        public Button btnSubmitRating;
        public int votes;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.username);
            province = (TextView) view.findViewById(R.id.province);
            image = (ImageView) view.findViewById(R.id.image);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            btnSubmitRating = (Button) view.findViewById(R.id.btnSubmitRating);
            btnSubmitRating.setOnClickListener(this);

            //ratingBar.setOnTouchListener(this);

        }


        @Override
        public void onClick(View view) {

            if (view.getId()== R.id.btnSubmitRating){
                Toast.makeText(mContext, "rating" + name.getText().toString()+  " "+ timeStamp, Toast.LENGTH_SHORT).show();
                final float rating1 = ratingBar.getRating();
                albumFragment.pushRatingFirebase(timeStamp,rating1 );
            }



        }
    }


    public AlbumAdapter(AlbumFragment albumFragment, Context mContext, List<CardImage> albumList) {
        this.albumFragment = albumFragment;
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
        holder.name.setText(album.getUsername());
        holder.province.setText(album.getProvince());
        holder.votes =  album.getVotes();
        float rating = album.getRating() / holder.votes;
        holder.ratingBar.setRating(rating);
        timeStamp = album.getTimeStamp();
        // loading album cover using Glide library
        Glide.with(mContext).load(album.getUrlString()).into(holder.image);


    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }
}