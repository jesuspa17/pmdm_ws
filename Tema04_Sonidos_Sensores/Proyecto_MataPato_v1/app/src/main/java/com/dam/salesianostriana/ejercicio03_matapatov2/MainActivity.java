package com.dam.salesianostriana.ejercicio03_matapatov2;

import android.app.Dialog;
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
import java.io.Reader;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Response;


public class MainActivity extends AppCompatActivity {

    SoundPool soundPool;
    ImageView pato;
    RelativeLayout layout;
    int id;
    int puntuacion;

    TextView tiempo, txtPuntuacion, cuentaAtrasIni;
    Button btnRestart;

    float posXini;
    float posYini;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (RelativeLayout) findViewById(R.id.layout_pato);

        this.getSupportActionBar().hide();

        pato = new ImageView(this);
        pato.setY(150);
        posXini = pato.getX();
        posYini = pato.getY();

        pato.setImageResource(R.drawable.little_duck);
        tiempo = (TextView) findViewById(R.id.textViewTiempo);
        btnRestart = (Button) findViewById(R.id.btn_restart);
        txtPuntuacion = (TextView) findViewById(R.id.textViewPuntuacionLista);
        cuentaAtrasIni = (TextView) findViewById(R.id.textViewCuentaAtrasIni);


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

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtPuntuacion.setText("0");
                iniciarCuentaAtrasInicial();
                btnRestart.setVisibility(View.INVISIBLE);
                pato.setX(posXini);
                pato.setY(posYini);
                cuentaAtrasIni.setVisibility(View.VISIBLE);
                puntuacion = 0;
            }
        });
    }

    boolean acabado = false;

    public void iniciarCuentaAtrasInicial() {

        new CountDownTimer(4000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                cuentaAtrasIni.setText("La partida comienza en: " + String.valueOf(millisUntilFinished / 1000));
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
        new CountDownTimer(5000, 1000) {

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

                tiempo.setText("0 s");
                pato.setOnClickListener(null);
                btnRestart.setVisibility(View.VISIBLE);

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.layout_final_partida);

                final ListView lista = (ListView) dialog.findViewById(R.id.listViewDialog);
                TextView ptos = (TextView) dialog.findViewById(R.id.textViewDialogPuntuacion);
                Button btnIrMenu = (Button) dialog.findViewById(R.id.btnDialogMenu);
                Button btnRestart = (Button) dialog.findViewById(R.id.btnDialogRestart);

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

    public static String leer(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


}
