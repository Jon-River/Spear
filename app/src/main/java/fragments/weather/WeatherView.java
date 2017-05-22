package fragments.weather;

import objects.OpenWeather.WeatherResponse;

/**
 * Created by Pablo on 12/5/17.
 */

public interface WeatherView {
    void setWeatherResult(WeatherResponse weatherResult);
    void showError(String error);

  void getWeatherByCityZip(String cityZip);
}
