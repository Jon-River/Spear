package com.spear.android.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spear.android.news.NewsActivity;
import com.spear.android.R;
import com.spear.android.album.AlbumActivity;
import com.spear.android.map.MapFragment;
import com.spear.android.pojo.WeatherResponse;
import com.spear.android.weather.search.SearchFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class WeatherActivity extends AppCompatActivity implements WeatherView, View.OnClickListener {


    private ImageView imgWeather, imgCardinal;
    private TextView txtHumidity, txtPressure, txtTemperature, txtDate,
            txtDescription, txtWindVel, txtSunrise, txtSunset, txtCity;
    private FloatingActionButton fabOpenSearchView;

    private WeatherPresenter weatherPresenter;

    private SearchFragment searchFragment;
    private MapFragment mapFragment;
    private FragmentManager fm;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather2);


        weatherPresenter = new WeatherPresenter(this);

        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.signOutItem:

                return true;
            case R.id.profile:

                return true;
            case android.R.id.home:

                return true;
            case R.id.newsmenu:
                startActivity(new Intent(this, NewsActivity
                        .class));
                return true;
            case R.id.mapmenu:
                if (menu.getItem(1).getTitle().equals("map")) {
                    openMapFragment();
                } else {
                    closeMapFragment();
                }
                return true;
            case R.id.gallerymenu:
                startActivity(new Intent(this, AlbumActivity
                        .class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closeMapFragment() {
        setTitle("Weather");
        cambiarFragment(0);
        menu.getItem(1).setIcon(R.mipmap.earth);
        menu.getItem(1).setTitle("map");
    }

    private void openMapFragment() {
        setTitle("Map");
        menu.getItem(1).setIcon(R.mipmap.weathermenu);
        menu.getItem(1).setTitle("weather");
        cambiarFragment(2);
    }

    public void init() {

        fm = getSupportFragmentManager();

        searchFragment = (SearchFragment) fm.findFragmentById(R.id.weatherSearchFrag);
        mapFragment = (MapFragment) fm.findFragmentById(R.id.mapFragment);
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        txtPressure = (TextView) findViewById(R.id.txtPressure);
        txtTemperature = (TextView) findViewById(R.id.txtTemperature);
        txtSunrise = (TextView) findViewById(R.id.txtSunrise);
        txtSunset = (TextView) findViewById(R.id.txtSunset);
        txtWindVel = (TextView) findViewById(R.id.txtWindVel);
        imgCardinal = (ImageView) findViewById(R.id.imgCardinal);
        imgWeather = (ImageView) findViewById(R.id.imgWeather);
        fabOpenSearchView = (FloatingActionButton) findViewById(R.id.fabSearchView);
        //fabOpenSearchView.setBackgroundTintList(getResources().getColorStateList(R.color.black));
        fabOpenSearchView.setOnClickListener(this);
        cambiarFragment(1);
    }

    @Override
    public void setWeatherResult(WeatherResponse weatherResult) {
        cambiarFragment(0);
        WeatherResponse data = weatherResult;
        if (data != null) {
            setDataResponse(data);
            Log.v("data", ""
                    + data.getDescription()
                    + " "
                    + data.getPressure()
                    + " "
                    + data.getTemperature()
                    + " "
                    + data.getHumidity()
                    + " "
                    + data.getSpeed()
                    + " "
                    + data.getDeg()
                    + " "
                    + data.getTempMax()
                    + " "
                    + data.getTempMin()
                    + " "
                    + data.getSunrise()
                    + " "
                    + data.getSunset()
                    + " "
                    + data.getName());
        }
    }

    private void setDataResponse(WeatherResponse data) {
        convertDegToImage(data.getDeg());
        convertIconToImage(data.getIcon());
        Date date = new Date();
        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
        String dateStr = fmtOut.format(date);
        if (date != null) {
            txtDate.setText(dateStr);
        } else {
            txtDate.setText("N/A");
        }

        if (data.getDescription() != null) {
            txtDescription.setText(data.getDescription());
        } else {
            txtDescription.setText("N/A");
        }

        if (data.getPressure() != 0) {
            txtPressure.setText(String.valueOf(data.getPressure()) + "hPa");
        } else {
            txtPressure.setText("N/A");
        }


        if (data.getHumidity() != 0) {
            txtHumidity.setText(String.valueOf(data.getHumidity()) + "%");

        } else {
            txtHumidity.setText("N/A");
        }

        if (data.getTemperature() != 0) {
            int temp = (int) data.getTemperature();
            txtTemperature.setText(String.valueOf(temp) + "ºC");
        } else {
            txtTemperature.setText("N/A");
        }


        if (data.getSpeed() != 0) {
            txtWindVel.setText(convertToKMH(data.getSpeed()));
        } else {
            txtWindVel.setText("N/A");
        }

        if (data.getSunrise() != 0) {
            String sunrise = timeStampToDate(data.getSunrise());

            sunrise = sunrise.substring(10);
            txtSunrise.setText(sunrise);

        } else {
            txtSunrise.setText("N/A");
        }

        if (data.getSunset() != 0) {

            String sunset = timeStampToDate(data.getSunset());

            sunset = sunset.substring(10);
            txtSunset.setText(sunset);

        } else {
            txtSunset.setText("N/A");
        }

        if (data.getName() != null) {
            txtCity.setText(data.getName());
        } else {
            txtCity.setText("N/A");
        }

    }

    private String convertToKMH(float speed) {
        String kmh = String.format("%.2f", speed * 1.609344f) + " KM/H";
        return kmh;
    }

    public String timeStampToDate(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();

            calendar.setTimeInMillis(timestamp * 1000);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
        }
        return "";
    }

    public String getCurrentTimezoneOffset() {

        TimeZone tz = TimeZone.getDefault();
        Calendar cal = GregorianCalendar.getInstance(tz);
        int offsetInMillis = tz.getOffset(cal.getTimeInMillis());

        String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000),
                Math.abs((offsetInMillis / 60000) % 60));
        offset = "GMT" + (offsetInMillis >= 0 ? "+" : "-") + offset;

        return offset;
    }

    private void convertIconToImage(String icon) {
        if (icon != null) {
            if (icon.equals("01d")) {
                imgWeather.setImageResource(R.drawable.weatherclear);
            } else if (icon.equals("01n")) {
                imgWeather.setImageResource(R.drawable.weatherclearnight);
            } else if (icon.equals("02d")) {
                imgWeather.setImageResource(R.drawable.weatherfewclouds);
            } else if (icon.equals("02n")) {
                imgWeather.setImageResource(R.drawable.weatherfewcloudsnight);
            } else if (icon.equals("03d")) {
                imgWeather.setImageResource(R.drawable.weatherclouds);
            } else if (icon.equals("03n")) {
                imgWeather.setImageResource(R.drawable.weathercloudsnight);
            } else if (icon.equals("04d")) {
                imgWeather.setImageResource(R.drawable.weatherclouds);
            } else if (icon.equals("04n")) {
                imgWeather.setImageResource(R.drawable.weathercloudsnight);
            } else if (icon.equals("09d")) {
                imgWeather.setImageResource(R.drawable.weathershowersday);
            } else if (icon.equals("09n")) {
                imgWeather.setImageResource(R.drawable.weathershowersnight);
            } else if (icon.equals("010n")) {
                imgWeather.setImageResource(R.drawable.weatherrainday);
            } else if (icon.equals("010n")) {
                imgWeather.setImageResource(R.drawable.weatherrainnight);
            } else if (icon.equals("011n")) {
                imgWeather.setImageResource(R.drawable.weatherstorm);
            } else if (icon.equals("011n")) {
                imgWeather.setImageResource(R.drawable.weatherstormnight);
            } else if (icon.equals("013n")) {
                imgWeather.setImageResource(R.drawable.weathersnow);
            } else if (icon.equals("013n")) {
                imgWeather.setImageResource(R.drawable.weathersnow);
            } else if (icon.equals("050n")) {
                imgWeather.setImageResource(R.drawable.weathermist);
            } else if (icon.equals("050n")) {
                imgWeather.setImageResource(R.drawable.weathermist);
            }
        } else {
            imgWeather.setImageResource(R.mipmap.error);
        }
    }

    private void convertDegToImage(float deg) {
        if (deg == 0) {
            imgCardinal.setImageResource(R.mipmap.error);
        } else {
            if (deg >= 337.5 || deg < 22.5) {
                //north
                imgCardinal.setImageResource(R.mipmap.north);
            } else if (deg >= 22.5 && deg < 67.5) {
                //northeast
                imgCardinal.setImageResource(R.mipmap.northeast);
            } else if (deg >= 67.5 && deg < 112.5) {
                //east
                imgCardinal.setImageResource(R.mipmap.east);
            } else if (deg >= 112.5 && deg < 157.5) {
                //southeast
                imgCardinal.setImageResource(R.mipmap.southeast);
            } else if (deg >= 157.5 && deg < 202.5) {
                //south
                imgCardinal.setImageResource(R.mipmap.south);
            } else if (deg >= 202.5 && deg < 247.5) {
                //southweast
                imgCardinal.setImageResource(R.mipmap.southwest);
            } else if (deg >= 247.5 && deg < 292.5) {
                //west
                imgCardinal.setImageResource(R.mipmap.west);
            } else if (deg >= 292.5 && deg < 337.5) {
                //northwest
                imgCardinal.setImageResource(R.mipmap.northwest);
            }
        }
    }


    @Override
    public void showError(String error) {
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
    }

    public void cambiarFragment(int ifrg) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(searchFragment);
        transaction.hide(mapFragment);

        if (ifrg == 1) {
            transaction.show(searchFragment);
        } else if (ifrg == 2) {
            transaction.show(mapFragment);
        }
        transaction.commit();
    }

    @Override
    public void getWeatherByCityZip(String cityZip) {
        weatherPresenter.getWeatherByName(cityZip);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabSearchView) {
            cambiarFragment(1);
        }

    }
}