package com.spear.android.news;

/**
 * Created by Pablo on 10/6/17.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spear.android.R;
import com.spear.android.pojo.NewsCard;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private Context mContext;
    private List<NewsCard> newsList;
    private NewsActivity view;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView newsImage;
        public TextView txtTittle, txtSubtittle, txtDescription;
        private Button btnLinkUrl;
        public NewsCard card;


        public MyViewHolder(View v) {
            super(v);
            newsImage = (ImageView) v.findViewById(R.id.imageNews);
            txtTittle = (TextView) v.findViewById(R.id.txtTittle);
            txtSubtittle = (TextView) v.findViewById(R.id.txtSubtittle);
            btnLinkUrl = (Button) v.findViewById(R.id.btnLinkUrl);
            txtDescription = (TextView) v.findViewById(R.id.txtDescription);
            Typeface typeLibel = Typeface.createFromAsset(NewsAdapter.this.view.getAssets(), "Libel_Suit.ttf");
            txtTittle.setTypeface(typeLibel);
            txtSubtittle.setTypeface(typeLibel);
            btnLinkUrl.setTypeface(typeLibel);
            txtDescription.setTypeface(typeLibel);
            btnLinkUrl.setOnClickListener(this);



            //ratingBar.setOnTouchListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnLinkUrl){
                view.openUrlIntent(btnLinkUrl.getText().toString());
            }
        }
    }


    public NewsAdapter(NewsActivity view, Context mContext, List<NewsCard> newsList) {
        this.view = view;
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
        holder.btnLinkUrl.setText(news.getLinkurl());
        holder.txtTittle.setText(news.getTittle());
        holder.txtSubtittle.setText(news.getSubtitule());

    }

    public void updateReceiptsList(List<NewsCard> newslist) {
        newsList.clear();
        newsList.addAll(newslist);
        this.notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
