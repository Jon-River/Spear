package com.spear.android.map.menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.spear.android.R;
import com.spear.android.map.MapActivity;
import com.spear.android.map.MapView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapMenuFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private EditText etLatitude, etLongitude, etDescription;
    private TextView txtTittle;
    private Button btnPushPoint, btnSkip;
    private SwitchCompat swEnableManual;
    private MapView view;
    private final int hideFragments = 0;


    public MapMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map_menu, container, false);

        init(v);
        return v;
    }

    private void init(View v) {
        view = (MapActivity)getActivity();
        etDescription = (EditText) v.findViewById(R.id.etDescription);
        etLatitude = (EditText) v.findViewById(R.id.etLatitude);
        etLongitude = (EditText) v.findViewById(R.id.etLongitude);
        etLatitude.setEnabled(true);
        etLongitude.setEnabled(true);
        btnPushPoint = (Button) v.findViewById(R.id.btnPushPoint);
        btnSkip = (Button) v.findViewById(R.id.btnSkip);
        swEnableManual = (SwitchCompat) v.findViewById(R.id.swEnableManual);
        btnPushPoint.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
        swEnableManual.setOnCheckedChangeListener(this);
        swEnableManual.setChecked(false);


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

        if(isChecked){
            swEnableManual.setText("Disable manual lat/long");
            etLatitude.setEnabled(true);
            etLongitude.setEnabled(getAllowEnterTransitionOverlap());
        }else{
            swEnableManual.setText("Enable manual lat/long");
            etLatitude.setEnabled(false);
            etLongitude.setEnabled(false);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSkip){
            view.cambiarFragment(hideFragments);
        }else if (v.getId()== R.id.btnPushPoint){
            view.pushGeoPoint(etLatitude.getText().toString(), etLongitude.getText().toString(), etDescription.getText().toString());
        }
    }

    public void setGeoCoords(String lat, String lon) {
        etLatitude.setText(lat);
        etLongitude.setText(lon);
    }
}
