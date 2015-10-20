package com.salesianostriana.dam.di.ejemplo2_intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn_marcar;
    Button btn_llamar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_marcar = (Button) findViewById(R.id.btn_marcar);
        btn_marcar.setOnClickListener(this);

        btn_llamar = (Button) findViewById(R.id.btn_marcar);
        btn_llamar.setOnClickListener(this);
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

        int idBoton= v.getId();
        Uri number = Uri.parse("tel:955718480");


        switch (idBoton){
            case R.id.btn_marcar:
                Intent dialPhone = new Intent(Intent.ACTION_DIAL,number);
                startActivity(dialPhone);
                break;
            case R.id.btn_llamar:
                Intent callPhone = new Intent(Intent.ACTION_CALL,number);
                startActivity(callPhone);
                break;
        }


    }
}
