package com.spear.android.weather.search;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.spear.android.R;
import com.spear.android.weather.WeatherActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

  private Button btnSkip, btnFind;
  private WeatherActivity view;
  private EditText etCityZip;
  private TextView txtTittle;

  public SearchFragment() {

    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_weather_search, container, false);
    init(v);


    return v;
  }

  private void init(View v) {
    Typeface typeLibel = Typeface.createFromAsset(getActivity().getAssets(), "Libel_Suit.ttf");
    view = (WeatherActivity) getActivity();
    btnFind = (Button) v.findViewById(R.id.btnFind);
    btnSkip = (Button) v.findViewById(R.id.btnSkip);
    etCityZip = (EditText) v.findViewById(R.id.etCity);
    txtTittle = (TextView) v.findViewById(R.id.txtTittle);

    btnSkip.setOnClickListener(this);
    btnFind.setOnClickListener(this);
    btnFind.setTypeface(typeLibel);
    btnSkip.setTypeface(typeLibel);
    etCityZip.setTypeface(typeLibel);
    txtTittle.setTypeface(typeLibel);
  }

  @Override public void onClick(View v) {
    if (v.getId() == R.id.btnSkip){
      view.cambiarFragment(0);
    }else if (v.getId() == R.id.btnFind){
      String cityZip = etCityZip.getText().toString().trim();
      if (cityZip!= null){
        view.getWeatherResponse(cityZip);
      }
    }


  }
}
