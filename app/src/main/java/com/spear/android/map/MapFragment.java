package com.spear.android.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.spear.android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    View mapView;
    MapView map;
    private GoogleMap googleMap;



    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mapView != null) {
            ViewGroup parent = (ViewGroup) mapView.getParent();
            if (parent != null)
                parent.removeView(mapView);
        }
        try {
            mapView = inflater.inflate(R.layout.fragment_map, container, false);
            map = (MapView) mapView.findViewById(R.id.map);
            map.onCreate(savedInstanceState);
            map.onResume();
            map.getMapAsync(this);//when you already implement OnMapReadyCallback in your fragment
        } catch (InflateException e) {
    /* com.spear.android.map is already there, just return view as it is */
        }
        return mapView;

    }

    @Override public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
