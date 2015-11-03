package com.dam.salesianostriana.pmdm.reproductormusica;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * @author Jesus
 */

public class MainActivity extends AppCompatActivity {

    ListView canciones;
    Intent service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canciones = (ListView) findViewById(R.id.listView);

        //Se crea la lista de canciones a reproducir.
        final ArrayList<Cancion> listaCanciones = new ArrayList<Cancion>();
        listaCanciones.add(new Cancion("Leiva", "Terriblemente Cruel","4:30",R.raw.leiva_terriblemente));
        listaCanciones.add(new Cancion("Leiva", "Polvora", "3:30", R.raw.leiva_polvora));

        //Se crea el servicio que iniciaremos para reproducir dicha canción.
        service = new Intent(MainActivity.this, MiNotificacion.class);

        CancionAdapter adapter = new CancionAdapter(this, listaCanciones);
        canciones.setAdapter(adapter);

        canciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cancion cancionSeleccionada = listaCanciones.get(position);

                //Se le pasan los extras al Intent para recoger los datos allí
                Intent i = new Intent(MainActivity.this, ReproductorActivity.class);
                i.putExtra("cancion", cancionSeleccionada.getTitutlo());
                i.putExtra("grupo", cancionSeleccionada.getGrupo());
                i.putExtra("duracion", cancionSeleccionada.getDuracion());
                i.putExtra("icono", android.R.drawable.ic_media_pause);
                i.putExtra("uri", cancionSeleccionada.getRuta_cancion());

                //Se le pasan los extras al Servicio para recoger los datos allí.
                service.putExtra("cancion", cancionSeleccionada.getTitutlo());
                service.putExtra("grupo", cancionSeleccionada.getGrupo());
                service.putExtra("duracion", cancionSeleccionada.getDuracion());
                service.putExtra("uri", cancionSeleccionada.getRuta_cancion());
                //service.putExtra("uri","http://picosong.com/68dJ/");

                //Se para antes el servio para que cancele la reproducción de una canción
                //en el caso de que esté sonando.
                stopService(service);
                //Inicamos servicio y se lanza el activity.
                startService(service);
                startActivity(i);
                MainActivity.this.finish();

            }
        });

    }
}
