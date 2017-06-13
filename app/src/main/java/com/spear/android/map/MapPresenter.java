package com.spear.android.map;

import java.util.Map;

/**
 * Created by Pablo on 2/6/17.
 */

public class MapPresenter {

    private MapView view;
    private MapInteractor mapInteractor;
    final int hideFragments = 0;


    public MapPresenter(MapView mapActivity) {
        this.view = mapActivity;

        mapInteractor = new MapInteractorImp(onPushPoiFirebase,onLoadPoiFirebase,onPoiDeletedFirebase);
    }

    public void pushGeoPoint(String latitude, String longitude, String description) {
        mapInteractor.pushGeoPointFirebase(latitude, longitude,description);
    }

    public MapInteractor.OnPushPoiFirebase onPushPoiFirebase = new MapInteractor.OnPushPoiFirebase() {
        @Override
        public void OnSuccess() {
            view.cambiarFragment(hideFragments);
            view.closeMenuDialog();
        }

        @Override
        public void OnError(Exception exception) {
            view.showError(exception.toString());
            view.closeMenuDialog();
        }
    };

    public MapInteractor.OnLoadPoiFirebase onLoadPoiFirebase = new MapInteractor.OnLoadPoiFirebase(){

        @Override
        public void OnSuccess(Map<String, Object> poiList) {
            view.setGeoCoords(poiList);
        }

        @Override
        public void OnError() {

        }
    };

    public MapInteractor.OnPoiDeletedFirebase onPoiDeletedFirebase = new MapInteractor.OnPoiDeletedFirebase() {
        @Override
        public void OnSucces(String timestamp) {
            view.cambiarFragment(hideFragments);
        }

        @Override
        public void OnError() {

        }
    };

    public void loadGeoCords(){
        mapInteractor.loadGeoPointsFirebase();
    }

    public void deletePoi(String timestamp) {
        mapInteractor.deletePoiFirebase(timestamp);
    }
}
