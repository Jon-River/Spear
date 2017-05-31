package com.spear.android.managers;

/**
 * Created by Pablo on 9/5/17.
 */

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.spear.android.pojo.WeatherResponse;
import com.spear.android.weather.WeatherInteractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Long.parseLong;


/**
 * Created by pablo.rojas on 01/03/2017.
 */
public class OpenWeatherManager {

    private WeatherInteractor.OnResponseWeatherApi onResponseWeatherApi;

    private WeatherResponse weatherResult;

    private String url = "http://api.openweathermap.org/data/2.5/weather?";

    private String ApiKey = "&units=metric&appid=a8042b44f26db02b28c5916d6a000fda";

    private static final String TAG = "OpenWeatherManagerApi";

    public OpenWeatherManager(WeatherInteractor.OnResponseWeatherApi onResponseWeatherApi) {
        this.onResponseWeatherApi = onResponseWeatherApi;
    }

    public void getWeatherByLatLon(double lat, double lon) {
        rebootUrl();
        url = url + "lat=" + lat + "&lon=" + lon + ApiKey;
        Log.d("Query", "" + url);
        getWeatherByUrl(url);
    }


    public void getWeatherByZip(int zip, String countryCode) {
        rebootUrl();
        url = url + "zip=" + zip + "," + countryCode + ApiKey;
        Log.d("Query", "" + url);
        getWeatherByUrl(url);

    }

    public void getWeatherByName(String cityName) {
        rebootUrl();
        url = url + "q=" + cityName + ApiKey;
        Log.d("Query", "" + url);
        getWeatherByUrl(url);
    }

    private WeatherResponse getWeatherByUrl(String url) {
        weatherResult = new WeatherResponse();
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String description, icon, country;
                        double temperature, humidity;
                        long pressure, sunrise, sunset;
                        float speed, deg;


                        JSONArray jsWeather = getWeather(response);
                        JSONObject obj = getObjDescriptionIcon(jsWeather);
                        if (obj != null) {
                            description = getDescripion(obj);
                            icon = getIcon(obj);
                            if (description != null) {
                                weatherResult.setDescription(description);
                            }
                            if (icon != null) {
                                weatherResult.setIcon(icon);
                            }
                        }

                        JSONObject objMain = getObjMain(response);
                        if (objMain != null) {
                            temperature = getTemperature(objMain);
                            pressure = getPressure(objMain);
                            humidity = getHumidity(objMain);
                            if (temperature != 0) {
                                weatherResult.setTemperature(temperature);
                            }
                            if (pressure != 0) {
                                weatherResult.setPressure(pressure);
                            }
                            if (humidity != 0) {
                                weatherResult.setHumidity(humidity);
                            }

                        }

                        JSONObject objWind = getObjWind(response);
                        if (objWind != null) {
                            speed = getSpeed(objWind);
                            deg = getDeg(objWind);
                            if (speed != 0) {
                                weatherResult.setSpeed(speed);
                            }
                            if (deg != 0) {
                                weatherResult.setDeg(deg);
                            }
                        }

                        JSONObject objSys = getObjSys(response);
                        if (objSys != null) {
                            country = getCountry(objSys);
                            sunrise = getSunrise(objSys);
                            sunset = getSunset(objSys);
                            if (country != null) {

                            }
                            if (sunrise != 0) {
                                weatherResult.setSunrise(sunrise);
                            }
                            if (sunset != 0) {
                                weatherResult.setSunset(sunset);
                            }

                        }
                        String name = getName(response);
                        if (name != null){
                            weatherResult.setName(name);
                        }


                        onResponseWeatherApi.onSuccess(weatherResult);
                        Log.v("eeeeee", "success");


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onResponseWeatherApi.onError(error.getMessage());

                    }
                }
        );

        WeatherApplication.requestQueue.add(jsArrayRequest);
        return weatherResult;
    }

    private String getName(JSONObject response) {
        String name = null;
        try {
            name = response.get("name").toString();
        } catch (JSONException e) {

        }
        return name;
    }

    private JSONArray getWeather(JSONObject response) {
        JSONArray weather = null;
        try {
            weather = response.getJSONArray("weather");
        } catch (JSONException e) {

        }
        return weather;
    }

    private long getSunrise(JSONObject objSys) {
        long sunrise = 0;
        try {
            sunrise = parseLong(objSys.get("sunrise").toString());
        } catch (JSONException e) {

        }
        return sunrise;
    }

    private long getSunset(JSONObject objSys) {
        long sunset = 0;
        try {
            sunset = parseLong(objSys.get("sunset").toString());
        } catch (JSONException e) {

        }
        return sunset;
    }

    private String getCountry(JSONObject objSys) {
        String country = null;
        try {
            country = objSys.get("country").toString();
        } catch (JSONException e) {

        }
        return country;
    }

    private JSONObject getObjSys(JSONObject response) {
        JSONObject objSys = null;
        try {
            objSys = response.getJSONObject("sys");

        } catch (JSONException e) {

        }
        return objSys;
    }

    private float getDeg(JSONObject objWind) {
        float deg = 0;
        try {
            deg = Float.parseFloat(objWind.get("deg").toString());
        } catch (JSONException e) {

        }
        return deg;
    }

    private float getSpeed(JSONObject objWind) {
        float speed = 0;
        try {
            speed = Float.parseFloat(objWind.get("speed").toString());
        } catch (JSONException e) {

        }
        return speed;
    }

    private JSONObject getObjWind(JSONObject response) {
        JSONObject objWind = null;
        try {
            objWind = response.getJSONObject("wind");
        } catch (JSONException e) {

        }
        return objWind;
    }

    private double getHumidity(JSONObject objMain) {
        double humidity = 0;
        try {
            humidity = Double.parseDouble(objMain.get("humidity").toString());
        } catch (JSONException e) {

        }
        return humidity;
    }

    private long getPressure(JSONObject objMain) {
        long pressure = 0;
        try {
            pressure = Long.parseLong(objMain.get("pressure").toString());
        } catch (JSONException e) {

        }
        return pressure;
    }

    private double getTemperature(JSONObject objMain) {
        double temperature = 0;
        try {
            temperature = Double.parseDouble(objMain.get("temp").toString());
        } catch (JSONException e) {

        }
        return temperature;
    }

    private JSONObject getObjMain(JSONObject response) {
        JSONObject objMain = null;
        try {
            objMain = response.getJSONObject("main");
        } catch (JSONException e) {

        }
        return objMain;
    }

    private String getIcon(JSONObject obj) {
        String icon = null;
        try {
            icon = obj.get("icon").toString();
        } catch (JSONException e) {

        }
        return icon;
    }

    private String getDescripion(JSONObject obj) {
        String description = null;
        try {
            description = obj.get("description").toString();
        } catch (JSONException e) {

        }
        return description;
    }

    private JSONObject getObjDescriptionIcon(JSONArray jsWeather) {
        JSONObject obj = null;
        try {
            obj = (JSONObject) jsWeather.get(0);
        } catch (JSONException e) {

        }

        return obj;
    }


    private void rebootUrl() {
        url = "http://api.openweathermap.org/data/2.5/weather?";
    }


}
