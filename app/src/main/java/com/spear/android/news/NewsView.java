package com.spear.android.news;

import com.spear.android.pojo.NewsCard;

import java.util.ArrayList;

/**
 * Created by Pablo on 10/6/17.
 */

public interface NewsView {
    void render(ArrayList<NewsCard> newsList);

    void showError(String error);

    void openUrlIntent(String s);
}
