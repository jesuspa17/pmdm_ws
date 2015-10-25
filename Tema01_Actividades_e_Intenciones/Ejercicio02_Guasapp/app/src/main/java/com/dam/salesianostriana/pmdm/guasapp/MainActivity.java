package com.dam.salesianostriana.pmdm.guasapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
Guasap App - 7/10/2015

GUASAP app

Bill Gates, el que fuera CEO y fundador de Microsoft, interesado por las aplicaciones móviles, se ha lanzado 
a desarrollar una aplicación que le haga la competencia a Whatsapp.

Bill Gates le ha encargado el desarrollo de la aplicación a los alumnos de 2º DAM de los Salesianos de Triana. 
La aplicación ha sido bautizada como Guasap.

En una 1ª fase de desarrollo se pide que la aplicación realice la siguiente funcionalidad:
GUASAP COMO APLICACIÓN DE MENSAJERÍA

Si en nuestro móvil Android, en el detalle de un contacto, nos encontramos con el icono de envío de mensaje, 
al pulsar, nos deberán aparecer todas las aplicaciones que permitan enviar mensajes de texto, como los SMS. 
Entre esas aplicaciones deberá aparecer la aplicación Guasap, tal y como se muestra en la 
siguiente secuencia de imágenes:

Para que nuestra aplicación Guasap, pueda tratar dichas peticiones, debemos saber los siguientes datos:

    acción: android.intent.action.SENDTO
    esquema de datos: smsto (en clase lo hicimos con datos "tel").
    categoría: android.intent.category.DEFAULT

El Activity que controlará el envío de mensajes será SentMessageActivity.

Para probar el lanzamiento de nuestra app Guasap, utilizar la app que hicimos en 
clase de ListinTelefonico que haga lo siguiente:

    Uri uri = Uri.parse("smsto:" + smsNumber);
    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
    intent.putExtra("sms_body", "Hola Mundo");  
    startActivity(intent);
    
**/

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btn_miguel, btn_jesus;
    TextView tlf_miguel, tlf_jesus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_miguel = (ImageButton) findViewById(R.id.enviarMiguel);
        btn_jesus = (ImageButton) findViewById(R.id.enviarJesus);

        tlf_miguel = (TextView) findViewById(R.id.tlf_miguel);
        tlf_jesus = (TextView) findViewById(R.id.tlf_jesus);


        btn_miguel.setOnClickListener(this);
        btn_jesus.setOnClickListener(this);
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
        String telefono = "";
        if (v.getId() == R.id.enviarMiguel){
            telefono = tlf_miguel.getText().toString();
        }else if(v.getId() == R.id.enviarJesus){
            telefono = tlf_jesus.getText().toString();
        }

        Uri datos = Uri.parse("tel:"+telefono);
        Intent i = new Intent(Intent.ACTION_SENDTO, datos);
        startActivity(i);

    }
}
