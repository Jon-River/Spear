package fragments.weather;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spear.android.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import objects.OpenWeather.WeatherResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment implements WeatherView {

    private Button btnWeather;
    private ImageView imgWeather, imgCardinal;
    private TextView txtHumidity, txtPressure, txtTemperature, txtTempMax, txtTempMin, txtDate, txtDescription, txtWindVel, txtSunrise, txtSunset;
    private EditText etCityZip;
    Dialog dialog;


    private WeatherPresenter weatherPresenter;

    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);

        weatherPresenter = new WeatherPresenter(this);

        init(v);

        dialog = new Dialog(getContext(), R.style.DialogAnimation);
        dialog.setContentView(R.layout.dialog_weather);
        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        //dialog.getWindow().getAttributes().windowAnimations = R.anim.dialog_animation;


        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String cityZip = etCityZip.getText().toString();
                if (cityZip != null) {
                    weatherPresenter.getWeatherByName(cityZip);
                }

            }
        });
        return v;
    }

    private void init(View v) {
        btnWeather = (Button) v.findViewById(R.id.btnFind);
        txtDate = (TextView) v.findViewById(R.id.txtDate);
        txtDescription = (TextView) v.findViewById(R.id.txtDescription);
        txtHumidity = (TextView) v.findViewById(R.id.txtHumidity);
        txtPressure = (TextView) v.findViewById(R.id.txtPressure);
        txtTemperature = (TextView) v.findViewById(R.id.txtTemperature);
        txtTempMax = (TextView) v.findViewById(R.id.txtTempMax);
        txtTempMin = (TextView) v.findViewById(R.id.txtTempMin);
        txtSunrise = (TextView) v.findViewById(R.id.txtSunrise);
        txtSunset = (TextView) v.findViewById(R.id.txtSunset);
        txtWindVel = (TextView) v.findViewById(R.id.txtWindVel);
        imgCardinal = (ImageView) v.findViewById(R.id.imgCardinal);
        imgWeather = (ImageView) v.findViewById(R.id.imgWeather);
        etCityZip = (EditText) v.findViewById(R.id.etCity);


    }


    @Override
    public void setWeatherResult(WeatherResponse weatherResult) {
        WeatherResponse data = weatherResult;
        if (data != null) {
            setDataResponse(data);
            Log.v("data", "" + data.getDescription() + " " + data.getPressure() + " " + data.getTemperature() + " " + data.getHumidity() + " " + data.getSpeed() + " " + data.getDeg() + " " + data.getTempMax() + " " + data.getTempMin() + " " + data.getSunrise() + " " + data.getSunset());

        }
    }

    private void setDataResponse(WeatherResponse data) {
        convertDegToImage(data.getDeg());
        convertIconToImage(data.getIcon());
        Date date = new Date();
        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");
        String dateStr = fmtOut.format(date);
        txtDate.setText(dateStr);
        txtDescription.setText(data.getDescription());
        txtPressure.setText(String.valueOf(data.getPressure()));
        txtHumidity.setText(String.valueOf(data.getHumidity()));
        txtTemperature.setText(String.valueOf(data.getTemperature()) + "ºC");
        txtTempMin.setText(String.valueOf(data.getTempMin()));
        txtTempMax.setText(String.valueOf(data.getTempMax()));
        txtWindVel.setText(String.valueOf(data.getSpeed()));


        String sunrise = getDateCurrentTimeZone(data.getSunrise());
        sunrise = sunrise.substring(10);

        String sunset = getDateCurrentTimeZone(data.getSunset());
        String ofset = getCurrentTimezoneOffset();
        sunset = sunset.substring(10);
        Log.v("time", " " + sunrise + sunset+" off:" + ofset);
        txtSunrise.setText(sunrise);
        txtSunset.setText(sunset);


    }

    public String getDateCurrentTimeZone(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getTimeZone("GMT+02:00");
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
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

        String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
        offset = "GMT"+(offsetInMillis >= 0 ? "+" : "-") + offset;

        return offset;
    }

    private void convertIconToImage(String icon) {
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

    }

    private void convertDegToImage(float deg) {
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

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), "" + error, Toast.LENGTH_SHORT).show();

    }
}
