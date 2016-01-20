package com.dam.salesianostriana.maps.ejercicio01_traker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        final List<LatLng> lista_lat = new ArrayList<>();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                final LatLng lat_long = new LatLng(latLng.latitude, latLng.longitude);
                final Marker marcador = mMap.addMarker(new MarkerOptions()
                        .position(lat_long)
                        .draggable(true));

                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                marcador.setTitle("Mi posicion");
                marcador.showInfoWindow();

                lista_lat.add(lat_long);
                PolylineOptions options = new PolylineOptions();

                for(int i= 0;i<lista_lat.size();i++) {
                    options.add(new LatLng(lista_lat.get(i).latitude, lista_lat.get(i).longitude))
                            .width(15)
                            .color(Color.BLUE);

                }
                Polyline polygon = mMap.addPolyline(options);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lat_long, 15));

            }
        });





    }

}


