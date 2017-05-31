package com.spear.android.weather;

import com.spear.android.pojo.WeatherResponse;

/**
 * Created by Pablo on 13/5/17.
 */

public interface WeatherInteractor {


    void getWeatherByName(String cityName);

    public interface OnResponseWeatherApi{
        void onSuccess(WeatherResponse weatherResult);
        void onError(String error);
    }
}
