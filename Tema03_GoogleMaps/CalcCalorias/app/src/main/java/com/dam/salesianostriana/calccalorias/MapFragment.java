package com.dam.salesianostriana.calccalorias;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap map;
    MapView mapView;
    private GoogleApiClient mGoogleApiClient = null;
    private Location mCurrentLocation;
    private String mLastUpdateTime;
    private boolean mRequestingLocationUpdates = true;
    private LocationRequest mLocationRequest;


    TextView txtMetros;
    TextView txtCalorias;
    Button btn_guardar;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.map_fragment, container, false);


        // 1. Instancio un objeto de tipo GoogleApiClient
        buildGoogleApiClient();

        // 2. Activar la detección de localización
        createLocationRequest();

        mapView = (MapView) v.findViewById(R.id.mapview);
        txtMetros = (TextView) v.findViewById(R.id.textViewMetros);
        txtCalorias = (TextView) v.findViewById(R.id.textViewCalorias);
        btn_guardar = (Button) v.findViewById(R.id.btn_guardar);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        return v;
    }



    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        // Intervalo de uso normal de la la aplicación
        mLocationRequest.setInterval(50000);
        // Interval de una app que requiera una localización exhaustiva
        mLocationRequest.setFastestInterval(50000);// 50 segundos
        // GPS > mejor método de localización / consume más batería
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.i("POSICION", "POSICION: onLocationChanged");
        mCurrentLocation = location;
        updateUI();

    }

    private void updateUI() {

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(37.417777, -6.155389);
        map.addMarker(new MarkerOptions().position(sydney).title("Mi calle!"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            stopLocationUpdates();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng sydney = new LatLng(37.422134, -6.146573);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));

        final List<LatLng> lista_lat = new ArrayList<>();
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                final LatLng lat_long = new LatLng(latLng.latitude, latLng.longitude);
                final Marker marcador = map.addMarker(new MarkerOptions()
                        .position(lat_long)
                        .draggable(true));

                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                marcador.setTitle("Mi posicion");
                marcador.showInfoWindow();

                lista_lat.add(lat_long);
                PolylineOptions options = new PolylineOptions();

                for (int i = 0; i < lista_lat.size(); i++) {
                    options.add(new LatLng(lista_lat.get(i).latitude, lista_lat.get(i).longitude))
                            .width(15)
                            .color(Color.BLUE);

                    DecimalFormat decimalFormat = new DecimalFormat("##.##");
                    double metros = SphericalUtil.computeDistanceBetween(lista_lat.get(0), lista_lat.get(lista_lat.size() - 1)) / 1000;
                    //hace que se vaya mostrando la distancia recorrida en ese momento.
                    txtMetros.setText(String.valueOf(decimalFormat.format(metros)));
                    txtCalorias.setText(String.valueOf(decimalFormat.format(calcularCalorias(55,metros))));
                }
                Polyline polygon = map.addPolyline(options);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(lat_long, 15));

            }
        });
    }


    public double calcularCalorias(double peso, double distancia){
        return peso * distancia;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("POSICION", "POSICION: onConnected");
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        mRequestingLocationUpdates = true;
        Log.i("POSICION", "POSICION: startLocationUpdates");
    }


    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        mRequestingLocationUpdates = false;
        Log.i("POSICION", "POSICION: stopLocationUpdates");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}