package com.spear.android.news;

/**
 * Created by Pablo on 10/6/17.
 */

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
import com.spear.android.pojo.NewsCard;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private Context mContext;
    private List<NewsCard> newsList;
    private Activity newsActivity;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView newsImage;
        public TextView txtTittle, txtSubtittle, txtLinkUrl, txtDescription;
        public NewsCard card;


        public MyViewHolder(View view) {
            super(view);
            newsImage = (ImageView) view.findViewById(R.id.imageNews);
            txtTittle = (TextView) view.findViewById(R.id.txtTittle);
            txtSubtittle = (TextView) view.findViewById(R.id.txtSubtittle);
            txtLinkUrl = (TextView) view.findViewById(R.id.txtLinkUrl);
            txtDescription = (TextView) view.findViewById(R.id.txtDescription);
            Typeface typeLibel = Typeface.createFromAsset(newsActivity.getAssets(), "Libel_Suit.ttf");
            txtTittle.setTypeface(typeLibel);
            txtSubtittle.setTypeface(typeLibel);
            txtLinkUrl.setTypeface(typeLibel);
            txtDescription.setTypeface(typeLibel);



            //ratingBar.setOnTouchListener(this);

        }

    }


    public NewsAdapter(Activity newsActivity, Context mContext, List<NewsCard> newsList) {
        this.newsActivity = newsActivity;
        this.mContext = mContext;
        this.newsList = newsList;


    }


    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_card, parent, false);

        return new NewsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.MyViewHolder holder, int position) {
        NewsCard news = newsList.get(position);
        holder.card = news;
        Glide.with(mContext).load(news.getImageurl()).into(holder.newsImage);
        holder.txtDescription.setText(news.getDescription());
        holder.txtLinkUrl.setText(news.getLinkurl());
        holder.txtTittle.setText(news.getTittle());
        holder.txtSubtittle.setText(news.getSubttitle());

    }


    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
