package com.salesianostriana.di.intentsmultiples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
Aplicación Intents múltiples. - 8/10/2015

Queremos conseguir diseñar una aplicación que realice varias funciones. 
En primer lugar, la aplicación debe constar de un menú principal que nos permita selccionar 
entre las diferentes funciones que serán:

    Llamar por teléfono
    Buscar en el mapa
    Envío de email

Lo que se pretende es que desde nuestra aplicación podamos introducir los datos necesarios para
realizar cada una de las acciones y se ejecuten. Pasamos a detellarlas:

-LLAMAR POR TELÉFONO

    El usuario podrá introducir un número de teléfono. Al pulsar en un botón "Realizar llamada", 
    nos deberá aparecer el número escrito por el usuario en la interfaz de llamada, para poder realizar dicha llamada.

-BUSCAR EN MAPA
    
    El usuario podrá introducir una dirección, del mismo modo que lo haría en el buscador de direcciones 
    de Google Maps (http://maps.google.es) y al pulsar en el mapa, se deberá mostrar dicha dirección en un Mapa de Google.

-ENVÍO DE EMAIL

    El usuario podrá redactar un email y enviarlo. Para ello indicará: email destinatario, mensaje del correo y asunto.
    
    IMPORTANTE: para la realización de este ejercicio es necesario definir un 
    Dispositivo Virtual en nuestro AVD que utilice la API de Google de la versión en la que implementemos la aplicación.
    
    AYUDA: Para la realización de este ejercicio será necesario el uso de Intents 
    (acciones lanzadas por el usuario y que serán resueltas por Aplicaciones distintas a la nuestra). 
    Como ayuda se recomienda leer el siguiente enlace de Android Developer: 
    http://developer.android.com/intl/es/training/basics/intents/sending.html
 * */

public class MainActivity extends AppCompatActivity{

    @Bind(R.id.llamada)
    LinearLayout llamada;

    @Bind(R.id.mapas)
    LinearLayout mapa;

    @Bind(R.id.enviar_email)
    LinearLayout email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

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

    @OnClick(R.id.llamada)
    public void submitCall(){
        Intent i = new Intent(MainActivity.this, LlamarActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.mapas)
    public void submitMap(){
        Intent i = new Intent(MainActivity.this, MapaActivity.class);
        startActivity(i);

    }

    @OnClick(R.id.enviar_email)
    public void submitEmail(){
        Intent i = new Intent(MainActivity.this, EmailActivity.class);
        startActivity(i);

    }


}
