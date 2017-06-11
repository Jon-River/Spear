package com.spear.android.news;

import com.spear.android.pojo.NewsCard;

import java.util.ArrayList;

/**
 * Created by Pablo on 10/6/17.
 */

public class NewsPresenter {
    private NewsView view;
    private NewsInteractor interactor;
    private ArrayList <NewsCard> newsList;

    public NewsPresenter(NewsActivity view) {
        this.view = view;
        interactor = new NewsInteractorImp(onLoadNews);
    }

    public void loadNewsFirebase() {
        interactor.loadNewsFirebase();
    }

    NewsInteractor.OnLoadNews onLoadNews = new NewsInteractor.OnLoadNews() {
        @Override
        public void OnSuccess(ArrayList<NewsCard> newsList) {
            view.render(newsList);
        }

        @Override
        public void OnError(String error) {
            view.showError(error);
        }
    };
}
