package com.dam.salesianostriana.pmdm.reproductormusica;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindDrawable;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReproductorActivity extends AppCompatActivity {

    @BindDrawable(android.R.drawable.ic_media_pause)
    Drawable btn_pausa;

    @BindDrawable(android.R.drawable.ic_media_play)
    Drawable btn_play;

    TextView textViewCancion;
    TextView textViewGrupo;
    TextView textViewDuracion;
    ImageButton play;
    Intent service;
    ImageButton atras;

    String titulo;
    String grupo;
    String duracion;
    int icono;
    int cancion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);
        ButterKnife.bind(this);

        //Obtenemos los elementos de la UI del reproductor de música
        textViewCancion = (TextView) findViewById(R.id.textViewCancion);
        textViewGrupo = (TextView) findViewById(R.id.textViewGrupo);
        textViewDuracion = (TextView) findViewById(R.id.textViewDuracion);
        play = (ImageButton) findViewById(R.id.btn_play);
        atras = (ImageButton) findViewById(R.id.btn_atras);

        /***INICIAMOS SERVICIO!!***/
        service = new Intent(ReproductorActivity.this, MiNotificacion.class);

        //Para volver al activity de la lista.
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReproductorActivity.this, MainActivity.class);
                startActivity(i);
                ReproductorActivity.this.finish();
            }
        });


        //Obtenemos los extras obtenidos de la lista de Canciones, para pasarselos
        //a los TextView's del Reproductor de música.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             titulo = extras.getString("cancion");
             grupo = extras.getString("grupo");
             duracion = extras.getString("duracion");
             icono = extras.getInt("icono");
             cancion = extras.getInt("uri");

             textViewCancion.setText(titulo);
             textViewGrupo.setText(grupo);
             textViewDuracion.setText(duracion);
             play.setImageDrawable(getResources().getDrawable(icono));
        }
        
    }

    @OnClick(R.id.btn_play)
    public void play(ImageButton v) {
        if (v.getDrawable().equals(btn_play)) {

            /// /Se crea un objeto Canción con los datos obtenidos del MainActivity, de modo que
            //se podrá iniciar el servicio también desde este activity con los datos de la canción

            //Funcionara de igual manera que si pulsamos en el elemento de listview, pero en este caso
            //estaremos pulsando en el botón play/pausa

            Cancion c = new Cancion(grupo, titulo, duracion, cancion);
                service.putExtra("cancion", c.getTitutlo());
                service.putExtra("grupo",c.getGrupo());
                service.putExtra("duracion", c.getDuracion());
                service.putExtra("uri", c.getRuta_cancion());
                //service.putExtra("uri","http://picosong.com/68dJ/");
                startService(service);
                v.setImageDrawable(btn_pausa);

        } else {
            v.setImageDrawable(btn_play);
            stopService(service);

        }
    }


}
