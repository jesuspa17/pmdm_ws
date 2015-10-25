package com.dam.salesianostriana.pmdm.bbva_notification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
/**

BBVA sms confirmación

El BBVA en su app Android tiene un sistema de verifación mediante SMS para confirmar que el titular 
está realizando la operación desde el teléfono móvil que está vinculado a su cuenta.

Se pide implementar la pantalla en la que se recibe dicho código SMS. El proceso es el siguiente:

    El usuario solicita realizar una transferencia.
    El dispositivo recibe un SMS, mientras que la app está aguardando la recepción de dicho SMS.
    Se lee el SMS recibido y si el remitente es 22100 que es la centralita de BBVA, se extrae de dicho SMS 
    el código de verificación.
    Dicho código extraído deberá ser mostrado en una notificación.
    El usuario pulsa en el botón Aceptar para completar la operación. Se muestra un Toast indicando 
    que la operación ha sido correcta.

Ampliación:
Dicho código extraído deberá ser introducido de manera automática por la app en el EditText en el que el usuario lo debería haber escrito, así ahorramos al usuario tener que escribirlo.
 **/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
