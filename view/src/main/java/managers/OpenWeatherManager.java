package managers;

/**
 * Created by Pablo on 9/5/17.
 */

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weather.WeatherInteractor;
import pojo.OpenWeather.WeatherResponse;


/**
 * Created by pablo.rojas on 01/03/2017.
 */
public class OpenWeatherManager {

    private WeatherInteractor.OnResponseWeatherApi onResponseWeatherApi;

    private WeatherResponse weatherResult;

    private String url =  "http://api.openweathermap.org/data/2.5/weather?";

    private String ApiKey = "&units=metric&appid=a8042b44f26db02b28c5916d6a000fda";

    private static final String TAG= "OpenWeatherManagerApi";

    public OpenWeatherManager(WeatherInteractor.OnResponseWeatherApi onResponseWeatherApi) {
        this.onResponseWeatherApi = onResponseWeatherApi;
    }

    public void getWeatherByLatLon (double lat, double lon){
        url = url + "lat="+lat+"&lon="+lon+ApiKey;
        Log.d("Query",""+ url);
        getWeatherByUrl(url);
    }


    public void getWeatherByZip(int zip, String countryCode){
        url = url + "zip="+zip+","+countryCode+ApiKey;
        Log.d("Query",""+ url);
        getWeatherByUrl(url);

    }

    public void getWeatherByName(String cityName){
        url = url + "q="+cityName+ApiKey;
        Log.d("Query",""+ url);
        getWeatherByUrl(url);
    }

    private WeatherResponse getWeatherByUrl(String url) {

        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject objCoord = response.getJSONObject("coord");
                            String lon = objCoord.get("lon").toString();
                            String lat = objCoord.get("lat").toString();
                            float fLon =  Float.parseFloat(lon);
                            float fLat = Float.parseFloat(lat);

                            //Coords coord = new Coords(fLat,fLon);

                            JSONArray jsWeather = response.getJSONArray("weather");
                            JSONObject obj = (JSONObject) jsWeather.get(0);
                            int idWeather = Integer.parseInt(obj.get("id").toString());
                            String sMain = obj.get("main").toString();
                            String description = obj.get("description").toString();
                            String icon = obj.get("icon").toString();

                           // Weather weather = new Weather(idWeather, sMain, description, icon);

                            String base = response.get("base").toString();

                            JSONObject objMain = response.getJSONObject("main");
                            double temperature =  Double.parseDouble(objMain.get("temp").toString());
                            long pressure = Long.parseLong(objMain.get("pressure").toString());
                            double humidity =  Double.parseDouble(objMain.get("humidity").toString());
                            double tempMin =  Double.parseDouble(objMain.get("temp_min").toString());
                            double tempMax =  Double.parseDouble(objMain.get("temp_max").toString());

                            //Main main = new Main(temperature,pressure, humidity, tempMin, tempMax);

                            String visibility = response.get("visibility").toString();

                            JSONObject objWind = response.getJSONObject("wind");
                            float speed = Float.parseFloat(objWind.get("speed").toString());
                            float deg = Float.parseFloat(objWind.get("deg").toString());

                            //Wind wind = new Wind(speed, deg);

                            JSONObject objClouds = response.getJSONObject("clouds");
                            float clouds = Float.parseFloat(objClouds.get("all").toString());

                            long dt = Long.parseLong(response.get("dt").toString());

                            JSONObject objSys = response.getJSONObject("sys");
                            int type = Integer.parseInt(objSys.get("type").toString());
                            long idsys = Long.parseLong(objSys.get("id").toString());
                            float message = Float.parseFloat(objSys.get("message").toString());
                            String country = objSys.get("country").toString();
                            long sunrise = Long.parseLong(objSys.get("sunrise").toString());
                            long sunset = Long.parseLong(objSys.get("sunset").toString());

                           // Sys sys = new Sys(type, idsys, message, country, sunrise, sunset);

                            long id = Long.parseLong(response.get("id").toString());
                            String name = response.get("name").toString();
                            long  cod = Long.parseLong(response.get("cod").toString());

                            weatherResult = new WeatherResponse(description, temperature, pressure , humidity, tempMax, tempMin, speed, deg, sunrise, sunset, name, icon);
                            onResponseWeatherApi.onSuccess(weatherResult);
                            rebootUrl();


                        } catch (JSONException e) {
                            onResponseWeatherApi.onError(e.toString());

                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());

                    }
                }
        );

        WeatherApplication.requestQueue.add(jsArrayRequest);

        return weatherResult;
    }

    private void rebootUrl() {
        url = "http://api.openweathermap.org/data/2.5/weather?";
    }


}
