package com.spear.android.map;

/**
 * Created by Pablo on 2/6/17.
 */

public class MapPresenter {

    private MapView view;
    private MapInteractor mapInteractor;


    public MapPresenter(MapView mapActivity) {
        this.view = mapActivity;
        mapInteractor = new MapInteractorImp();
    }
}
