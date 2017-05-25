package weather;

import pojo.OpenWeather.WeatherResponse;

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
