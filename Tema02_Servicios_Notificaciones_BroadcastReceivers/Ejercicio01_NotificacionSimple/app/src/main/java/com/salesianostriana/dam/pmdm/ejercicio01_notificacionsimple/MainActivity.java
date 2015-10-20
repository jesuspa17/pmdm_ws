package com.salesianostriana.dam.pmdm.ejercicio01_notificacionsimple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Spinner canciones;
    Button play;
    Button stop;
    Intent service;

    public void darFormatoSpinner(Spinner s, int array){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obteniendo elementos de la UI
        canciones = (Spinner) findViewById(R.id.spi_canciones);
        play = (Button) findViewById(R.id.btn_play);
        stop = (Button) findViewById(R.id.btn_stop);

        //se carga el spinner
        this.darFormatoSpinner(canciones, R.array.canciones);

        //se crea el servicio
        service = new Intent(MainActivity.this, MiNotificacion.class);

        //se asocia los eventos a los botones
        play.setOnClickListener(this);
        stop.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btn_play:
                service.putExtra("cancion", canciones.getSelectedItem().toString());

                if(canciones.getSelectedItem().toString().equalsIgnoreCase("Terriblemente cruel")){

                    service.putExtra("uri",R.raw.leiva_terriblemente);

                }else if(canciones.getSelectedItem().toString().equalsIgnoreCase("Polvora")){

                    service.putExtra("uri",R.raw.leiva_terriblemente);
                }

                startService(service);
                break;
            case R.id.btn_stop:
                stopService(service);
                break;
        }

    }
}
