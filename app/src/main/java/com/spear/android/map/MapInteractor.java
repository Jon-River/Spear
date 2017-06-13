package com.spear.android.map;

import java.util.Map;

/**
 * Created by Pablo on 2/6/17.
 */

public interface MapInteractor {
    void pushGeoPointFirebase(String latitude, String longitude, String description);

    void loadGeoPointsFirebase();

    void deletePoiFirebase(String timestamp);

    interface OnPushPoiFirebase {
        void OnSuccess();

        void OnError(Exception exception);
    }

    interface OnLoadPoiFirebase {
        void OnSuccess(Map<String, Object> poiList);

        void OnError();
    }

    interface OnPoiDeletedFirebase{
        void OnSucces(String timestamp);
        void OnError();
    }
}
