package com.spear.android.news;

import com.spear.android.pojo.NewsCard;

import java.util.ArrayList;

/**
 * Created by Pablo on 10/6/17.
 */

public interface NewsInteractor {
    void loadNewsFirebase();

    interface OnLoadNews{
        void OnSuccess(ArrayList<NewsCard> newsList);
        void OnError(String error);
    }
}
