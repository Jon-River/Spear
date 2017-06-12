package com.spear.android.map.map;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.spear.android.R;
import com.spear.android.map.MapActivity;
import com.spear.android.pojo.PoiInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, LocationListener, CompoundButton.OnCheckedChangeListener {

    private View view;
    private MapView map;
    private com.spear.android.map.MapView mapView;
    private GoogleMap googleMap;
    private FloatingActionButton fabAddPoi;



    private static final String LOGTAG = "android-localizacion";

    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private static final int PETICION_CONFIG_UBICACION = 201;

    private GoogleApiClient apiClient;
    private LocationRequest locRequest;
    private SwitchCompat swEnableLocation;
    private Marker marker;
    private Map<String, Object> poiListMap;
    private static final int mapmenu = 3;
    private static final int poi = 4;


    public MapFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            mapView = (MapActivity) getActivity();
            view = inflater.inflate(R.layout.fragment_map, container, false);
            map = (MapView) view.findViewById(R.id.map);
            fabAddPoi = (FloatingActionButton) view.findViewById(R.id.fabAddPoi);

            swEnableLocation = (SwitchCompat) view.findViewById(R.id.swEnableLocation);
            swEnableLocation.setOnCheckedChangeListener(this);

            fabAddPoi.setOnClickListener(this);
            map.onCreate(savedInstanceState);
            map.onResume();
            map.getMapAsync(this);
            apiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity(), this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();


        } catch (InflateException e) {

        }
        return view;

    }

    @Override
    public void onMapReady(GoogleMap gooMap) {
        this.googleMap = gooMap;
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Projection proj = googleMap.getProjection();
                Point coord = proj.toScreenLocation(latLng);
                mapView.setGeoCordsMenu(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude));
                mapView.cambiarFragment(mapmenu);
            }
        });
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            public void onMapClick(LatLng point) {
                Projection proj = googleMap.getProjection();
                Point coord = proj.toScreenLocation(point);
               /* Toast.makeText(
                        getActivity(),
                        "Click\n" +
                                "Lat: " + point.latitude + "\n" +
                                "Lng: " + point.longitude + "\n" +
                                "X: " + coord.x + " - Y: " + coord.y,
                        Toast.LENGTH_SHORT).show();*/
            }
        });
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                mapView.cambiarFragment(poi);
                PoiInfo poi = (PoiInfo) poiListMap.get(marker.getTitle());
                mapView.setDataPoi(poi);
                Toast.makeText(
                        getActivity(),
                        "Marcador pulsado:\n" +
                                marker.getTitle(),
                        Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        mapView.loadGeoPoints();


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabAddPoi) {
            mapView.cambiarFragment(mapmenu);
        }
    }


    public void setSatelite() {
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }


    @Override
    public void onLocationChanged(Location location) {
        updateUINoMove(location);
    }


    private void enableLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            swEnableLocation.setChecked(false);
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);

        } else {
            swEnableLocation.setChecked(true);

            locRequest = new LocationRequest();
            locRequest.setInterval(2000);
            locRequest.setFastestInterval(1000);
            locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest locSettingsRequest =
                    new LocationSettingsRequest.Builder()
                            .addLocationRequest(locRequest)
                            .build();

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(
                            apiClient, locSettingsRequest);

            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult locationSettingsResult) {
                    final Status status = locationSettingsResult.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:

                            Log.i(LOGTAG, "Configuración correcta");
                            startLocationUpdates();

                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                Log.i(LOGTAG, "Se requiere actuación del usuario");
                                status.startResolutionForResult(getActivity(), PETICION_CONFIG_UBICACION);

                            } catch (IntentSender.SendIntentException e) {
                                Log.i(LOGTAG, "Error al intentar solucionar configuración de ubicación");
                            }

                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Log.i(LOGTAG, "No se puede cumplir la configuración de ubicación necesaria");

                            break;
                    }
                }
            });
        }


    }

    private void disableLocationUpdates() {
        if (marker != null) {
            marker.remove();
        }
        LocationServices.FusedLocationApi.removeLocationUpdates(
                apiClient, this);

    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i(LOGTAG, "Inicio de recepción de ubicaciones");
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    apiClient, locRequest, this);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {


        Log.e(LOGTAG, "Error grave al conectar con Google Play Services");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
            swEnableLocation.setChecked(false);
        } else {
            swEnableLocation.setChecked(true);

            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);
            CameraUpdate camUpd1 =
                    CameraUpdateFactory
                            .newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 18);
            googleMap.moveCamera(camUpd1);
            //updateUI(lastLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.e(LOGTAG, "Se ha interrumpido la conexión con Google Play Services");
    }

    private void updateUI(Location loc) {
        if (loc != null) {
            if (marker != null) {
                marker.remove();
            }
            CameraUpdate camUpd1 =
                    CameraUpdateFactory
                            .newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 18);
            googleMap.moveCamera(camUpd1);

            marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title("Current position"));


            mapView.setGeoCordsMenu(String.valueOf(loc.getLatitude()), String.valueOf(loc.getLongitude()));
        } else {
            mapView.setGeoCordsMenu("Unknown", "Unknown");
        }
    }

    private void updateUINoMove(Location loc) {
        if (loc != null) {
         /*   if (marker != null) {
                marker.remove();
            }*/
            // marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title("Current position"));
            if (!mapView.checkIfMapMenuisOpen()) {
                mapView.setGeoCordsMenu(String.valueOf(loc.getLatitude()), String.valueOf(loc.getLongitude()));
            }


        } else {
            mapView.setGeoCordsMenu("Unknown", "Unknown");
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PETICION_CONFIG_UBICACION:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(LOGTAG, "El usuario no ha realizado los cambios de configuración necesarios");
                        break;
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {

            enableLocationUpdates();

        } else {

            disableLocationUpdates();
        }
    }



    private void updateMarkersMap() {
       if (marker != null){
           marker.remove();

       }
        Iterator iterator = poiListMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            PoiInfo poiObj = (PoiInfo) entry.getValue();
            marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(poiObj.getLatitude()), Double.parseDouble(poiObj.getLongitude()))).title(String.valueOf(poiObj.getTimestamp())));
        }
    }

    public void permissionGranted() {
        @SuppressWarnings("MissingPermission")
        Location lastLocation =
                LocationServices.FusedLocationApi.getLastLocation(apiClient);
        CameraUpdate camUpd1 =
                CameraUpdateFactory
                        .newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 18);
        googleMap.moveCamera(camUpd1);
        swEnableLocation.setChecked(true);
        // updateUI(lastLocation);
    }

    public void permissionDenied() {
        swEnableLocation.setChecked(false);
        Log.e(LOGTAG, "Permiso denegado");
    }


    public void setPoiListMap(Map<String, Object> poiListMap) {
        this.poiListMap = poiListMap;
        updateMarkersMap();
    }

    public void addPoiListMap(Map<String, Object> poiListMa) {
        Iterator iterator = poiListMa.entrySet().iterator();
        if (poiListMap == null){
            poiListMap = new HashMap<>();
        }
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String timestamp = (String) entry.getKey();
            PoiInfo poi = (PoiInfo) entry.getValue();
            poiListMap.put(timestamp,poi);
        }


        updateMarkersMap();
    }


    public void deletePoi(String timestamp) {
        poiListMap.remove(timestamp);
        updateMarkersMap();
    }
}