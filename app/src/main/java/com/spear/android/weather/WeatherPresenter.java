package com.spear.android.weather;

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


    public void getWeatherByName(String cityName) {
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
}
