package com.spear.android.news;

/**
 * Created by Pablo on 10/6/17.
 */

public class NewsPresenter {
    private NewsView view;
    private NewsInteractor interactor;


    public NewsPresenter(NewsActivity view) {
        this.view = view;
        interactor = new NewsInteractorImp();
    }
}
