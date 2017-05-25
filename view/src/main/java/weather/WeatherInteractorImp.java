package weather;

import managers.OpenWeatherManager;
import pojo.OpenWeather.WeatherResponse;

/**
 * Created by Pablo on 13/5/17.
 */

public class WeatherInteractorImp  implements WeatherInteractor{

    private OpenWeatherManager openWeatherManager;
    private OnResponseWeatherApi onResponseWeatherPresenter;

    public WeatherInteractorImp(OnResponseWeatherApi onResponseWeatherPresenter) {
        openWeatherManager = new OpenWeatherManager(onResponseWeatherApi);
        this.onResponseWeatherPresenter = onResponseWeatherPresenter;
    }

    @Override
    public void getWeatherByName(String cityName) {
        openWeatherManager.getWeatherByName(cityName);
    }

    private final OnResponseWeatherApi onResponseWeatherApi  = new OnResponseWeatherApi() {
        @Override
        public void onSuccess(WeatherResponse weatherResult) {
            onResponseWeatherPresenter.onSuccess(weatherResult);
        }

        @Override
        public void onError(String error) {
            onResponseWeatherPresenter.onError(error);
        }
    };

}
