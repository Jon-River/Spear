package com.spear.android.weather;

import com.spear.android.R;
import com.spear.android.pojo.WeatherResponse;

/**
 * Created by Pablo on 12/5/17.
 */

public class WeatherPresenter {

    private WeatherInteractor weatherInteractor;
    private WeatherView view;

    public WeatherPresenter(WeatherView view) {
        this.view = view;
        weatherInteractor = new WeatherInteractorImp(onResponseCallback);
    }


    public void getWeatherResponse(String cityName) {
        weatherInteractor.getWeatherByName(cityName);
    }

    private final WeatherInteractor.OnResponseWeatherApi onResponseCallback = new WeatherInteractor.OnResponseWeatherApi() {
        @Override
        public void onSuccess(WeatherResponse weatherResult) {
            view.setWeatherResult(weatherResult);
        }

        @Override
        public void onError(String error) {
            view.showError(error);

        }
    };

    public int convertDegToImage(float deg) {
        if (deg >= 337.5 || deg < 22.5) {
            //north
            return R.mipmap.north;
        } else if (deg >= 22.5 && deg < 67.5) {
            //northeast
            return R.mipmap.northeast;
        } else if (deg >= 67.5 && deg < 112.5) {
            //east
            return R.mipmap.east;
        } else if (deg >= 112.5 && deg < 157.5) {
            //southeast
            return R.mipmap.southeast;
        } else if (deg >= 157.5 && deg < 202.5) {
            //south
            return R.mipmap.south;
        } else if (deg >= 202.5 && deg < 247.5) {
            //southweast
            return R.mipmap.southwest;
        } else if (deg >= 247.5 && deg < 292.5) {
            //west
            return R.mipmap.west;
        } else if (deg >= 292.5 && deg < 337.5) {
            //northwest
            return R.mipmap.northwest;
        } else {
            return R.mipmap.error;
        }
    }

    public int convertIconToImage(String icon) {
        if (icon.equals("01d")) {
            return R.drawable.weatherclear;
        } else if (icon.equals("01n")) {
            return R.drawable.weatherclearnight;
        } else if (icon.equals("02d")) {
            return R.drawable.weatherfewclouds;
        } else if (icon.equals("02n")) {
            return R.drawable.weatherfewcloudsnight;
        } else if (icon.equals("03d")) {
            return R.drawable.weatherclouds;
        } else if (icon.equals("03n")) {
            return R.drawable.weathercloudsnight;
        } else if (icon.equals("04d")) {
            return R.drawable.weatherclouds;
        } else if (icon.equals("04n")) {
            return R.drawable.weathercloudsnight;
        } else if (icon.equals("09d")) {
            return R.drawable.weathershowersday;
        } else if (icon.equals("09n")) {
            return R.drawable.weathershowersnight;
        } else if (icon.equals("010n")) {
            return R.drawable.weatherrainday;
        } else if (icon.equals("010n")) {
            return R.drawable.weatherrainnight;
        } else if (icon.equals("011n")) {
            return R.drawable.weatherstorm;
        } else if (icon.equals("011n")) {
            return R.drawable.weatherstormnight;
        } else if (icon.equals("013n")) {
            return R.drawable.weathersnow;
        } else if (icon.equals("013n")) {
            return R.drawable.weathersnow;
        } else if (icon.equals("050n")) {
            return R.drawable.weathermist;
        } else if (icon.equals("050n")) {
            return R.drawable.weathermist;
        } else {
            return R.mipmap.error;
        }
    }
}
