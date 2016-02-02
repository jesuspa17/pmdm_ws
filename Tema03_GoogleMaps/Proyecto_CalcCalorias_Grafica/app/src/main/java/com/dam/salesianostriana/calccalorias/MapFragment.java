package com.dam.salesianostriana.calccalorias;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.dam.salesianostriana.calccalorias.greendao.Ruta;
import com.dam.salesianostriana.calccalorias.greendao.RutaDao;
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
import java.util.Date;
import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap map;
    MapView mapView;
    private GoogleApiClient mGoogleApiClient = null;
    private Location mCurrentLocation;
    private String mLastUpdateTime;
    private boolean mRequestingLocationUpdates = true;
    private LocationRequest mLocationRequest;

    List<LatLng> lista_lat;
    double metros;
    double calorias;


    TextView txtMetros;
    TextView txtCalorias;
    Button btn_guardar, btn_empezar;
    Chronometer chronometer;

    Polyline polygon;

    String tiempo_inicio;
    String tiempo_final;


    public MapFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_fragment, container, false);

        mapView = (MapView) v.findViewById(R.id.mapview);
        txtMetros = (TextView) v.findViewById(R.id.textViewMetros);
        txtCalorias = (TextView) v.findViewById(R.id.textViewCalorias);
        btn_guardar = (Button) v.findViewById(R.id.btn_guardar);
        btn_empezar = (Button) v.findViewById(R.id.btn_comenzar);
        chronometer = (Chronometer) v.findViewById(R.id.chronometer);

        buildGoogleApiClient();
        createLocationRequest();

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        btn_guardar.setEnabled(false);
        btn_guardar.setBackgroundResource(R.drawable.bg_button_off);

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
        mLocationRequest.setInterval(20000);
        // Interval de una app que requiera una localización exhaustiva
        mLocationRequest.setFastestInterval(20000);// 50 segundos
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
        lista_lat = new ArrayList<>();

        LatLng mi_posicion = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        final Marker marker_inicial = map.addMarker(new MarkerOptions()
                .position(mi_posicion)
                .draggable(true));

        lista_lat.add(mi_posicion);
        marker_inicial.setTitle("Posicion inicial");
        marker_inicial.showInfoWindow();

        final Marker marker_final = map.addMarker(new MarkerOptions()
                .position(mi_posicion)
                .draggable(true));
        marker_final.setVisible(false);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mi_posicion, 15));

                if (btn_guardar.isEnabled()) {

                    final LatLng lat_long = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

                    marker_final.setVisible(true);
                    marker_final.setPosition(lat_long);

                    map.animateCamera(CameraUpdateFactory.newLatLng(lat_long));
                    lista_lat.add(lat_long);
                    PolylineOptions options = new PolylineOptions();

                    for (int i = 0; i < lista_lat.size(); i++) {
                        options.add(new LatLng(lista_lat.get(i).latitude, lista_lat.get(i).longitude))
                                .width(15)
                                .color(Color.BLUE);

                        DecimalFormat decimalFormat = new DecimalFormat("##.##");
                        metros = SphericalUtil.computeDistanceBetween(lista_lat.get(0), lista_lat.get(lista_lat.size() - 1)) / 1000;
                        calorias = calcularCalorias(55, metros);

                        //hace que se vaya mostrando la distancia recorrida en ese momento.
                        txtMetros.setText(String.valueOf(decimalFormat.format(metros)));
                        txtCalorias.setText(String.valueOf(decimalFormat.format(calorias)));

                    }

                    polygon = map.addPolyline(options);
                    polygon.setVisible(true);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(lat_long, 15));

                }

        btn_empezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startLocationUpdates();
                btn_empezar.setEnabled(false);
                btn_guardar.setEnabled(true);
                btn_empezar.setBackgroundResource(R.drawable.bg_button_off);
                btn_guardar.setBackgroundResource(R.drawable.bg_button);
                tiempo_inicio = new Date().toString();

                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();

            }
        });


        final RutaDao rutaDao = Utils.instanciarBD(getActivity()).getRutaDao();

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getContext().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                long id_usuario = Long.parseLong(prefs.getString("id_usuario", null));

                String dia_semana = Utils.getDiaSemana();
                Log.i("DIA SEMANA", "DIA SEMANA; " + dia_semana);

                tiempo_final = new Date().toString();
                Log.i("INICIO", "INICIO; " + tiempo_inicio.toString());
                Log.i("FINAL", "FINAL; " + tiempo_final.toString());

                long minutos;
                long segundos;

                minutos = ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000) / 60;
                segundos = ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000) % 60;
                chronometer.stop();
                chronometer.setText("00:00");

                String tiempo_total = String.valueOf(minutos) + "m:" + segundos + "s";

                rutaDao.insert(new Ruta(metros, calorias, new Date(), tiempo_total, id_usuario));

                List<PojoRuta> lista = new ArrayList<>();
                List<Ruta> listadao = Utils.instanciarBD(getActivity()).getRutaDao().queryBuilder().where(RutaDao.Properties.Id_usuario.eq(id_usuario)).list();

                //actualiza la lista
                for (int i = 0; i < listadao.size(); i++) {
                    lista.add(new PojoRuta(Utils.formatearFechaString(Utils.formatoFinal, listadao.get(i).getFecha().toString()), listadao.get(i).getDistancia(), listadao.get(i).getCalorias(), Utils.getDiaSemana(), listadao.get(i).getDuracion()));
                }
                GraficaFragment.recyclerView.setAdapter(GraficaFragment.adapter = new RutaAdapter(lista));

                polygon.remove();
                polygon.setVisible(false);

                LatLng pos = lista_lat.get(lista_lat.size() - 1);
                marker_inicial.setPosition(lista_lat.get(lista_lat.size() - 1));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(lista_lat.get(lista_lat.size() - 1), 15));

                lista_lat.clear();
                txtCalorias.setText("0");
                txtMetros.setText("0");
                btn_guardar.setEnabled(false);
                btn_empezar.setEnabled(true);
                btn_guardar.setBackgroundResource(R.drawable.bg_button_off);
                btn_empezar.setBackgroundResource(R.drawable.bg_button);

                lista_lat.add(pos);

            }
        });


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
        lista_lat = new ArrayList<>();

        LatLng mi_posicion = new LatLng(37.422134, -6.146573);
        final Marker marker_inicial = map.addMarker(new MarkerOptions()
                .position(mi_posicion)
                .draggable(true));

        lista_lat.add(mi_posicion);
        marker_inicial.setTitle("Posicion inicial");
        marker_inicial.showInfoWindow();

        final Marker marker_final = map.addMarker(new MarkerOptions()
                .position(mi_posicion)
                .draggable(true));
        marker_final.setVisible(false);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mi_posicion, 15));

            //esto lo pongo aqi porq en la maquina virtual no vale la localizacion automatica
            //deberia ir en el updateUI();
            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                    if (btn_guardar.isEnabled()) {

                        final LatLng lat_long = new LatLng(latLng.latitude, latLng.longitude);

                        marker_final.setVisible(true);
                        marker_final.setPosition(lat_long);

                        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        lista_lat.add(lat_long);
                        PolylineOptions options = new PolylineOptions();

                        for (int i = 0; i < lista_lat.size(); i++) {
                            options.add(new LatLng(lista_lat.get(i).latitude, lista_lat.get(i).longitude))
                                    .width(15)
                                    .color(Color.BLUE);

                            DecimalFormat decimalFormat = new DecimalFormat("##.##");
                            metros = SphericalUtil.computeDistanceBetween(lista_lat.get(0), lista_lat.get(lista_lat.size() - 1)) / 1000;
                            calorias = calcularCalorias(55, metros);

                            //hace que se vaya mostrando la distancia recorrida en ese momento.
                            txtMetros.setText(String.valueOf(decimalFormat.format(metros)));
                            txtCalorias.setText(String.valueOf(decimalFormat.format(calorias)));

                        }

                        polygon = map.addPolyline(options);
                        polygon.setVisible(true);
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(lat_long, 15));

                    }

                }
            });


        btn_empezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationUpdates();
                btn_empezar.setEnabled(false);
                btn_guardar.setEnabled(true);
                btn_empezar.setBackgroundResource(R.drawable.bg_button_off);
                btn_guardar.setBackgroundResource(R.drawable.bg_button);
                tiempo_inicio = new Date().toString();

                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();

            }
        });


        final RutaDao rutaDao = Utils.instanciarBD(getActivity()).getRutaDao();

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getContext().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                long id_usuario = Long.parseLong(prefs.getString("id_usuario", null));

                String dia_semana = Utils.getDiaSemana();
                Log.i("DIA SEMANA", "DIA SEMANA; " + dia_semana);

                tiempo_final = new Date().toString();
                Log.i("INICIO", "INICIO; " + tiempo_inicio.toString());
                Log.i("FINAL", "FINAL; " + tiempo_final.toString());

                long minutos;
                long segundos;

                minutos = ((SystemClock.elapsedRealtime()-chronometer.getBase())/1000)/60;
                segundos = ((SystemClock.elapsedRealtime()-chronometer.getBase())/1000)%60;
                chronometer.stop();
                chronometer.setText("00:00");

                String tiempo_total = String.valueOf(minutos)+"m:"+segundos+"s";

                rutaDao.insert(new Ruta(metros, calorias, new Date(), tiempo_total, id_usuario));

                List<PojoRuta> lista = new ArrayList<>();
                List<Ruta> listadao = Utils.instanciarBD(getActivity()).getRutaDao().queryBuilder().where(RutaDao.Properties.Id_usuario.eq(id_usuario)).list();

                //actualiza la lista
                for (int i = 0; i < listadao.size(); i++) {
                    lista.add(new PojoRuta(Utils.formatearFechaString(Utils.formatoFinal, listadao.get(i).getFecha().toString()), listadao.get(i).getDistancia(), listadao.get(i).getCalorias(), Utils.getDiaSemana(), listadao.get(i).getDuracion()));
                }
                GraficaFragment.recyclerView.setAdapter(GraficaFragment.adapter = new RutaAdapter(lista));

                polygon.remove();
                polygon.setVisible(false);

                LatLng pos = lista_lat.get(lista_lat.size()-1);
                marker_inicial.setPosition(lista_lat.get(lista_lat.size()-1));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(lista_lat.get(lista_lat.size()-1), 15));

                lista_lat.clear();
                txtCalorias.setText("0");
                txtMetros.setText("0");
                btn_guardar.setEnabled(false);
                btn_empezar.setEnabled(true);
                btn_guardar.setBackgroundResource(R.drawable.bg_button_off);
                btn_empezar.setBackgroundResource(R.drawable.bg_button);

                lista_lat.add(pos);

            }
        });


    }


    public double calcularCalorias(double peso, double distancia) {
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