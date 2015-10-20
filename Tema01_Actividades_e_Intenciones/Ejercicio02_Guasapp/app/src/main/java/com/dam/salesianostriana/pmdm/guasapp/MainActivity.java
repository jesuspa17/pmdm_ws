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
