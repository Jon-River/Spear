package fragments.weather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.spear.android.R;

import objects.OpenWeather.WeatherResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment implements WeatherView{
    private Button btnWeather;


    private WeatherPresenter weatherPresenter;

    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);

        weatherPresenter = new WeatherPresenter(this);

        btnWeather = (Button) v.findViewById(R.id.openweather);
        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherPresenter.getWeatherByName("madrid");
            }
        });
        return v;
    }


    @Override
    public void setWeatherResult(WeatherResponse weatherResult) {
        WeatherResponse data = weatherResult;
        Log.v("data",""+ data.getDescription()+ " " +data.getPressure()+ " " +data.getTemperature()+ " " +data.getHumidity()+ " " +data.getSpeed()+ " " +data.getDeg()+ " " +data.getTempMax()+ " " +data.getTempMin()+ " " +data.getSunrise() + " " + data.getSunset());
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(),""+error, Toast.LENGTH_SHORT).show();

    }
}
