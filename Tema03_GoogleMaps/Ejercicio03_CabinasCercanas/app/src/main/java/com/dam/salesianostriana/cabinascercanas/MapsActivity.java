package com.dam.salesianostriana.cabinascercanas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    Marker marker, markerContenedor, markerC1, markerC2, markerC3, markerC4, markerC5, marcadorUsuario;
    private GoogleApiClient mGoogleApiClient = null;
    private Location mCurrentLocation;
    private String mLastUpdateTime;
    private boolean mRequestingLocationUpdates = true;
    private LocationRequest mLocationRequest;

    ArrayList<LatLng> markers_cabina = new ArrayList<LatLng>();

    LatLng cabina1 = new LatLng(37.381149,-6.008723);
    LatLng cabina2 = new LatLng(37.381166,-6.007628);
    LatLng cabina3 = new LatLng(37.380194,-6.006942);
    LatLng cabina4 = new LatLng(37.380433,-6.007993);
    LatLng cabina5 = new LatLng(37.379495,-6.006856);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // 1. Instancio un objeto de tipo GoogleApiClient
        buildGoogleApiClient();

        // 2. Activar la detección de localización
        createLocationRequest();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
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
        mMap.animateCamera(CameraUpdateFactory.newLatLng(cabina1));
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("POSICION", "POSICION: onLocationChanged");
        mCurrentLocation = location;
        updateUI();

    }
    private void updateUI() {

        LatLng posicionUsuario = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        marcadorUsuario = mMap.addMarker(new MarkerOptions().position(posicionUsuario).draggable(true));

        CircleOptions radioBusqueda = new CircleOptions().center(posicionUsuario).radius(50.00).fillColor(getResources().getColor(R.color.transparecia)).strokeWidth(0).strokeColor(R.color.transparecia);

        markers_cabina.add(cabina1);
        markers_cabina.add(cabina2);
        markers_cabina.add(cabina3);
        markers_cabina.add(cabina4);
        markers_cabina.add(cabina5);

        for (int i = 0; i < markers_cabina.size(); i++) {
            double distancia = SphericalUtil.computeDistanceBetween(posicionUsuario, markers_cabina.get(i));
            DecimalFormat df = new DecimalFormat("#.#");
            if (distancia < 50) {
                markerContenedor = mMap.addMarker(new MarkerOptions().position(markers_cabina.get(i)).title("Cabina " + i).snippet("a " + df.format(distancia) + " metros").icon(BitmapDescriptorFactory.fromResource(R.drawable.cabina_def)));
            }
        }

        Circle circle = mMap.addCircle(radioBusqueda);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicionUsuario, 18));

    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.i("POSICION", "POSICION: onConnected");
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            stopLocationUpdates();
        }
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        mRequestingLocationUpdates = false;
        Log.i("POSICION", "POSICION: stopLocationUpdates");
    }

    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        mRequestingLocationUpdates = true;
        Log.i("POSICION", "POSICION: startLocationUpdates");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

}