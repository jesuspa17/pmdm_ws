package com.jesusps.holamundo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {

    private EditText usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //el método setContentView carga una interfaz de usuario en el Activity actual
        //la IU debe ser un fichero XML, que se encuentre en res/layout/activity_login.xml

        setContentView(R.layout.activity_login);

        //obtenemos id de el bloque usuario de la pantalla Login
        usuario = (EditText) findViewById(R.id.editTextUsuario);

        //Entra en el método onCreate
        Log.i("INFO", "LoginActivity: onCreate");
    }

    @Override
    protected void onResume() {
        //Entra en el método onResume
        Log.i("INFO", "LoginActivity: onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        //Entra en el método onPause
        Log.i("INFO", "LoginActivity: onPause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //Entra en el método onDestroy
        Log.i("INFO", "LoginActivity: onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        //Entra en el método onStop
        Log.i("INFO", "LoginActivity: onStop");
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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


    // 1. public
    // 2. que devuelva void
    // 3. reciba como parámetro un View

    /**
     * Al pulsar en el botón entrar, pasa en a un segundo activity
     * @param v
     */
    public void login(View v){

        //lanzamos el segundo Activity, es decir, una nueva interfaz.
        Intent i = new Intent(this, UserActivity.class);

        // <nombre, valor> => <"email","ejemplo@gmail.com">
        i.putExtra("usuario",usuario.getText()); //recoje el texto escrito en el campo Usuario

        startActivity(i);

    }


}
