package com.dam.salesianostriana.ejercicio03_matapatov2;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dam.salesianostriana.ejercicio03_matapatov2.pojos.Usuario;

import java.io.IOException;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Response;


public class MainActivity extends AppCompatActivity {

    SoundPool soundPool;
    ImageView pato;
    RelativeLayout layout;
    int id;
    int puntuacion;

    TextView tiempo, txtPuntuacion, cuentaAtrasIni,puntMax;

    float posXini;
    float posYini;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (RelativeLayout) findViewById(R.id.layout_pato);

        prefs = getSharedPreferences("kill_pato", MODE_PRIVATE);
        editor = prefs.edit();

        this.getSupportActionBar().hide();

        pato = new ImageView(this);
        pato.setY(150);
        posXini = pato.getX();
        posYini = pato.getY();

        pato.setImageResource(R.drawable.little_duck);
        tiempo = (TextView) findViewById(R.id.textViewTiempo);
        txtPuntuacion = (TextView) findViewById(R.id.textViewPuntuacionLista);
        cuentaAtrasIni = (TextView) findViewById(R.id.textViewCuentaAtrasIni);
        puntMax =(TextView) findViewById(R.id.textViewPuntMaxima);

        int ptoMax = prefs.getInt("puntuacion",0);

        if(ptoMax!=0){
            puntMax.setText(String.valueOf(ptoMax));
        }

        layout.addView(pato);
        AudioAttributes aa = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(aa)
                .build();

        id = soundPool.load(MainActivity.this, R.raw.powerup, 1);
        iniciarCuentaAtrasInicial();

    }

    boolean acabado = false;

    public void iniciarCuentaAtrasInicial() {

        new CountDownTimer(4000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                cuentaAtrasIni.setText("La partida comienza en " + String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                acabado = true;
                iniciarCuentaAtras();
            }
        }.start();
    }

    public void iniciarCuentaAtras() {
        if (acabado) {
            cuentaAtrasIni.setText("YA!!!!");
        }
        new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tiempo.setText(String.valueOf(millisUntilFinished / 1000) + " s");

                if (millisUntilFinished / 1000 == 58) {
                    cuentaAtrasIni.setText("");
                }

                pato.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        puntuacion = puntuacion + 1;
                        txtPuntuacion.setText(String.valueOf(puntuacion));

                        int width = layout.getWidth() - pato.getWidth();
                        int height = layout.getHeight() - pato.getHeight();

                        int numAleatorioX = (int) Math.floor(Math.random() * width);
                        int numAleatorioY = (int) Math.floor(Math.random() * height);

                        pato.setX(numAleatorioX);
                        pato.setY(numAleatorioY);

                        soundPool.play(id, 1, 1, 1, 0, 1);

                    }
                });

            }

            @Override
            public void onFinish() {


                String nom_user = prefs.getString("nick",null);

                if(prefs.getInt("puntuacion",0) < puntuacion){
                    editor.putInt("puntuacion",puntuacion);
                    editor.apply();
                    new AlmacenarPuntuacionTask().execute(nom_user,String.valueOf(puntuacion));
                }

                tiempo.setText("0 s");
                pato.setOnClickListener(null);

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.layout_final_partida);

                final ListView lista = (ListView) dialog.findViewById(R.id.listViewDialog);
                TextView ptos = (TextView) dialog.findViewById(R.id.textViewDialogPuntuacion);
                Button btnIrMenu = (Button) dialog.findViewById(R.id.btnDialogMenu);
                Button btnRestart = (Button) dialog.findViewById(R.id.btnDialogRestart);

                btnIrMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, MenuActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        MainActivity.this.finish();
                    }
                });

                btnRestart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        txtPuntuacion.setText("0");
                        iniciarCuentaAtrasInicial();
                        pato.setX(posXini);
                        pato.setY(posYini);
                        cuentaAtrasIni.setVisibility(View.VISIBLE);
                        puntuacion = 0;
                        puntMax.setText(String.valueOf(prefs.getInt("puntuacion",0)));
                        dialog.dismiss();


                    }
                });

                String texto = ptos.getText().toString();
                ptos.setText(texto + String.valueOf(puntuacion));

                new ObtenerRankingTask(){

                    @Override
                    protected void onPostExecute(ArrayList<Usuario> usuarios) {
                        super.onPostExecute(usuarios);
                        lista.setAdapter(new RankingAdapter(MainActivity.this,usuarios));
                    }
                }.execute();


                dialog.setTitle("Partida finalizada");
                dialog.show();



            }
        }.start();
    }


    public class ObtenerRankingTask extends AsyncTask<Void, Void, ArrayList<Usuario>>{

        @Override
        protected ArrayList<Usuario> doInBackground(Void... params) {

            Call<ArrayList<Usuario>> listCall = Servicio.instanciarServicio().obtenerRanking();
            Response<ArrayList<Usuario>> listResponse = null;

            try {
                listResponse = listCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return listResponse.body();
        }

    }


    public class AlmacenarPuntuacionTask extends AsyncTask<String, Void, Usuario>{

        @Override
        protected Usuario doInBackground(String... params) {
            Call<Usuario> usuarioCall = Servicio.instanciarServicio().almacenarPuntuacion(params[0],params[1]);
            Response<Usuario> response = null;
            try {
                response = usuarioCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response.body();
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            super.onPostExecute(usuario);
        }
    }

}
