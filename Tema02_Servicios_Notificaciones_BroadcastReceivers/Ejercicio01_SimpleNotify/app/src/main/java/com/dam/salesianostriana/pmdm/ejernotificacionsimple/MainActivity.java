package com.dam.salesianostriana.pmdm.ejernotificacionsimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

/**
Servicio simple con Notificación

En este ejercicio se pide realizar nuestro primer proyecto con Servicios y Notificaciones. 
Debemos crear los siguientes elementos:

- Un Activity (MainActivity) que tenga 2 botones: Iniciar y Parar, que inicien y paren el Servicio respectivamente. 

Recordar que la forma de lanzar un servicio es:
Intent i = new Intent(this, MiPrimerService.class);
startService(intent):

Y la forma de pararlo:
stopService(intent);

- Un Service (MiPrimerService) que lo único que debe hacer ahora mismo es en el método 
startCommand (método que se lanza al iniciar el servicio), debe lanzar una notificación.

- La diferencia con el ejercicio realizado en clase es que en la Interfaz de usuario debemos 
implementar un Spinner con diferentes títulos de canciones y al darle al botón Play, deberá mostrarse 
en la notificación el título de la canción seleccionada.

- Si se pulsa en el botón stop, deberá ponerse en la notificación "Pausa" y 
como subtítulo el título de la canción pausada.

 **/
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Spinner canciones;
    ImageButton play;
    ImageButton stop;
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
        play = (ImageButton) findViewById(R.id.btn_play);
        stop = (ImageButton) findViewById(R.id.btn_stop);

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
                startService(service);
                break;
            case R.id.btn_stop:
                stopService(service);
                break;
        }

    }
}
