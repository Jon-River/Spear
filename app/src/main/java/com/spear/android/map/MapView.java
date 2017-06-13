package com.spear.android.map;

import com.spear.android.pojo.PoiInfo;

import java.util.Map;

/**
 * Created by Pablo on 2/6/17.
 */

public interface MapView {

    void cambiarFragment(int fragment);

    void pushGeoPoint(String latitude, String longitude, String comentary);

    void setGeoCordsMenu(String s, String s1);

    void loadGeoPoints();

    boolean checkIfMapMenuisOpen();

    void setGeoCoords(Map<String, Object> map);

    void setDataPoi(PoiInfo poi);

    void deletePoi(String timestamp);

    void closeMenuDialog();

    void showError(String s);

    void notChangeCoords();

    void changeCoords();
}
