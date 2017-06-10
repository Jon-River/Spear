package com.spear.android.weather.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.spear.android.R;

import com.spear.android.weather.WeatherActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

  private Button btnSkip, btnFind;
  private WeatherActivity view;
  private EditText etCityZip;

  public SearchFragment() {

    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_search, container, false);
    view = (WeatherActivity) getActivity();
    btnFind = (Button) v.findViewById(R.id.btnFind);
    btnSkip = (Button) v.findViewById(R.id.btnSkip);
    etCityZip = (EditText) v.findViewById(R.id.etCity);

    btnSkip.setOnClickListener(this);
    btnFind.setOnClickListener(this);

    return v;
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
