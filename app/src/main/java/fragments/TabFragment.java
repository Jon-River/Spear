package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.spear.android.R;

import managers.OpenWeatherManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {
    private Button btnWeather;

    private OpenWeatherManager openWeatherManager;

    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab, container, false);
        openWeatherManager = new OpenWeatherManager();

        btnWeather = (Button) v.findViewById(R.id.openweather);
        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWeatherManager.getWeatherByName("madrid");
            }
        });
        return v;
    }



}
