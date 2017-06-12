package com.spear.android.map.poi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.spear.android.R;
import com.spear.android.map.MapActivity;
import com.spear.android.map.MapView;
import com.spear.android.pojo.PoiInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class PoiFragment extends Fragment implements View.OnClickListener {

    private TextView txtPoiLatitude, txtPoiLongitude, txtPoiDescription;
    private Button btnSkip, btnDeletePoi;
    private MapView view;
    private String timestamp;
    private static final int hideFragments = 0;


    public PoiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_poi, container, false);
        init(v);
        return v;
    }

    private void init(View v) {
        view = (MapActivity) getActivity();
        txtPoiLatitude = (TextView) v.findViewById(R.id.txtPoiLatitude);
        txtPoiLongitude = (TextView) v.findViewById(R.id.txtPoiLongitude);
        txtPoiDescription = (TextView) v.findViewById(R.id.txtPoiDescription);
        btnSkip = (Button) v.findViewById(R.id.btnSkip);
        btnDeletePoi = (Button) v.findViewById(R.id.btnDeletePoi);
        btnSkip.setOnClickListener(this);
        btnDeletePoi.setOnClickListener(this);
    }

    public void setDataPoi(PoiInfo poi) {
        String latitude = poi.getLatitude();
        String longitude = poi.getLongitude();
        String description = poi.getDescription();
        timestamp = String.valueOf(poi.getTimestamp());
        Toast.makeText(getActivity(), "lat " + latitude + " lon " + longitude + " desc " + description, Toast.LENGTH_SHORT).show();
        txtPoiLatitude.setText(latitude);
        txtPoiLongitude.setText(longitude);
        txtPoiDescription.setText(description);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSkip) {
            view.cambiarFragment(hideFragments);
        }else if (v.getId() == R.id.btnDeletePoi){
            Log.v("delete","timestamp"+timestamp);
            view.deletePoi(timestamp);
        }
    }
}
