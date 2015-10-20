package com.salesianostriana.pmdm.jesuspallares_webbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FavoritosActivity extends AppCompatActivity {


    @Bind(R.id.listView_favoritos)
    ListView listaFavoritos;

    ArrayList<String> favoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        ButterKnife.bind(this);

        //De esta manera obtenemos los datos almacenados en el array del MainActivity
        Intent intent = getIntent();
        Bundle datos = intent.getExtras();
        //se le da contenido al array creado en esta clase a través de la id creada en el MainActivity
        favoritos = datos.getStringArrayList("arrayfav");
        for(int i = 0; i<favoritos.size(); i++) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, favoritos);
            listaFavoritos.setAdapter(adapter);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorito, menu);
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
