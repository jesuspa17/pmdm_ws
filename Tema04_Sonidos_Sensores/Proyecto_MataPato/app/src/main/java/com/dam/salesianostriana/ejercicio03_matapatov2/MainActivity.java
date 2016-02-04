package com.dam.salesianostriana.ejercicio03_matapatov2;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Reader;


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
        txtPuntuacion = (TextView) findViewById(R.id.textViewPuntuacion);
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

                tiempo.setText("0 s");
                pato.setOnClickListener(null);
                btnRestart.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Juego finalizado, su puntuación es: " + puntuacion, Toast.LENGTH_SHORT).show();

            }
        }.start();
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
