package com.jesusps.holamundo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class UserActivity extends Activity {

    private TextView textoBienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        textoBienvenida = (TextView) findViewById(R.id.textViewWelcome);

        Log.i("INFO", "UserActivity: onCreate");

        Intent i = getIntent();

        //Rescata los datos escritos en el campo usuario de LoginActivity (lo muestra por el log)
        Bundle datos = i.getExtras();
        String emailUsuario = datos.get("usuario").toString();

        //Cargamos en el textViewWelcome el texto obtenido desde LoginActivity para mostrarlos en la pantalla
        textoBienvenida.setText(textoBienvenida.getText().toString()+emailUsuario);

        Log.i("PARAMETROS","emailUsuario: "+emailUsuario);


    }

    @Override
    protected void onResume() {
        Log.i("INFO", "UserActivity: onCreate");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("INFO", "UserActivity: onCreate");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i("INFO", "UserActivity: onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
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

    public void logout(View v){
        this.onDestroy();
    }

}
