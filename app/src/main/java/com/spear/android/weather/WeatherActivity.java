package com.spear.android.weather;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spear.android.R;
import com.spear.android.album.AlbumActivity;
import com.spear.android.custom.CustomTypeFace;
import com.spear.android.managers.SQLliteManager;
import com.spear.android.map.MapFragment;
import com.spear.android.news.NewsActivity;
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
    private SQLliteManager dataManager;
    private SQLiteDatabase db;
    private WeatherResponse data;
    private ActionBar actionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weather);


        weatherPresenter = new WeatherPresenter(this);

        init();

        data = checkIfDataExist();


        if (data != null) {
            setDataResponse(data, data.getDate());
            cambiarFragment(0);
        }else{
            cambiarFragment(1);
        }


    }



    private WeatherResponse checkIfDataExist() {
        WeatherResponse dataAux = null;
        db = dataManager.getWritableDatabase();
        if (db != null) {
            String selectQuery = "SELECT  * FROM " + "weather_response";
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                dataAux = new WeatherResponse();
                do {
                    Log.v("Cursor", "" + cursor.getString(0) + cursor.getString(1) + cursor.getString(2) + cursor.getString(3) + cursor.getString(4) + cursor.getString(5) + " sun: " + cursor.getString(6) + " sun: " + cursor.getString(7) + " deg: " + cursor.getString(8) + " icon: " + cursor.getString(9));
                    dataAux.setDate(cursor.getString(0));
                    dataAux.setDescription(cursor.getString(1));
                    dataAux.setPressure(Long.parseLong(cursor.getString(2)));
                    dataAux.setHumidity(Double.parseDouble(cursor.getString(3)));
                    dataAux.setTemperature(Double.parseDouble(cursor.getString(4)));
                    dataAux.setSpeed(Float.parseFloat(cursor.getString(5)));
                    dataAux.setSunrise(Long.parseLong(cursor.getString(6)));
                    dataAux.setSunset(Long.parseLong(cursor.getString(7)));
                    dataAux.setDeg(Float.parseFloat(cursor.getString(8)));
                    dataAux.setIcon(cursor.getString(9));
                    dataAux.setName(cursor.getString(10));

                } while (cursor.moveToNext());
            }
        }

        return dataAux;
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
        menu.getItem(1).setIcon(R.mipmap.earth);
        menu.getItem(1).setTitle("map");
        if (data != null) {
            cambiarFragment(0);
            fabOpenSearchView.show();
        }else{
            cambiarFragment(1);
            fabOpenSearchView.hide();
        }



    }

    private void openMapFragment() {
        setTitle("Map");
        menu.getItem(1).setIcon(R.mipmap.weathermenu);
        menu.getItem(1).setTitle("weather");
        fabOpenSearchView.hide();
        cambiarFragment(2);
    }

    public void init() {
        Typeface typeLibel = Typeface.createFromAsset(getAssets(), "Libel_Suit.ttf");
        dataManager = new SQLliteManager(this, "spear", null, 1);
        db = dataManager.getWritableDatabase();
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
       txtCity.setTypeface(typeLibel);
        txtDate.setTypeface(typeLibel);
        txtDescription.setTypeface(typeLibel);
        txtHumidity.setTypeface(typeLibel);
        txtPressure.setTypeface(typeLibel);
        txtTemperature.setTypeface(typeLibel);
        txtSunrise.setTypeface(typeLibel);
        txtSunset.setTypeface(typeLibel);
        txtWindVel.setTypeface(typeLibel);
        fabOpenSearchView.setOnClickListener(this);
        actionBar = getSupportActionBar();
        SpannableStringBuilder typeFaceAction = new SpannableStringBuilder("Weather");
        typeFaceAction.setSpan (new CustomTypeFace("", typeLibel), 0, typeFaceAction.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        actionBar.setTitle(typeFaceAction);

    }

    @Override
    public void setWeatherResult(WeatherResponse weatherResult) {

        cambiarFragment(0);
        WeatherResponse result = weatherResult;
        if (result != null) {
            Date date = new Date();
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
            String dateStr = fmtOut.format(date);
            saveWeatherResultToDatabase(result, dateStr);
            setDataResponse(result, dateStr);
        }
    }

    private void saveWeatherResultToDatabase(WeatherResponse weatherResult, String dateStr) {
        db = dataManager.getWritableDatabase();
        if (db != null) {
            db.execSQL("INSERT INTO weather_response (date, description,pressure,humidity,temperature ,windvel ,sunrise,sunset,deg,icon,city) " +
                    "VALUES ('" + dateStr + "', '" + weatherResult.getDescription() + "' , '" + weatherResult.getPressure() + "','" + weatherResult.getHumidity() + "','" + weatherResult.getTemperature() + "','" + weatherResult.getSpeed() + "','" + weatherResult.getSunrise() + "','" + weatherResult.getSunset() + "','" + weatherResult.getDeg() + "','" + weatherResult.getIcon() + "','" + weatherResult.getName() + "')");



        }
    }

    private void setDataResponse(WeatherResponse response, String dateStr) {
        convertDegToImage(response.getDeg());
        convertIconToImage(response.getIcon());
        if (dateStr != null) {
            txtDate.setText(dateStr);
        } else {
            txtDate.setText("N/A");
        }

        if (response.getDescription() != null) {
            txtDescription.setText(response.getDescription());
        } else {
            txtDescription.setText("N/A");
        }

        if (response.getPressure() != 0) {
            txtPressure.setText(String.valueOf(response.getPressure()) + "hPa");
        } else {
            txtPressure.setText("N/A");
        }


        if (response.getHumidity() != 0) {
            txtHumidity.setText(String.valueOf(response.getHumidity()) + "%");

        } else {
            txtHumidity.setText("N/A");
        }

        if (response.getTemperature() != 0) {
            int temp = (int) response.getTemperature();
            txtTemperature.setText(String.valueOf(temp) + "ºC");
        } else {
            txtTemperature.setText("N/A");
        }


        if (response.getSpeed() != 0) {
            txtWindVel.setText(convertToKMH(response.getSpeed()));
        } else {
            txtWindVel.setText("N/A");
        }

        if (response.getSunrise() != 0) {
            String sunrise = timeStampToDate(response.getSunrise());

            sunrise = sunrise.substring(10);
            txtSunrise.setText(sunrise);

        } else {
            txtSunrise.setText("N/A");
        }

        if (response.getSunset() != 0) {

            String sunset = timeStampToDate(response.getSunset());

            sunset = sunset.substring(10);
            txtSunset.setText(sunset);

        } else {
            txtSunset.setText("N/A");
        }

        if (response.getName() != null) {
            txtCity.setText(response.getName());
        } else {
            txtCity.setText("N/A");
        }
        data = checkIfDataExist();
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
        }else if (ifrg == 0){
            if (!fabOpenSearchView.isShown()){
                fabOpenSearchView.show();
            }

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
            fabOpenSearchView.hide();
        }

    }
}