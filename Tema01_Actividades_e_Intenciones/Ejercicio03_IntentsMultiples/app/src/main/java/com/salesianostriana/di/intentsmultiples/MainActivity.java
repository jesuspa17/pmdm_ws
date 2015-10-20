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
